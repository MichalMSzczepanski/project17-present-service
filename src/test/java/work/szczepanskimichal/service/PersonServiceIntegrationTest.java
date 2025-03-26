package work.szczepanskimichal.service;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import work.szczepanskimichal.context.UserContext;
import work.szczepanskimichal.model.person.PersonUpdateDto;
import work.szczepanskimichal.repository.*;
import work.szczepanskimichal.service.assembler.OccasionAssembler;
import work.szczepanskimichal.service.assembler.PersonAssembler;
import work.szczepanskimichal.service.assembler.PresentAssembler;

import java.time.LocalDateTime;

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
    private OccasionService occasionService;
    @Autowired
    private OccasionRepository occasionRepository;
    @Autowired
    private PresentService presentService;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private UserContext userContext;

    final String PERSON_NAME = "personName";
    final String PERSON_LASTNAME = "personLastName";
    final String PERSON_LASTNAME_CHANGED = "personLastNameChanged";
    final String OCCASION_NAME = "occasionName";
    final String PRESENT_NAME = "presentIdeaName";
    final String PRESENT_DESCRIPTION = "presentIdeaDescription";
    final LocalDateTime NOW = LocalDateTime.now();

    @Test
    void shouldCreatePerson_withOccasion_withPresent() {
        //given
        var personCreateDto = PersonAssembler.assemblePersonCreateDto(PERSON_NAME, PERSON_LASTNAME);
        var persistedPerson = personService.createPerson(personCreateDto);
        var occasionCreateDto = OccasionAssembler.assembleOccasion(OCCASION_NAME, NOW, persistedPerson.getId());
        var persistedOccasion = occasionService.createOccasion(occasionCreateDto);
        var presentCreateDto = PresentAssembler.assemblePresentCreateDto(PRESENT_NAME, PRESENT_DESCRIPTION,
                persistedOccasion.getId());
        var persistedPresent = presentService.createPresentIdea(presentCreateDto);

        //when
        entityManager.flush();
        entityManager.clear();
        var resultPerson = personRepository.findById(persistedPerson.getId()).get();
        var resultOccasion = resultPerson.getOccasions().stream().findFirst().get();
        var resultPresent = resultOccasion.getPresentIdeas().stream().findFirst().get();

        //then
        assertEquals(persistedPerson.getId(), resultPerson.getId());
        assertEquals(resultPerson.getName(), PERSON_NAME);
        assertEquals(resultPerson.getLastname(), PERSON_LASTNAME);
        assertEquals(resultOccasion.getId(), persistedOccasion.getId());
        assertEquals(resultOccasion.getName(), OCCASION_NAME);
        assertEquals(resultPresent.getId(), persistedPresent.getId());
        assertEquals(resultPresent.getName(), PRESENT_NAME);
    }

    @Test
    void shouldDeleteAllPersonChildren_onPersonDeletion() {
        //given
        var personCreateDto = PersonAssembler.assemblePersonCreateDto(PERSON_NAME, PERSON_LASTNAME);
        var persistedPerson = personService.createPerson(personCreateDto);
        var occasionCreateDto = OccasionAssembler.assembleOccasion(OCCASION_NAME, NOW, persistedPerson.getId());
        var persistedOccasion = occasionService.createOccasion(occasionCreateDto);
        var presentCreateDto = PresentAssembler.assemblePresentCreateDto(PRESENT_NAME, PRESENT_DESCRIPTION,
                persistedOccasion.getId());
        presentService.createPresentIdea(presentCreateDto);

        //when
        entityManager.flush();
        entityManager.clear();
        personService.deletePersonById(persistedPerson.getId());

        //then
        assertTrue(personRepository.findAll().isEmpty());
        assertTrue(occasionRepository.findAll().isEmpty());
        assertTrue(personRepository.findAll().isEmpty());
    }

    @Test
    void shouldCreateMultiplePersons() {
        //given
        var personCreateDtoOne = PersonAssembler.assemblePersonCreateDto(PERSON_NAME, PERSON_LASTNAME);
        personService.createPerson(personCreateDtoOne);
        var personCreateDtoTwo = PersonAssembler.assemblePersonCreateDto(PERSON_NAME, PERSON_LASTNAME);
        personService.createPerson(personCreateDtoTwo);

        //when
        entityManager.flush();
        entityManager.clear();
        var persons = personService.getAllUserPersons();

        //then
        assertEquals(2, persons.size());
    }

    @Test
    void shouldUpdatePerson() {
        var personCreateDtoOne = PersonAssembler.assemblePersonCreateDto(PERSON_NAME, PERSON_LASTNAME);
        var createdPerson = personService.createPerson(personCreateDtoOne);
        var personUpdatedDto = PersonUpdateDto.builder()
                .id(createdPerson.getId())
                .name(createdPerson.getName())
                .lastname(PERSON_LASTNAME_CHANGED).build();

        //when
        entityManager.flush();
        entityManager.clear();
        userContext.setUserId(createdPerson.getOwner());
        var updatedPerson = personService.updatePerson(personUpdatedDto);

        //then
        assertEquals(PERSON_LASTNAME_CHANGED, updatedPerson.getLastname());
    }
}