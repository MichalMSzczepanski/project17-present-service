package work.szczepanskimichal.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import work.szczepanskimichal.context.UserContext;
import work.szczepanskimichal.exception.OwnerMissmatchException;
import work.szczepanskimichal.mapper.PresentMapper;
import work.szczepanskimichal.model.present.Present;
import work.szczepanskimichal.model.present.PresentCreateDto;
import work.szczepanskimichal.model.present.PresentDto;
import work.szczepanskimichal.model.present.PresentUpdateDto;
import work.szczepanskimichal.repository.PresentRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PresentService {

    private final PresentRepository presentRepository;
    private final OccasionService occasionService;
    private final PresentMapper presentMapper;
    private final UserContext userContext;

    public PresentDto createPresent(PresentCreateDto presentDto) {
        validatePresentOwner(presentDto.getOwner());
        var parentOccasion = occasionService.getOccasionById(presentDto.getOccasionId());
        var present = presentMapper.toEntity(presentDto)
                .toBuilder()
                .occasion(parentOccasion)
                .build();
        return presentMapper.toDto(presentRepository.save(present));
    }

    public Present getPresentById(UUID id) {
        return presentRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public List<Present> getPresentsByOccasionId(UUID occasionId) {
        return presentRepository.getPresentsByOccasionId(occasionId);
    }

    public PresentDto updatePresent(PresentUpdateDto presentDto) {
        var present = presentMapper.toEntity(presentDto);
        return presentMapper.toDto(presentRepository.save(present));
    }

    public void deletePresent(UUID presentId) {
        presentRepository.deleteById(presentId);
    }

    private void validatePresentOwner(UUID personOwnerId) {
        if (personOwnerId == userContext.getUserId()) {
            throw new OwnerMissmatchException(userContext.getUserId(), personOwnerId);
        }
    }

}
