package work.szczepanskimichal.repository.cache;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;
import work.szczepanskimichal.model.reminder.date.ReminderDateCache;

import java.util.Date;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ReminderDateCacheRepositoryImpl implements ReminderDateCacheRepository {

    private final RedisTemplate<String, ReminderDateCache> redisTemplate;

    @Value("${spring.data.redis.reminder.dates.key}")
    private String reminderDatesKey;

    @Override
    public void addReminderDateCache(ReminderDateCache reminderDate) {
        long score = reminderDate.getDate().getTime();
        redisTemplate.opsForZSet().add(reminderDatesKey, reminderDate, score);
    }

    @Override
    public void addReminderDateCaches(Set<ReminderDateCache> reminderDates) {
        ZSetOperations<String, ReminderDateCache> zSetOps = redisTemplate.opsForZSet();

        Set<ZSetOperations.TypedTuple<ReminderDateCache>> tuples = reminderDates.stream()
                .map(reminderDate -> new DefaultTypedTuple<>(reminderDate, (double) reminderDate.getDate().getTime()))
                .collect(Collectors.toSet());

        zSetOps.add(reminderDatesKey, tuples);
    }

    @Override
    public Set<ReminderDateCache> getAllReminderDateCaches() {
       return redisTemplate.opsForZSet().range(reminderDatesKey, 0, -1);
    }

    @Override
    public ReminderDateCache getReminderDateCacheById(UUID id) {
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
    public Set<ReminderDateCache> getReminderDateCachesForNextFifteenMinutes() {
        Date currentDate = new Date(System.currentTimeMillis());
        Date fifteenMinutesLaterDate = new Date(System.currentTimeMillis() + 15 * 60 * 1000);

        return redisTemplate.opsForZSet()
                .rangeByScore(reminderDatesKey, currentDate.getTime(), fifteenMinutesLaterDate.getTime());
    }

    @Override
    public void removeReminderDateCache(UUID id) {
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

    @Override
    public void clearCache() {
        redisTemplate.delete(reminderDatesKey);
    }
}
