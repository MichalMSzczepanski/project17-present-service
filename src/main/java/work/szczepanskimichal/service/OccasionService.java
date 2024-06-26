package work.szczepanskimichal.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import work.szczepanskimichal.mapper.OccasionMapper;
import work.szczepanskimichal.model.occasion.Occasion;
import work.szczepanskimichal.model.occasion.OccasionCreateDto;
import work.szczepanskimichal.model.occasion.OccasionCreatedDto;
import work.szczepanskimichal.repository.OccasionRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OccasionService {

    private final OccasionRepository occasionRepository;
    private final PersonService personService;
    private final OccasionMapper occasionMapper;

    public OccasionCreatedDto createOccasion(OccasionCreateDto occasionDto) {
        var parentPerson = personService.getPersonById(occasionDto.getPersonId());
        var occasion = occasionMapper.toEntity(occasionDto)
                .toBuilder()
                .person(parentPerson)
                .build();
        return occasionMapper.toDto(occasionRepository.save(occasion));
    }

    public Occasion getOccasionById(UUID id) {
        return occasionRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public List<Occasion> getOccasionsByPersonId(UUID personId) {
        return occasionRepository.findAllByPersonId(personId);
    }
}
