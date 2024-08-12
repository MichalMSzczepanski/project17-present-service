package work.szczepanskimichal.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import work.szczepanskimichal.model.reminder.date.ReminderDateCache;
import work.szczepanskimichal.repository.cache.ReminderDateCacheRepository;

import java.util.Date;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReminderSchedulerService {

    private final ReminderDateCacheRepository reminderDateCustomRepository;
//    private final NotificationService notificationService; // Example service for notifications


    @Scheduled(fixedRate = 60000) // Check every minute
    public void checkUpcomingReminders() {
        Set<ReminderDateCache> upcomingReminders = reminderDateCustomRepository.getReminderDatesForNextFifteenMinutes();
        log.info("reminder scheduler checking for reminders in cache for the next 15 minutes...");

        // Handle upcoming reminders (e.g., send notifications)
        for (ReminderDateCache reminderDate : upcomingReminders) {
            // Process reminderDate
//            notificationService.sendReminderNotification(reminderDate);
            log.info("Checking upcoming reminder date: {}", reminderDate);
        }
    }
}