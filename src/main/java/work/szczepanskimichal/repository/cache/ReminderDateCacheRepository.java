package work.szczepanskimichal.repository.cache;

import work.szczepanskimichal.model.reminder.date.ReminderDateCache;

import java.util.Set;
import java.util.UUID;

public interface ReminderDateCacheRepository {
    void addReminderDate(ReminderDateCache reminderDate);

    ReminderDateCache getReminderDateById(UUID id);

    Set<ReminderDateCache> getReminderDatesForNextFifteenMinutes();

    void removeReminderDate(UUID id);
}