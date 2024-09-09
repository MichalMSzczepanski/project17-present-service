package work.szczepanskimichal.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import work.szczepanskimichal.exception.UserNotFoundException;
import work.szczepanskimichal.feign.UserServiceFeignClient;
import work.szczepanskimichal.mapper.ReminderDateMapper;
import work.szczepanskimichal.model.reminder.date.ReminderDateCache;
import work.szczepanskimichal.service.PersonService;
import work.szczepanskimichal.service.reminder.ReminderDateService;
import work.szczepanskimichal.service.reminder.ReminderRecurrenceService;
import work.szczepanskimichal.service.cache.ReminderDateCacheService;
import work.szczepanskimichal.service.notification.NotificationService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReminderSchedulerService {

    private final ReminderDateCacheService reminderDateCacheService;
    private final ReminderDateService reminderDateService;
    private final ReminderRecurrenceService reminderRecurrenceService;
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

        reminderDateCacheService.clearCache();


        var reminderDates = reminderDateService.getReminderDatesForNext24h();
        var reminderDateCaches = reminderDates.stream()
                .map(reminderDateMapper::toCache)
                .collect(Collectors.toSet());
        if (reminderDateCaches.isEmpty()) {
            log.info("No reminder dates found");
            return;
        }
        reminderDateCacheService.addReminderDateCaches(reminderDateCaches);
        log.info("Reminder dates for {} have been cached successfully. Number of records: {}",
                today.format(formatter), reminderDateCaches.size());
    }

    //    @Scheduled(cron = "1 */15 * * * *")  correct
    @Scheduled(cron = "1 * * * * *") // for tests
    public void checkUpcomingReminders() {
        log.info("Checking for reminder dates in cache for the next 15 minutes...");

        Set<ReminderDateCache> upcomingReminders =
                reminderDateCacheService.getReminderDateCachesForNextFifteenMinutes();

        log.info("Fetched {} reminder dates", upcomingReminders.size());

        for (ReminderDateCache reminderDateCache : upcomingReminders) {

            log.info("processing reminder date: {}", reminderDateCache);
            var person = personService.getPersonWithOccasionsAndRemindersByReminderDateId(reminderDateCache.getId());
            log.info("fetched details for reminder: {}", person);
            var userCommsDto = userServiceFeignClient.getUserComms(person.getOwner()).orElseThrow(() -> new UserNotFoundException(person.getOwner()));
            log.info("fetched user details for communication: {}", userCommsDto);
            notificationService.sendReminderMessage(userCommsDto.getEmail(), person);
            log.info("dispatched notification for reminder: {} to: {}", reminderDateCache, userCommsDto.getEmail());
            reminderRecurrenceService.manageReminderRecurrence(reminderDateCache.getReminderId(), reminderDateCache.getDate());
            reminderDateService.deleteReminderDate(reminderDateCache.getId());
            log.info("deleted successfully processed reminder date: {}", reminderDateCache.getId());
            reminderDateCacheService.removeReminderDateFromCache(reminderDateCache.getId());
            log.info("deleted successfully processed reminder date cache: {} from cache", reminderDateCache.getId());

        }

        //todo manage failure on different steps
    }

    //todo scheduled action for removing old, not triggered reminders
}