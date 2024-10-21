package work.szczepanskimichal.scheduler;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.test.context.ActiveProfiles;
import redis.embedded.RedisServer;
import work.szczepanskimichal.exception.DataNotFoundException;
import work.szczepanskimichal.feign.UserServiceFeignClient;
import work.szczepanskimichal.mapper.ReminderDateMapperImpl;
import work.szczepanskimichal.model.reminder.Recurrence;
import work.szczepanskimichal.model.user.UserCommsDto;
import work.szczepanskimichal.repository.cache.ReminderDateCacheRepositoryImpl;
import work.szczepanskimichal.service.OccasionService;
import work.szczepanskimichal.service.PersonService;
import work.szczepanskimichal.service.cache.ReminderDateCacheProcessor;
import work.szczepanskimichal.service.reminder.ReminderDateService;
import work.szczepanskimichal.service.reminder.ReminderService;
import work.szczepanskimichal.service.assembler.OccasionAssembler;
import work.szczepanskimichal.service.assembler.PersonAssembler;
import work.szczepanskimichal.service.assembler.ReminderAssembler;
import work.szczepanskimichal.service.assembler.ReminderDateAssembler;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class ReminderDateCacheProcessorIntegrationTest {

    @Autowired
    private ReminderDateCacheProcessor reminderDateCacheProcessor;
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

//    @MockBean
//    private UserServiceFeignClient userServiceFeignClient;
//    @MockBean
//    private KafkaTemplate<String, String> kafkaTemplate;

//    private static RedisServer redisServer;

    final String PERSON_NAME = "personName";
    final String PERSON_LASTNAME = "personLastName";
    final String OCCASION_NAME = "occasionName";
    final String REMINDER_NAME = "reminderName";
    final LocalDateTime REMINDER_DATE_DATE = LocalDateTime.of(2000, 1, 1, 10, 15);
    final LocalDateTime NOW = LocalDateTime.now();

//    @BeforeEach
//    public void setUp() throws IOException {
//        redisServer = new RedisServer(6379);
//        redisServer.start();
//    }
//
//    @AfterEach
//    public void tearDown() {
//        redisServer.stop();
//    }

    @Test
    @Disabled
    //todo runs solo, breaks with mvn clean install due to @MockBean
    //todo mock feign and kafka without mockito
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
        var cachedReminderDate = reminderDateCacheRepositoryImpl.getReminderDateCacheById(persistedReminderDate.getId());

//        // mock feign client response
//        UUID userId = persistedPerson.getOwner();
//        UserCommsDto userCommsDto = UserCommsDto.builder()
//                .email("test@gmail.com")
//                .build();
//        Mockito.when(userServiceFeignClient.getUserComms(userId)).thenReturn(Optional.of(userCommsDto));
//
//        // mock kafka sending notification
//        CompletableFuture<SendResult<String, String>> future = new CompletableFuture<>();
//        future.complete(null);  // or complete with a SendResult object if needed
//        Mockito.when(kafkaTemplate.send(Mockito.anyString(), Mockito.anyString()))
//                .thenReturn(future);

        // flush all commited changes
        entityManager.flush();
        entityManager.clear();

        // when
        reminderDateCacheProcessor.processReminderDateCache(cachedReminderDate);

        // then
        assertThrows(DataNotFoundException.class, () -> {
            //todo notification sent
            //todo reminderDate deleted
            reminderDateService.getReminderDateById(persistedReminderDateDto.getReminderId());
            //todo recurrence managed
            //todo reminderDateCache deleted

        });
    }

    //todo test with recurring reminderDate

}
