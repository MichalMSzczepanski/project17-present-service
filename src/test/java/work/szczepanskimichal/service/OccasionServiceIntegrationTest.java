package work.szczepanskimichal.service;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import work.szczepanskimichal.context.UserContext;
import work.szczepanskimichal.model.occasion.OccasionUpdateDto;
import work.szczepanskimichal.service.assembler.OccasionAssembler;
import work.szczepanskimichal.service.assembler.PersonAssembler;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class OccasionServiceIntegrationTest {

    @Autowired
    private PersonService personService;
    @Autowired
    private OccasionService occasionService;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private UserContext userContext;

    final String PERSON_NAME = "personName";
    final String PERSON_LASTNAME = "personLastName";
    final String OCCASION_NAME = "occasionName";
    final String OCCASION_UPDATED_NAME = "occasionUpdatedName";
    final LocalDateTime NOW = LocalDateTime.now();


    @Test
    void shouldCreatePerson_withMultipleOccasions() {

        //given
        var personCreateDto = PersonAssembler.assemblePersonCreateDto(PERSON_NAME, PERSON_LASTNAME);
        var persistedPerson = personService.createPerson(personCreateDto);
        var occasionOneCreateDto = OccasionAssembler.assembleOccasion(OCCASION_NAME, NOW, persistedPerson.getId());
        occasionService.createOccasion(occasionOneCreateDto);
        var occasionTwoCreateDto = OccasionAssembler.assembleOccasion(OCCASION_NAME, NOW, persistedPerson.getId());
        occasionService.createOccasion(occasionTwoCreateDto);

        //when
        entityManager.flush();
        entityManager.clear();
        var occasions = occasionService.getOccasionsByPersonId(persistedPerson.getId());

        //then
        assertEquals(2, occasions.size());
    }

    @Test
    void shouldUpdateOccasion() {
        //given
        var personCreateDto = PersonAssembler.assemblePersonCreateDto(PERSON_NAME, PERSON_LASTNAME);
        var persistedPerson = personService.createPerson(personCreateDto);
        var occasionOneCreateDto = OccasionAssembler.assembleOccasion(OCCASION_NAME, NOW, persistedPerson.getId());
        var occasion = occasionService.createOccasion(occasionOneCreateDto);

        var updatedOccasion = OccasionUpdateDto.builder()
                .id(occasion.getId())
                .name(OCCASION_UPDATED_NAME)
                .date(occasion.getDate())
                .personId(occasion.getPersonId())
                .build();

        //when
        entityManager.flush();
        entityManager.clear();
        userContext.setUserId(persistedPerson.getOwner());
        var occasionUpdated = occasionService.updateOccasion(updatedOccasion);

        //then
        assertEquals(OCCASION_UPDATED_NAME, occasionUpdated.getName());
    }
}