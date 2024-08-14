package work.szczepanskimichal.repository.cache;

import work.szczepanskimichal.model.reminder.date.ReminderDateCache;

import java.util.Set;
import java.util.UUID;

public interface ReminderDateCacheRepository {

    void addReminderDateCache(ReminderDateCache reminderDate);

    void addReminderDateCaches(Set<ReminderDateCache> reminderDates);

    ReminderDateCache getReminderDateById(UUID id);

    Set<ReminderDateCache> getReminderDateCachesForNextFifteenMinutes();

    void removeReminderDateCache(UUID id);

    void clearCache();
}