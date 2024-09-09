package work.szczepanskimichal.service.reminder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import work.szczepanskimichal.model.reminder.date.ReminderDateCreateDto;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReminderRecurrenceService {

    private final ReminderService reminderService;
    private final ReminderDateService reminderDateService;

    public void manageReminderRecurrence(UUID reminderId, Date date) {
        var reminder = reminderService.getReminderById(reminderId);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        LocalDateTime newTime = reminder.getRecurring().applyStrategy(localDateTime);

        if (newTime == null) {
            reminderService.deleteReminder(reminder.getId());
            return;
        }

        var newReminderDate = ReminderDateCreateDto.builder()
                .date(newTime)
                .reminderId(reminder.getId())
                .build();

        reminderDateService.createReminderDate(newReminderDate);
        log.info("created new reminder date: {} for reminder: {}", newReminderDate, reminder);
    }
}
