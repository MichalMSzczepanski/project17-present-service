package work.szczepanskimichal.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import work.szczepanskimichal.context.UserContext;
import work.szczepanskimichal.exception.OwnerMissmatchException;
import work.szczepanskimichal.mapper.OccasionMapper;
import work.szczepanskimichal.model.occasion.Occasion;
import work.szczepanskimichal.model.occasion.OccasionCreateDto;
import work.szczepanskimichal.model.occasion.OccasionDto;
import work.szczepanskimichal.model.occasion.OccasionUpdateDto;
import work.szczepanskimichal.repository.OccasionRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OccasionService {

    private final OccasionRepository occasionRepository;
    private final PersonService personService;
    private final OccasionMapper occasionMapper;
    private final UserContext userContext;

    public OccasionDto createOccasion(OccasionCreateDto occasionDto) {
        validateOccasionOwner(occasionDto.getOwner());
        var parentPerson = personService.getPersonById(occasionDto.getPersonId());
        var occasion = occasionMapper.toEntity(occasionDto)
                .toBuilder()
                .person(parentPerson)
                .build();
        return occasionMapper.toDto(occasionRepository.save(occasion));
    }

    public OccasionDto getOccasionDtoById(UUID id) {
        //todo check if user is occasion owner
        //todo fix generic exception
        return occasionMapper.toDto(occasionRepository.findById(id).orElseThrow(RuntimeException::new));
    }

    public Occasion getOccasionById(UUID id) {
        //todo check if user is occasion owner
        //todo fix generic exception
        return occasionRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public List<OccasionDto> getOccasionsByPersonId(UUID personId) {
        //todo fetch occasions belonging to user
        var occasions = occasionRepository.findAllByPersonId(personId);
        return occasions.stream()
                .map(occasionMapper::toDto)
                .toList();
    }

    public OccasionDto updateOccasion(OccasionUpdateDto occasionDto) {
        var occasion = occasionMapper.toEntity(occasionDto);
        return occasionMapper.toDto(occasionRepository.save(occasion));
    }

    public void deleteOccasion(UUID occasionId) {
        //todo check if user is occasion owner
        //todo check if occasion exists
        occasionRepository.deleteById(occasionId);
    }

    private void validateOccasionOwner(UUID occasionOwnerId) {
        if (occasionOwnerId == userContext.getUserId()) {
            throw new OwnerMissmatchException(userContext.getUserId(), occasionOwnerId);
        }
    }
}
