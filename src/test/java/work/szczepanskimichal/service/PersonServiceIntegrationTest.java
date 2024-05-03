package work.szczepanskimichal.service;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import work.szczepanskimichal.repository.*;

import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class PersonServiceIntegrationTest {

    @Autowired
    private PersonService personService;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private OccasionRepository occasionRepository;
    @Autowired
    private ReminderRepository reminderRepository;
    @Autowired
    private PresentIdeaRepository presentIdeaRepository;
    @Autowired
    private PresentPurchasedRepository presentPurchasedRepository;

    final String PERSON_NAME = "personName";
    final String PERSON_LASTNAME = "personLastName";
    final String REMINDER_NAME = "reminderName";
    final String OCCASION_NAME = "occasionName";
    final String PRESENT_IDEA_NAME = "presentIdeaName";
    final String PRESENT_IDEA_DESCRIPTION = "presentIdeaDescription";
    final String PRESENT_PURCHASED_NAME = "presentPurchasedName";
    final String PRESENT_PURCHASED_DESCRIPTION = "presentPurchasedDescription";

    @Test
    void shouldCreatePerson_withoutOccasions_withoutReminders() {

        //given
        var person = PersonAssembler.assemblePerson(PERSON_NAME, PERSON_LASTNAME);

        //when
        var result = personService.createPerson(person);
        var persistedPerson = personRepository.findById(result.getId()).get();

        //then
        assertNotNull(persistedPerson.getId());
        assertEquals(persistedPerson.getName(), PERSON_NAME);
        assertEquals(persistedPerson.getLastname(), PERSON_LASTNAME);
        assertNull(persistedPerson.getOccasions());
        assertNull(persistedPerson.getPresentsIdeas());
        assertNull(persistedPerson.getPresentsPurchased());

    }

    @Test
    void shouldCreatePerson_withOccasion_withReminder_withPresentIdeas_withPresentsPurchased() {
        //given
        var reminder = ReminderAssembler.assembleReminder(REMINDER_NAME);
        var occasionTime = LocalDateTime.now();
        var occasionSet = Set.of(OccasionAssembler.assembleOccasion(
                OCCASION_NAME,
                occasionTime,
                reminder));
        var presentIdeaSet = Set.of(PresentIdeaAssembler.assemblePresentIdea(
                PRESENT_IDEA_NAME,
                PRESENT_IDEA_DESCRIPTION));
        var presentPurchasedSet = Set.of(PresentPurchasedAssembler.assemblePresentIdea(
                PRESENT_PURCHASED_NAME,
                PRESENT_PURCHASED_DESCRIPTION));

        var person = PersonAssembler.assemblePerson(
                PERSON_NAME,
                PERSON_LASTNAME,
                occasionSet,
                presentIdeaSet,
                presentPurchasedSet);

        //when
        var result = personService.createPerson(person);
        var persistedPerson = personRepository.findById(result.getId()).get();

        //then
        assertNotNull(persistedPerson.getId());
        assertEquals(persistedPerson.getName(), PERSON_NAME);
        assertEquals(persistedPerson.getLastname(), PERSON_LASTNAME);
        assertEquals(persistedPerson.getOccasions(), occasionSet);
        assertEquals(persistedPerson.getPresentsIdeas(), presentIdeaSet);
        assertEquals(persistedPerson.getPresentsPurchased(), presentPurchasedSet);
        assertEquals(persistedPerson.getOccasions().stream().findFirst().get().getReminders(), Set.of(reminder));
    }

    @Test
    void shouldCreatePerson_andDeleteAllChildren_afterParentPersonDeletion() {
        //given
        var reminder = ReminderAssembler.assembleReminder(REMINDER_NAME);
        var occasionTime = LocalDateTime.now();
        var occasionSet = Set.of(OccasionAssembler.assembleOccasion(
                OCCASION_NAME,
                occasionTime,
                reminder));
        var presentIdeaSet = Set.of(PresentIdeaAssembler.assemblePresentIdea(
                PRESENT_IDEA_NAME,
                PRESENT_IDEA_DESCRIPTION));
        var presentPurchasedSet = Set.of(PresentPurchasedAssembler.assemblePresentIdea(
                PRESENT_PURCHASED_NAME,
                PRESENT_PURCHASED_DESCRIPTION));

        var person = PersonAssembler.assemblePerson(
                PERSON_NAME,
                PERSON_LASTNAME,
                occasionSet,
                presentIdeaSet,
                presentPurchasedSet);

        //when
        var result = personService.createPerson(person);
        var persistedPerson = personRepository.findById(result.getId()).get();
        assertNotNull(persistedPerson.getId());
        assertEquals(persistedPerson.getName(), PERSON_NAME);
        assertEquals(persistedPerson.getLastname(), PERSON_LASTNAME);
        assertEquals(persistedPerson.getOccasions(), occasionSet);
        assertEquals(persistedPerson.getPresentsIdeas(), presentIdeaSet);
        assertEquals(persistedPerson.getPresentsPurchased(), presentPurchasedSet);
        assertEquals(persistedPerson.getOccasions().stream().findFirst().get().getReminders(), Set.of(reminder));

        personRepository.deleteById(result.getId());

        //then
        assertTrue(personRepository.findAll().isEmpty());
        assertTrue(occasionRepository.findAll().isEmpty());
        assertTrue(reminderRepository.findAll().isEmpty());
        assertTrue(presentIdeaRepository.findAll().isEmpty());
        assertTrue(presentPurchasedRepository.findAll().isEmpty());
    }

}