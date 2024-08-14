package work.szczepanskimichal.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import work.szczepanskimichal.mapper.ReminderDateMapper;
import work.szczepanskimichal.model.reminder.date.ReminderDateCache;
import work.szczepanskimichal.repository.cache.ReminderDateCacheRepository;

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

    @Scheduled(cron = "1 0 0 * * *")
    public void getReminderDateCachesForNext24h() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        log.info("Puring cache for {} entries", today.format(formatter));

        reminderDateCacheRepository.clearCache();

        log.info("Fetching and caching reminder dates for the next 24 hours. Date: {}", today.format(formatter));

        var reminderDates = reminderDateService.getReminderDatesForNext24h();
        var reminderDateCaches = reminderDates.stream()
                .map(reminderDateMapper::toCache)
                .collect(Collectors.toSet());
        reminderDateCacheRepository.addReminderDateCaches(reminderDateCaches);
        log.info("Reminder dates for {} have been cached successfully. Number of records: {}",
                today.format(formatter), reminderDateCaches.size());
    }


    @Scheduled(fixedRate = 60000) // Check every minute
    public void checkUpcomingReminders() {
        log.info("Reminder scheduler checking for reminder dates in cache for the next 15 minutes...");

        Set<ReminderDateCache> upcomingReminders =
                reminderDateCacheRepository.getReminderDateCachesForNextFifteenMinutes();

        log.info("Fetched {} reminder dates", upcomingReminders.size());

        for (ReminderDateCache reminderDate : upcomingReminders) {
            // Process reminderDate
            // send notification via notificationService
            // get user details from user service (email) via userService
            // get details about reminder/occasion/person
            log.info("sent notification for reminder: {}", reminderDate.getId());
            // remove reminderdate from database
        }

        //todo manage failure on different steps
    }
}