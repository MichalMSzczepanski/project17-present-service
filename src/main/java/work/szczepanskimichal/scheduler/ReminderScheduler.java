package work.szczepanskimichal.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import work.szczepanskimichal.model.reminder.date.ReminderDateCache;
import work.szczepanskimichal.service.cache.ReminderDateCacheProcessor;
import work.szczepanskimichal.service.reminder.ReminderDateService;
import work.szczepanskimichal.service.cache.ReminderDateCacheService;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReminderScheduler {

    private final ReminderDateCacheProcessor reminderDateCacheProcessor;
    private final ReminderDateCacheService reminderDateCacheService;
    private final ReminderDateService reminderDateService;

    @Scheduled(cron = "1 0 0 * * *")
    public void getReminderDateCachesForNext24h() {
        reminderDateCacheProcessor.cacheReminderDatesToReminderDateCachesForNext24h();
        checkUpcomingReminders();
    }

    @Scheduled(cron = "1 15-59/15 * * * *")
    public void checkUpcomingReminders() {
        Set<ReminderDateCache> upcomingReminders =
                reminderDateCacheService.getReminderDateCachesForNextFifteenMinutes();
        upcomingReminders.forEach(reminderDateCacheProcessor::processReminderDateCache);
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void deleteExpiredReminderDates() {
        reminderDateService.deleteExpiredReminderDates();
    }

}