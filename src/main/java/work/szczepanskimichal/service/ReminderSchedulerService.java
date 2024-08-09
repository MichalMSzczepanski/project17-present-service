package work.szczepanskimichal.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import work.szczepanskimichal.model.reminder.date.ReminderDateCache;
import work.szczepanskimichal.repository.redis.ReminderDateRedisRepository;

import java.util.Date;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReminderSchedulerService {

    private final ReminderDateRedisRepository reminderDateCustomRepository;
//    private final NotificationService notificationService; // Example service for notifications


    @Scheduled(fixedRate = 60000) // Check every minute
    public void checkUpcomingReminders() {
        long currentTime = new Date().getTime(); // Get the current timestamp
        Set<ReminderDateCache> upcomingReminders = reminderDateCustomRepository.getUpcomingReminderDates(currentTime);

        // Handle upcoming reminders (e.g., send notifications)
        for (ReminderDateCache reminderDate : upcomingReminders) {
            // Process reminderDate
//            notificationService.sendReminderNotification(reminderDate);
            log.info("Checking upcoming reminder date: {}", reminderDate);
        }
    }
}