package work.szczepanskimichal.model.reminder.date;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ActiveProfiles;
import redis.embedded.RedisServer;
import work.szczepanskimichal.repository.cache.ReminderDateCacheRepositoryImpl;

import java.io.IOException;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@RequiredArgsConstructor
@Slf4j
class ReminderDateCacheTest {

    @Autowired
    private RedisTemplate<String, ReminderDateCache> redisTemplate;

    @Autowired
    private ReminderDateCacheRepositoryImpl reminderDateCustomRepository;

    private static final String reminderDatesKey = "reminderDates";

    private RedisServer redisServer;

    @BeforeEach
    public void setUp() throws IOException {
        redisServer = new RedisServer(6379);
        redisServer.start();
    }

    @AfterEach
    public void tearDown() {
        redisServer.stop();
    }

    @Test
    void testAddReminderDate() {
        UUID id = UUID.randomUUID();
        UUID reminderId = UUID.randomUUID();
        Date date = new Date();

        ReminderDateCache reminderDateCache = ReminderDateCache.builder()
                .id(id)
                .reminderId(reminderId)
                .date(date)
                .build();

        reminderDateCustomRepository.addReminderDate(reminderDateCache);

        Set<ReminderDateCache> reminders = redisTemplate.opsForZSet().rangeByScore(reminderDatesKey, date.getTime(),
                date.getTime());

        assertNotNull(reminders);
        assertTrue(reminders.contains(reminderDateCache));
    }

    @Test
    void testGetReminderDatesForNextFifteenMinutes() {
        UUID id1 = UUID.randomUUID();
        UUID reminderId1 = UUID.randomUUID();
        Date date1 = new Date(System.currentTimeMillis() + 10000); // 10 seconds from now

        ReminderDateCache reminderDateCache1 = ReminderDateCache.builder()
                .id(id1)
                .reminderId(reminderId1)
                .date(date1)
                .build();

        reminderDateCustomRepository.addReminderDate(reminderDateCache1);

        UUID id2 = UUID.randomUUID();
        UUID reminderId2 = UUID.randomUUID();
        Date date2 = new Date(System.currentTimeMillis() - 10000); // 10 seconds ago

        ReminderDateCache reminderDateCache2 = ReminderDateCache.builder()
                .id(id2)
                .reminderId(reminderId2)
                .date(date2)
                .build();

        reminderDateCustomRepository.addReminderDate(reminderDateCache2);

        Set<ReminderDateCache> upcomingReminders =
                reminderDateCustomRepository.getReminderDatesForNextFifteenMinutes();

        assertNotNull(upcomingReminders);
        assertTrue(upcomingReminders.contains(reminderDateCache1));
        assertFalse(upcomingReminders.contains(reminderDateCache2));
    }

    @Test
    void testGetReminderDatesForDifferentTimeRanges() {
        for (int i = 0; i < 5; i++) {
            UUID id = UUID.randomUUID();
            UUID reminderId = UUID.randomUUID();
            Date date = new Date(System.currentTimeMillis() + (i + 1) * 1000 * 60 * 2);

            ReminderDateCache reminderDateCache = ReminderDateCache.builder()
                    .id(id)
                    .reminderId(reminderId)
                    .date(date)
                    .build();

            reminderDateCustomRepository.addReminderDate(reminderDateCache);
        }

        for (int i = 0; i < 5; i++) {
            UUID id = UUID.randomUUID();
            UUID reminderId = UUID.randomUUID();
            Date date = new Date(System.currentTimeMillis() + (i + 1) * 1000 * 60 * 35);

            ReminderDateCache reminderDateCache = ReminderDateCache.builder()
                    .id(id)
                    .reminderId(reminderId)
                    .date(date)
                    .build();

            reminderDateCustomRepository.addReminderDate(reminderDateCache);
        }

        // Get reminders within the next 15 minutes
        Set<ReminderDateCache> upcomingReminders =
                reminderDateCustomRepository.getReminderDatesForNextFifteenMinutes();

        assertNotNull(upcomingReminders);
        assertEquals(5, upcomingReminders.size());

        long currentTime = System.currentTimeMillis();
        long fifteenMinutesLater = currentTime + 15 * 60 * 1000;

        upcomingReminders.forEach(reminder -> {
            long reminderTime = reminder.getDate().getTime();
            assertTrue(reminderTime >= currentTime && reminderTime <= fifteenMinutesLater);
        });
    }


    @Test
    void testRemoveReminderDate() {
        UUID id = UUID.randomUUID();
        UUID reminderId = UUID.randomUUID();
        Date date = new Date();

        ReminderDateCache reminderDateCache = ReminderDateCache.builder()
                .id(id)
                .reminderId(reminderId)
                .date(date)
                .build();

        reminderDateCustomRepository.addReminderDate(reminderDateCache);

        reminderDateCustomRepository.removeReminderDate(id);

        Set<ReminderDateCache> reminders = redisTemplate.opsForZSet().rangeByScore(reminderDatesKey, date.getTime(),
                date.getTime());
        assertNotNull(reminders);
        assertTrue(reminders.isEmpty());
    }
}
