package work.szczepanskimichal.service;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import work.szczepanskimichal.context.UserContext;
import work.szczepanskimichal.model.reminder.Recurrence;
import work.szczepanskimichal.model.reminder.ReminderUpdateDto;
import work.szczepanskimichal.service.assembler.OccasionAssembler;
import work.szczepanskimichal.service.assembler.PersonAssembler;
import work.szczepanskimichal.service.assembler.ReminderAssembler;
import work.szczepanskimichal.service.reminder.ReminderService;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class ReminderServiceIntegrationTest {

    @Autowired
    private PersonService personService;
    @Autowired
    private OccasionService occasionService;
    @Autowired
    private ReminderService reminderService;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private UserContext userContext;

    final String PERSON_NAME = "personName";
    final String PERSON_LASTNAME = "personLastName";
    final String OCCASION_NAME = "occasionName";
    final String REMINDER_NAME = "reminderName";
    final String REMINDER_NAME_UPDATED = "reminderNameUpdated";
    final LocalDateTime NOW = LocalDateTime.now();

    @Test
    void shouldCreatePerson_withOccasion_withMultipleReminders() {
        //given
        var personCreateDto = PersonAssembler.assemblePersonCreateDto(PERSON_NAME, PERSON_LASTNAME);
        var persistedPerson = personService.createPerson(personCreateDto);
        var occasionCreateDto = OccasionAssembler.assembleOccasion(OCCASION_NAME, NOW, persistedPerson.getId());
        var persistedOccasion = occasionService.createOccasion(occasionCreateDto);
        var reminderCreateDtoOne = ReminderAssembler.AssembleReminderCreateDto(REMINDER_NAME, Recurrence.NON,
                persistedOccasion.getId());
        reminderService.createReminder(reminderCreateDtoOne);
        var reminderCreateDtoTwo = ReminderAssembler.AssembleReminderCreateDto(REMINDER_NAME, Recurrence.NON,
                persistedOccasion.getId());
        reminderService.createReminder(reminderCreateDtoTwo);

        //when
        entityManager.flush();
        entityManager.clear();
        var reminders = reminderService.getRemindersByOccasion(persistedOccasion.getId());

        //then
        assertEquals(2, reminders.size());
    }

    @Test
    void shouldUpdateReminder() {
        //given
        var personCreateDto = PersonAssembler.assemblePersonCreateDto(PERSON_NAME, PERSON_LASTNAME);
        var persistedPerson = personService.createPerson(personCreateDto);
        var occasionCreateDto = OccasionAssembler.assembleOccasion(OCCASION_NAME, NOW, persistedPerson.getId());
        var persistedOccasion = occasionService.createOccasion(occasionCreateDto);
        var reminderCreateDtoOne = ReminderAssembler.AssembleReminderCreateDto(REMINDER_NAME, Recurrence.NON,
                persistedOccasion.getId());
        var reminder = reminderService.createReminder(reminderCreateDtoOne);
        var reminderUpdateDto = ReminderUpdateDto.builder()
                .id(reminder.getId())
                .name(REMINDER_NAME_UPDATED)
                .occasionId(reminder.getOccasionId())
                .recurring(reminder.getRecurring())
                .build();

        //when
        entityManager.flush();
        entityManager.clear();
        userContext.setUserId(persistedPerson.getOwner());
        var reminderUpdated = reminderService.updateReminder(reminderUpdateDto);

        //then
        assertEquals(REMINDER_NAME_UPDATED, reminderUpdated.getName());
    }
}