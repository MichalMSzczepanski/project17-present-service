package work.szczepanskimichal.scheduler;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import redis.embedded.RedisServer;
import work.szczepanskimichal.mapper.ReminderDateMapperImpl;
import work.szczepanskimichal.model.reminder.Recurrence;
import work.szczepanskimichal.model.user.UserCommsDto;
import work.szczepanskimichal.repository.cache.ReminderDateCacheRepositoryImpl;
import work.szczepanskimichal.service.OccasionService;
import work.szczepanskimichal.service.PersonService;
import work.szczepanskimichal.service.reminder.ReminderDateService;
import work.szczepanskimichal.service.reminder.ReminderService;
import work.szczepanskimichal.service.assembler.OccasionAssembler;
import work.szczepanskimichal.service.assembler.PersonAssembler;
import work.szczepanskimichal.service.assembler.ReminderAssembler;
import work.szczepanskimichal.service.assembler.ReminderDateAssembler;

import java.io.IOException;
import java.time.LocalDateTime;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class ReminderSchedulerServiceTest {

    @Autowired
    private ReminderSchedulerService reminderSchedulerService;
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
    @Autowired
    private ReminderDateCacheRepositoryImpl reminderDateCacheRepositoryImpl;
    @Autowired
    private ReminderDateMapperImpl reminderDateMapperImpl;

    private static RedisServer redisServer;

    //todo use WIREMOCK to mock feign
    //todo MOCK kafka without mockito

    final String PERSON_NAME = "personName";
    final String PERSON_LASTNAME = "personLastName";
    final String OCCASION_NAME = "occasionName";
    final String REMINDER_NAME = "reminderName";
    final LocalDateTime REMINDER_DATE_DATE = LocalDateTime.now().plusMinutes(5L);
    final LocalDateTime NOW = LocalDateTime.now();

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
    @Disabled
    void shouldCreatePerson_withOccasion_withReminder_withReminderDate() {

        //given
        var personCreateDto = PersonAssembler.assemblePersonCreateDto(PERSON_NAME, PERSON_LASTNAME);
        var persistedPerson = personService.createPerson(personCreateDto);
        var occasionCreateDto = OccasionAssembler.assembleOccasion(OCCASION_NAME, NOW, persistedPerson.getId());
        var persistedOccasion = occasionService.createOccasion(occasionCreateDto);
        var reminderCreateDtoOne = ReminderAssembler.AssembleReminderCreateDto(REMINDER_NAME, Recurrence.NON,
                persistedOccasion.getId());
        var persistedReminder = reminderService.createReminder(reminderCreateDtoOne);
        var reminderDateCreateDto = ReminderDateAssembler.AssembleReminderDateCreateDto(REMINDER_DATE_DATE,
                persistedReminder.getId());
        var persistedReminderDateDto = reminderDateService.createReminderDate(reminderDateCreateDto);
        var persistedReminderDate = reminderDateMapperImpl.toEntity(persistedReminderDateDto);
        var cache = reminderDateMapperImpl.toCache(persistedReminderDate);

        // save cache
        reminderDateCacheRepositoryImpl.addReminderDateCache(cache);
        var cachedReminderDate= reminderDateCacheRepositoryImpl.getReminderDateCacheById(persistedReminderDate.getId());

        // mock feign client response
        UserCommsDto userCommsDto = UserCommsDto.builder()
                .email("test@gmail.com")
                .build();
//        given(userServiceFeignClient.getUserComms(any())).willReturn(Optional.of(userCommsDto));
//
//        //mock kafka being ok
//        doNothing().when(kafkaService).sendMessage(any(Notification.class));

        // run job - last time join fetch didnt work
        //when
        entityManager.flush();
        entityManager.clear();
//        reminderSchedulerService.processReminderDateCache(cachedReminderDate);
//        var reminderDate = reminderDateService.getReminderDateById(persistedReminderDateDto.getId());

        //then

        //reminderDate is deleted from db

        //notification service was called

        //
    }

}