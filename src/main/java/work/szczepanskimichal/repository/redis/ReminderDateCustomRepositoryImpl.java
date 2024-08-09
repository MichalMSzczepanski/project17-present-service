package work.szczepanskimichal.repository.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import work.szczepanskimichal.model.reminder.date.ReminderDateCache;

import java.util.Set;
import java.util.UUID;

@Repository
public class ReminderDateCustomRepositoryImpl implements ReminderDateRedisRepository {

    private final RedisTemplate<String, ReminderDateCache> redisTemplate;

    @Value("${spring.data.redis.reminder.dates.key}")
    private String reminderDatesKey;

    public ReminderDateCustomRepositoryImpl(RedisTemplate<String, ReminderDateCache> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void addReminderDate(ReminderDateCache reminderDate) {
        long score = reminderDate.getDate().getTime(); // Convert date to timestamp
        redisTemplate.opsForZSet().add(reminderDatesKey, reminderDate, score);
    }

    @Override
    public Set<ReminderDateCache> getUpcomingReminderDates(long currentTime) {
        return redisTemplate.opsForZSet().rangeByScore(reminderDatesKey, currentTime, Double.MAX_VALUE);
    }

    @Override
    public void removeReminderDate(UUID id) {
        // To remove by ID, you need to handle the logic for finding and removing from the sorted set
        // Redis sorted sets do not support direct removal by ID, so you'll need additional logic
    }
}
