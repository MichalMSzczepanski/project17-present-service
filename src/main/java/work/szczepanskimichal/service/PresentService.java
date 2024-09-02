package work.szczepanskimichal.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import work.szczepanskimichal.context.UserContext;
import work.szczepanskimichal.exception.DataNotFoundException;
import work.szczepanskimichal.mapper.PresentMapper;
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
    private final ValidationService validationService;

    public PresentDto createPresent(PresentCreateDto presentDto) {
        validationService.validateOwner(presentDto.getOwner(), userContext);
        var parentOccasion = occasionService.getOccasionById(presentDto.getOccasionId());
        var present = presentMapper.toEntity(presentDto)
                .toBuilder()
                .occasion(parentOccasion)
                .build();
        return presentMapper.toDto(presentRepository.save(present));
    }

    public PresentDto getPresentDtoById(UUID id) {
        var present = presentRepository.findById(id).orElseThrow(() -> new DataNotFoundException(id));
        validationService.validateOwner(present.getOwner(), userContext);
        return presentMapper.toDto(present);
    }

    public List<PresentDto> getPresentsByOccasionId(UUID occasionId) {
        var presents = presentRepository.getPresentsByOccasionId(occasionId);
        if (presents.isEmpty()) {
            throw new DataNotFoundException(occasionId, "Occasion");
        }
        validationService.validateOwner(presents.get(0).getOwner(), userContext);
        return presents.stream()
                .map(presentMapper::toDto)
                .toList();
    }

    //todo get presents by occasion

    public PresentDto updatePresent(PresentUpdateDto presentDto) {
        validationService.validateOwner(presentDto.getOwner(), userContext);
        var present = presentMapper.toEntity(presentDto);
        return presentMapper.toDto(presentRepository.save(present));
    }

    public void deletePresent(UUID id) {
        var present = presentRepository.findById(id).orElseThrow(() -> new DataNotFoundException(id));
        validationService.validateOwner(present.getOwner(), userContext);
        presentRepository.deleteById(id);
    }
}
