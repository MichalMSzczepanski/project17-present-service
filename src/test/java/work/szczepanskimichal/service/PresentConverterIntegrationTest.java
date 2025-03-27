package work.szczepanskimichal.service;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import work.szczepanskimichal.repository.PresentIdeaRepository;
import work.szczepanskimichal.service.assembler.OccasionAssembler;
import work.szczepanskimichal.service.assembler.PersonAssembler;
import work.szczepanskimichal.service.assembler.PresentAssembler;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

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
    private PresentIdeaRepository presentIdeaRepository;

    final String PERSON_NAME = "personName";
    final String PERSON_LASTNAME = "personLastName";
    final String OCCASION_NAME = "occasionName";
    final String PRESENT_NAME = "presentIdeaName";
    final String PRESENT_DESCRIPTION = "presentIdeaDescription";
    final LocalDateTime NOW = LocalDateTime.now();

    @Test
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
        var presentIdea = presentIdeaRepository.findById(persistedPresentIdea.getId()).get();
        var presentPurchased = presentConverter.convertToPresentPurchased(presentIdea);
        entityManager.flush();
        entityManager.clear();

        //then
        assertNotNull(presentPurchased);
        assertEquals(presentPurchased.getPersonId(), persistedPerson.getId());
    }

}