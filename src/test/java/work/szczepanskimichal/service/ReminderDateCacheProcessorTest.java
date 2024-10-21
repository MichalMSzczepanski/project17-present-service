package work.szczepanskimichal.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.RedisTemplate;
import work.szczepanskimichal.model.occasion.Occasion;
import work.szczepanskimichal.model.person.Person;
import work.szczepanskimichal.model.reminder.date.ReminderDateCache;
import work.szczepanskimichal.model.user.UserCommsDto;
import work.szczepanskimichal.repository.PersonRepository;
import work.szczepanskimichal.repository.ReminderDateRepository;
import work.szczepanskimichal.service.cache.ReminderDateCacheProcessor;
import work.szczepanskimichal.service.cache.ReminderDateCacheService;
import work.szczepanskimichal.service.notification.KafkaService;
import work.szczepanskimichal.service.notification.NotificationService;
import work.szczepanskimichal.service.reminder.ReminderDateService;
import work.szczepanskimichal.service.reminder.ReminderRecurrenceService;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReminderDateCacheProcessorTest {

    @InjectMocks
    ReminderDateCacheProcessor reminderDateCacheProcessor;
    @Mock
    PersonService personService;
    @Mock
    PersonRepository personRepository;
    @Mock
    UserService userService;
    @Mock
    KafkaService kafkaService;
    @Mock
    ReminderDateRepository reminderDateRepository;
    @Mock
    NotificationService notificationService;
    @Mock
    ReminderDateService reminderDateService;
    @Mock
    ReminderRecurrenceService reminderRecurrenceService;
    @Mock
    ReminderDateCacheService reminderDateCacheService;

    @MockBean
    RedisTemplate<String, ReminderDateCache> redisTemplate;

    final UUID cacheId = UUID.randomUUID();
    final UUID ownerId = UUID.randomUUID();
    final String email = "email@email.com";

    @Test
    void shouldProcessReminderDateCache_successfully() {
        // given

        var occasionSet = Set.of(Occasion.builder()
                .owner(ownerId)
                .name("occasionName")
                .date(LocalDateTime.now().minusDays(5))
                .build());
        var person = Person.builder()
                .owner(ownerId)
                .name("name")
                .lastname("lastName")
                .occasions(occasionSet)
                .build();
        when(personService.getPersonWithOccasionsAndRemindersByReminderDateId(any())).thenReturn(person);

        var userCommsDto = UserCommsDto.builder()
                .email(email)
                .build();
        when(userService.getUserComms(any())).thenReturn(userCommsDto);

        var reminderDateCache = ReminderDateCache.builder()
                .id(cacheId)
                .build();

        // when
        reminderDateCacheProcessor.processReminderDateCache(reminderDateCache);

        // then
        verify(personService).getPersonWithOccasionsAndRemindersByReminderDateId(cacheId);
        verify(userService).getUserComms(ownerId);
        verify(notificationService).sendReminderMessage(email, person);
        verify(reminderDateService).deleteReminderDate(cacheId);
        verify(reminderDateCacheService).removeReminderDateFromCache(cacheId);
    }


}
