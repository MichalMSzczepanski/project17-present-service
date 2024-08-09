package work.szczepanskimichal.repository.redis;

import work.szczepanskimichal.model.reminder.date.ReminderDateCache;

import java.util.Set;
import java.util.UUID;

public interface ReminderDateRedisRepository {
    void addReminderDate(ReminderDateCache reminderDate);

    Set<ReminderDateCache> getUpcomingReminderDates(long currentTime);

    void removeReminderDate(UUID id);
}