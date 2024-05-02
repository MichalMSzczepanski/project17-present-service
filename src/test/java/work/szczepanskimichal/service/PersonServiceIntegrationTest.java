package work.szczepanskimichal.service;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import work.szczepanskimichal.model.*;
import work.szczepanskimichal.repository.*;

import java.math.BigDecimal;
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

    @Test
    void shouldCreatePerson_withoutOccasions_withoutReminders() {
        //given
        var personName = "personName";
        var personLastName = "personLastName";
        var person = Person.builder()
                .name(personName)
                .lastname(personLastName)
                .build();

        //when
        var result = personService.createPerson(person);
        var persistedPerson = personRepository.findById(result.getId()).get();

        //then
        assertNotNull(persistedPerson.getId());
        assertEquals(persistedPerson.getName(), personName);
        assertEquals(persistedPerson.getLastname(), personLastName);
        assertNull(persistedPerson.getOccasions());
        assertNull(persistedPerson.getPresentsIdeas());
        assertNull(persistedPerson.getPresentsPurchased());

    }

    @Test
    void shouldCreatePerson_withOccasion_withReminder_withPresentIdeas_withPresentsPurchased() {
        var reminder = Reminder.builder()
                .name("weeklyReminder")
                .build();
        var occasionTime = LocalDateTime.now();
        var occasionSet = Set.of(Occasion.builder()
                .name("birthdayOccasion")
                .date(occasionTime)
                .reminders(Set.of(reminder))
                .build());
        var presentIdeaSet = Set.of(PresentIdea.builder()
                .name("presentIdea")
                .description("presentIdeaDescription")
                .price(BigDecimal.valueOf(11L))
                .build());
        var presentPurchasedSet = Set.of(PresentPurchased.builder()
                .name("presentPurchased")
                .description("presentPurchasedDescription")
                .price(BigDecimal.valueOf(99L))
                .build());

        //given
        var personName = "personName";
        var personLastName = "personLastName";
        var person = Person.builder()
                .name(personName)
                .lastname(personLastName)
                .occasions(occasionSet)
                .presentsIdeas(presentIdeaSet)
                .presentsPurchased(presentPurchasedSet)
                .build();

        //when
        var result = personService.createPerson(person);
        var persistedPerson = personRepository.findById(result.getId()).get();

        //then
        assertNotNull(persistedPerson.getId());
        assertEquals(persistedPerson.getName(), personName);
        assertEquals(persistedPerson.getLastname(), personLastName);
        assertEquals(persistedPerson.getOccasions(), occasionSet);
        assertEquals(persistedPerson.getPresentsIdeas(), presentIdeaSet);
        assertEquals(persistedPerson.getPresentsPurchased(), presentPurchasedSet);
        assertEquals(persistedPerson.getOccasions().stream().findFirst().get().getReminders(), Set.of(reminder));
    }

    @Test
    void shouldCreatePerson_andDeleteAllChildrenAfterParentPersonDeletion() {
        var reminder = Reminder.builder()
                .name("weeklyReminder")
                .build();
        var occasionTime = LocalDateTime.now();
        var occasionSet = Set.of(Occasion.builder()
                .name("birthdayOccasion")
                .date(occasionTime)
                .reminders(Set.of(reminder))
                .build());
        var presentIdeaSet = Set.of(PresentIdea.builder()
                .name("presentIdea")
                .description("presentIdeaDescription")
                .price(BigDecimal.valueOf(11L))
                .build());
        var presentPurchasedSet = Set.of(PresentPurchased.builder()
                .name("presentPurchased")
                .description("presentPurchasedDescription")
                .price(BigDecimal.valueOf(99L))
                .build());

        //given
        var personName = "personName";
        var personLastName = "personLastName";
        var person = Person.builder()
                .name(personName)
                .lastname(personLastName)
                .occasions(occasionSet)
                .presentsIdeas(presentIdeaSet)
                .presentsPurchased(presentPurchasedSet)
                .build();

        //when
        var result = personService.createPerson(person);
        var persistedPerson = personRepository.findById(result.getId()).get();
        assertNotNull(persistedPerson.getId());
        assertEquals(persistedPerson.getName(), personName);
        assertEquals(persistedPerson.getLastname(), personLastName);
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