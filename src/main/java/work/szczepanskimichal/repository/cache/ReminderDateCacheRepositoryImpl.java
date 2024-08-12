package work.szczepanskimichal.repository.cache;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import work.szczepanskimichal.model.reminder.date.ReminderDateCache;

import java.util.Set;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ReminderDateCacheRepositoryImpl implements ReminderDateCacheRepository {

    private final RedisTemplate<String, ReminderDateCache> redisTemplate;

    @Value("${spring.data.redis.reminder.dates.key}")
    private String reminderDatesKey;

    @Override
    public void addReminderDate(ReminderDateCache reminderDate) {
        long score = reminderDate.getDate().getTime();
        redisTemplate.opsForZSet().add(reminderDatesKey, reminderDate, score);
    }

    @Override
    public ReminderDateCache getReminderDateById(UUID id) {
        Set<ReminderDateCache> reminders = redisTemplate.opsForZSet().range(reminderDatesKey, 0, -1);

        if (reminders != null) {
            return reminders.stream()
                    .filter(r -> r.getId().equals(id))
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }

    @Override
    public Set<ReminderDateCache> getReminderDatesForNextFifteenMinutes() {
        long currentTime = System.currentTimeMillis();
        long fifteenMinutesLater = currentTime + 15 * 60 * 1000; // 15 minutes in milliseconds

//        //todo this should be done once the notification are successfully sent
//        reminders.forEach(r -> removeReminderDate(r.getId()));
        // Fetch reminders from the sorted set that fall within the next 15 minutes
        return redisTemplate.opsForZSet()
                .rangeByScore(reminderDatesKey, currentTime, fifteenMinutesLater);
    }

    @Override
    public void removeReminderDate(UUID id) {
        Set<ReminderDateCache> reminders = redisTemplate.opsForZSet()
                .rangeByScore(reminderDatesKey, 0, Double.MAX_VALUE);

        if (reminders != null) {
            for (ReminderDateCache reminder : reminders) {
                if (reminder.getId().equals(id)) {
                    redisTemplate.opsForZSet().remove(reminderDatesKey, reminder);
                    break;
                }
            }
        }
    }
}
