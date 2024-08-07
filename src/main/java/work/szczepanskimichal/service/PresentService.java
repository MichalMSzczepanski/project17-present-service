package work.szczepanskimichal.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import work.szczepanskimichal.context.UserContext;
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
    private final ValidationService validationService;

    public PresentDto createPresent(PresentCreateDto presentDto) {
        validationService.validateOwner(presentDto.getOwner(), userContext);
        var parentOccasion =  occasionService.getOccasionById(presentDto.getOccasionId());
        var present = presentMapper.toEntity(presentDto)
                .toBuilder()
                .occasion(parentOccasion)
                .build();
        return presentMapper.toDto(presentRepository.save(present));
    }

    public Present getPresentById(UUID id) {
        //todo check if user is present owner
        //todo fix generic exception
        return presentRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public List<Present> getPresentsByOccasionId(UUID occasionId) {
        //todo check if user is present owner
        //todo fix generic exception
        return presentRepository.getPresentsByOccasionId(occasionId);
    }

    //todo get presents by occasion

    public PresentDto updatePresent(PresentUpdateDto presentDto) {
        validationService.validateOwner(presentDto.getOwner(), userContext);
        var present = presentMapper.toEntity(presentDto);
        return presentMapper.toDto(presentRepository.save(present));
    }

    public void deletePresent(UUID presentId) {
        //todo fix generic exception
        var present = presentRepository.findById(presentId).orElseThrow(RuntimeException::new);
        validationService.validateOwner(present.getOwner(), userContext);
        presentRepository.deleteById(presentId);
    }
}
