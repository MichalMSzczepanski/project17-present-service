package work.szczepanskimichal.service;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import work.szczepanskimichal.model.present.PresentUpdateDto;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class PresentServiceIntegrationTest {

    @Autowired
    private PersonService personService;
    @Autowired
    private OccasionService occasionService;
    @Autowired
    private PresentService presentService;
    @Autowired
    private EntityManager entityManager;

    final String PERSON_NAME = "personName";
    final String PERSON_LASTNAME = "personLastName";
    final String OCCASION_NAME = "occasionName";
    final String PRESENT_NAME = "presentIdeaName";
    final String PRESENT_NAME_UPDATED = "presentNameUpdated";
    final String PRESENT_DESCRIPTION = "presentIdeaDescription";
    final LocalDateTime NOW = LocalDateTime.now();

    @Test
    void shouldCreatePerson_withOccasion_withMultiplePresents() {

        //given
        var personCreateDto = PersonAssembler.assemblePersonCreateDto(PERSON_NAME, PERSON_LASTNAME);
        var persistedPerson = personService.createPerson(personCreateDto);
        var occasionCreateDto = OccasionAssembler.assembleOccasion(OCCASION_NAME, NOW, persistedPerson.getId());
        var persistedOccasion = occasionService.createOccasion(occasionCreateDto);
        var presentCreateDtoOne = PresentAssembler.assemblePresentCreateDto(PRESENT_NAME, PRESENT_DESCRIPTION,
                persistedOccasion.getId());
        presentService.createPresent(presentCreateDtoOne);
        var presentCreateDtoTwo = PresentAssembler.assemblePresentCreateDto(PRESENT_NAME, PRESENT_DESCRIPTION,
                persistedOccasion.getId());
        presentService.createPresent(presentCreateDtoTwo);

        //when
        entityManager.flush();
        entityManager.clear();
        var presents = presentService.getPresentsByOccasionId(persistedOccasion.getId());

        //then
        assertEquals(2, presents.size());
    }

    @Test
    void shouldUpdatePresent() {
        //given
        var personCreateDto = PersonAssembler.assemblePersonCreateDto(PERSON_NAME, PERSON_LASTNAME);
        var persistedPerson = personService.createPerson(personCreateDto);
        var occasionCreateDto = OccasionAssembler.assembleOccasion(OCCASION_NAME, NOW, persistedPerson.getId());
        var persistedOccasion = occasionService.createOccasion(occasionCreateDto);
        var presentCreateDtoOne = PresentAssembler.assemblePresentCreateDto(PRESENT_NAME, PRESENT_DESCRIPTION,
                persistedOccasion.getId());
        var present = presentService.createPresent(presentCreateDtoOne);
        var updatedPresent = PresentUpdateDto.builder()
                .id(present.getId())
                .name(PRESENT_NAME_UPDATED)
                .type(present.getType())
                .description(present.getDescription())
                .price(present.getPrice())
                .occasionId(present.getOccasionId())
                .build();

        //when
        entityManager.flush();
        entityManager.clear();
        var updatedEntity = presentService.updatePresent(updatedPresent);

        //then
        assertEquals(PRESENT_NAME_UPDATED, updatedEntity.getName());
    }

}