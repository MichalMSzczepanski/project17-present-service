package work.szczepanskimichal.model.reminder;

import java.time.LocalDateTime;

public interface RecurrenceStrategy {

    LocalDateTime calculateNextReminderDate(LocalDateTime dateTime);

}
