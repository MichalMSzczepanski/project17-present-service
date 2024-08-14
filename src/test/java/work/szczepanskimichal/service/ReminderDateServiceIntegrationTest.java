package work.szczepanskimichal.service;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import redis.embedded.RedisServer;
import work.szczepanskimichal.model.reminder.date.ReminderDateCache;
import work.szczepanskimichal.model.reminder.date.ReminderDateUpdateDto;
import work.szczepanskimichal.repository.cache.ReminderDateCacheRepositoryImpl;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class ReminderDateServiceIntegrationTest {

    @Autowired
    private PersonService personService;
    @Autowired
    private OccasionService occasionService;
    @Autowired
    private ReminderService reminderService;
    @Autowired
    private ReminderDateService reminderDateService;
    @Autowired
    private EntityManager entityManager;

    final String PERSON_NAME = "personName";
    final String PERSON_LASTNAME = "personLastName";
    final String OCCASION_NAME = "occasionName";
    final String REMINDER_NAME = "reminderName";
    final Date REMINDER_DATE_DATE = Date.from(Instant.now());
    final Date REMINDER_DATE_DATE_UPDATED = Date.from(Instant.now().plusSeconds(100));
    final LocalDateTime NOW = LocalDateTime.now();

    @Autowired
    private ReminderDateCacheRepositoryImpl reminderDateCacheRepositoryImpl;

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
    void shouldCreatePerson_withOccasion_withReminder_withReminderDate() {

        //given
        var personCreateDto = PersonAssembler.assemblePersonCreateDto(PERSON_NAME, PERSON_LASTNAME);
        var persistedPerson = personService.createPerson(personCreateDto);
        var occasionCreateDto = OccasionAssembler.assembleOccasion(OCCASION_NAME, NOW, persistedPerson.getId());
        var persistedOccasion = occasionService.createOccasion(occasionCreateDto);
        var reminderCreateDtoOne = ReminderAssembler.AssembleReminderCreateDto(REMINDER_NAME, false,
                persistedOccasion.getId());
        var persistedReminder = reminderService.createReminder(reminderCreateDtoOne);
        var reminderDateCreateDto = ReminderDateAssembler.AssembleReminderDateCreateDto(REMINDER_DATE_DATE,
                persistedReminder.getId());
        var persistedReminderDateDto = reminderDateService.createReminderDate(reminderDateCreateDto);

        //when
        entityManager.flush();
        entityManager.clear();
        var reminderDate = reminderDateService.getReminderDateById(persistedReminderDateDto.getId());

        //then
        assertNotNull(reminderDate);
        assertEquals(persistedReminderDateDto.getReminderId(), reminderDate.getReminderId());

//        //and then
//        ReminderDateCache cachedReminderDate =
//                reminderDateCacheRepositoryImpl.getReminderDateById(persistedReminderDateDto.getId());
//        assertNotNull(cachedReminderDate);
//        assertEquals(persistedReminderDateDto.getId(), cachedReminderDate.getId());
//        assertEquals(persistedReminderDateDto.getDate(), cachedReminderDate.getDate());
//        assertEquals(persistedReminderDateDto.getReminderId(), cachedReminderDate.getReminderId());
    }

    @Test
    void shouldUpdateReminderDate() {

        //given
        var personCreateDto = PersonAssembler.assemblePersonCreateDto(PERSON_NAME, PERSON_LASTNAME);
        var persistedPerson = personService.createPerson(personCreateDto);
        var occasionCreateDto = OccasionAssembler.assembleOccasion(OCCASION_NAME, NOW, persistedPerson.getId());
        var persistedOccasion = occasionService.createOccasion(occasionCreateDto);
        var reminderCreateDtoOne = ReminderAssembler.AssembleReminderCreateDto(REMINDER_NAME, false,
                persistedOccasion.getId());
        var persistedReminder = reminderService.createReminder(reminderCreateDtoOne);
        var reminderDateCreateDto = ReminderDateAssembler.AssembleReminderDateCreateDto(REMINDER_DATE_DATE,
                persistedReminder.getId());
        var reminderDate = reminderDateService.createReminderDate(reminderDateCreateDto);
        var reminderDateUpdated = ReminderDateUpdateDto.builder()
                .id(reminderDate.getId())
                .date(REMINDER_DATE_DATE_UPDATED)
                .reminderId(reminderDate.getReminderId())
                .build();

        //when
        entityManager.flush();
        entityManager.clear();
        var updatedEntity = reminderDateService.updateReminderDate(reminderDateUpdated);

        //then
        assertEquals(REMINDER_DATE_DATE_UPDATED, updatedEntity.getDate());
    }

}