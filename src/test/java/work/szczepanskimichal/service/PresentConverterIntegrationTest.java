package work.szczepanskimichal.service;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import work.szczepanskimichal.context.UserContext;
import work.szczepanskimichal.mapper.PresentMapper;
import work.szczepanskimichal.service.assembler.OccasionAssembler;
import work.szczepanskimichal.service.assembler.PersonAssembler;
import work.szczepanskimichal.service.assembler.PresentAssembler;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class PresentConverterIntegrationTest {

    @Autowired
    private PresentConverter presentConverter;
    @Autowired
    private PersonService personService;
    @Autowired
    private OccasionService occasionService;
    @Autowired
    private PresentService presentService;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private PresentMapper presentMapper;

    final String PERSON_NAME = "personName";
    final String PERSON_LASTNAME = "personLastName";
    final String OCCASION_NAME = "occasionName";
    final String PRESENT_NAME = "presentIdeaName";
    final String PRESENT_DESCRIPTION = "presentIdeaDescription";
    final LocalDateTime NOW = LocalDateTime.now();

    @Test
    @Disabled
    //todo constructing objects fails for personService.getPersonById(presentIdea.getOccasion().getPerson().getId())
    void shouldConvert_presentIdea_to_presentPurchased() {

        //given
        var personCreateDto = PersonAssembler.assemblePersonCreateDto(PERSON_NAME, PERSON_LASTNAME);
        var owner = personCreateDto.getOwner();
        var persistedPerson = personService.createPerson(personCreateDto);
        var occasionCreateDto = OccasionAssembler.assembleOccasion(OCCASION_NAME, NOW, persistedPerson.getId());
        var persistedOccasion = occasionService.createOccasion(occasionCreateDto);
        var presentCreateDto = PresentAssembler.assemblePresentCreateDto(PRESENT_NAME, PRESENT_DESCRIPTION,
                persistedOccasion.getId(), owner);
        var persistedPresentIdea = presentService.createPresentIdea(presentCreateDto);

        //when
        entityManager.flush();
        entityManager.clear();
        var presentIdea = presentMapper.toEntity(persistedPresentIdea);
        var presentPurchased = presentConverter.convertToPresentPurchased(presentIdea);

        //then
        assertNotNull(presentPurchased);
    }

}