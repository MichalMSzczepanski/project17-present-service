package work.szczepanskimichal.model.reminder.date;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;

import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class ReminderDateCacheTest {

    private static final GenericContainer<?> redisContainer = new GenericContainer<>("redis:latest")
            .withExposedPorts(6379)
            .waitingFor(Wait.forListeningPort());

    @Autowired
    private RedisTemplate<String, ReminderDateCache> redisTemplate;

    @BeforeAll
    public static void setUp() {
        redisContainer.start();
    }

    @AfterAll
    public static void tearDown() {
        redisContainer.stop();
    }

    @Test
    public void testRedisOperations() {
        UUID id = UUID.randomUUID();
        UUID reminderId = UUID.randomUUID();
        Date date = new Date();

        // Use Lombok's builder pattern to create an instance
        ReminderDateCache reminderDateCache = ReminderDateCache.builder()
                .id(id)
                .reminderId(reminderId)
                .date(date)
                .build();

        ValueOperations<String, ReminderDateCache> ops = redisTemplate.opsForValue();
        String key = "reminder:" + id.toString();

        // Save to Redis
        ops.set(key, reminderDateCache);

        // Retrieve from Redis
        ReminderDateCache retrieved = ops.get(key);

        assertNotNull(retrieved);
        assertEquals(id, retrieved.getId());
        assertEquals(reminderId, retrieved.getReminderId());
        assertEquals(date, retrieved.getDate());
    }
}
