package work.szczepanskimichal.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import work.szczepanskimichal.model.Occasion;
import work.szczepanskimichal.model.OccasionCreateDto;
import work.szczepanskimichal.model.Person;
import work.szczepanskimichal.repository.OccasionRepository;
import work.szczepanskimichal.repository.PersonRepository;
import work.szczepanskimichal.repository.PresentRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OccasionService {

    private final OccasionRepository occasionRepository;
    private final PersonService personService;

    public Occasion createOccasion(OccasionCreateDto occasionDto) {
        var parentPerson = personService.getPersonById(occasionDto.getPersonId());
        var occasion = Occasion.builder()
                .owner(occasionDto.getOwner())
                .name(occasionDto.getName())
                .date(occasionDto.getDate())
                .person(parentPerson)
                .build();
        return occasionRepository.save(occasion);
    }

    public Occasion getOccasionById(UUID id) {
        return occasionRepository.findById(id).orElseThrow(RuntimeException::new);
    }
}
