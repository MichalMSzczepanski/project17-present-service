package work.szczepanskimichal.service;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import work.szczepanskimichal.model.Person;
import work.szczepanskimichal.repository.PersonRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class PersonServiceTest {

    @Autowired
    private PersonService personService;

    @Autowired
    private PersonRepository personRepository;

    @Test
    void shouldCreatePerson_withoutOccasionsOrReminders() {
        //given
        var personName = "personName";
        var personLastName = "personLastName";
        var person = Person.builder()
                .name(personName)
                .lastname(personLastName)
                .build();

        //when
        var result = personService.createPerson(person);
        var persistedEntity = personRepository.findById(result.getId()).get();

        //then
        assertNotNull(persistedEntity.getId());
        assertEquals(persistedEntity.getName(), personName);
        assertEquals(persistedEntity.getLastname(), personLastName);
        assertNull(persistedEntity.getOccasions());
        assertNull(persistedEntity.getPresentsIdeas());
        assertNull(persistedEntity.getPresentsPurchased());

    }

}