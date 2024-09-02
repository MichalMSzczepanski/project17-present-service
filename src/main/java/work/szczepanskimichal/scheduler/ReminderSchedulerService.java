package work.szczepanskimichal.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import work.szczepanskimichal.exception.UserNotFoundException;
import work.szczepanskimichal.feign.UserServiceFeignClient;
import work.szczepanskimichal.mapper.ReminderDateMapper;
import work.szczepanskimichal.model.reminder.date.ReminderDateCache;
import work.szczepanskimichal.repository.cache.ReminderDateCacheRepository;
import work.szczepanskimichal.service.PersonService;
import work.szczepanskimichal.service.ReminderDateService;
import work.szczepanskimichal.service.ReminderService;
import work.szczepanskimichal.service.notification.NotificationService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReminderSchedulerService {

    private final ReminderDateCacheRepository reminderDateCacheRepository;
    private final ReminderDateService reminderDateService;
    private final ReminderDateMapper reminderDateMapper;
    private final PersonService personService;
    private final UserServiceFeignClient userServiceFeignClient;
    private final NotificationService notificationService;

    //    @Scheduled(cron = "1 0 0 * * *") correct
    @Scheduled(cron = "1 */2 * * * *") // for tests
    public void getReminderDateCachesForNext24h() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        log.info("Fetching and caching reminder dates for the next 24 hours. Date: {}", today.format(formatter));
        log.info("Purging cache for {} entries", today.format(formatter));

        reminderDateCacheRepository.clearCache();


        var reminderDates = reminderDateService.getReminderDatesForNext24h();
        var reminderDateCaches = reminderDates.stream()
                .map(reminderDateMapper::toCache)
                .collect(Collectors.toSet());
        if (reminderDateCaches.isEmpty()) {
            log.info("No reminder dates found");
            return;
        }
        reminderDateCacheRepository.addReminderDateCaches(reminderDateCaches);
        log.info("Reminder dates for {} have been cached successfully. Number of records: {}",
                today.format(formatter), reminderDateCaches.size());
    }

    //    @Scheduled(cron = "1 */15 * * * *")  correct
    @Scheduled(cron = "1 * * * * *") // for tests
    public void checkUpcomingReminders() {
        log.info("Checking for reminder dates in cache for the next 15 minutes...");

        Set<ReminderDateCache> upcomingReminders =
                reminderDateCacheRepository.getReminderDateCachesForNextFifteenMinutes();

        log.info("Fetched {} reminder dates", upcomingReminders.size());

        for (ReminderDateCache reminderDate : upcomingReminders) {

            log.info("processing reminder date: {}", reminderDate);
            var person = personService.getPersonWithOccasionsAndRemindersByReminderDateId(reminderDate.getId());
            log.info("fetched details for reminder: {}", person);
            var userCommsDto = userServiceFeignClient.getUserComms(person.getOwner()).orElseThrow(() -> new UserNotFoundException(person.getOwner()));
            log.info("fetched user details for communication: {}", userCommsDto);
            notificationService.sendReminderMessage(userCommsDto.getEmail(), person);
            log.info("dispatched notification for reminder: {} to: {}", reminderDate, userCommsDto.getEmail());
            // remove reminderdate from database
            reminderDateService.deleteReminderDate(reminderDate.getId());
            //check recurrance of reminder if we need to set a new date
            //remove reminderdate from cache
        }

        //todo manage failure on different steps
    }

    //todo scheduled action for removing old, not triggered reminders
}