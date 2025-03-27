package work.szczepanskimichal.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import work.szczepanskimichal.context.UserContext;
import work.szczepanskimichal.exception.DataNotFoundException;
import work.szczepanskimichal.mapper.PresentMapper;
import work.szczepanskimichal.model.present.PresentCreateDto;
import work.szczepanskimichal.model.present.PresentIdeaDto;
import work.szczepanskimichal.model.present.PresentIdeaUpdateDto;
import work.szczepanskimichal.model.present.PresentPurchasedDto;
import work.szczepanskimichal.repository.PresentIdeaRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PresentService {

    private final PresentIdeaRepository presentIdeaRepository;
    private final OccasionService occasionService;
    private final PresentMapper presentMapper;
    private final UserContext userContext;
    private final ValidationService validationService;
    private final PresentConverter presentConverter;

    public PresentIdeaDto createPresentIdea(PresentCreateDto presentDto) {
        validationService.validateOwner(presentDto.getOwner(), userContext);
        var parentOccasion = occasionService.getOccasionById(presentDto.getOccasionId());
        var presentIdea = presentMapper.toEntity(presentDto)
                .toBuilder()
                .occasion(parentOccasion)
                .build();
        return presentMapper.toDto(presentIdeaRepository.save(presentIdea));
    }

    public PresentIdeaDto getPresentDtoById(UUID id) {
        var present = presentIdeaRepository.findById(id).orElseThrow(() -> new DataNotFoundException(id));
        validationService.validateOwner(present.getOwner(), userContext);
        return presentMapper.toDto(present);
    }

    public List<PresentIdeaDto> getPresentsByOccasionId(UUID occasionId) {
        var presents = presentIdeaRepository.getPresentIdeasByOccasionId(occasionId);
        if (presents.isEmpty()) {
            throw new DataNotFoundException(occasionId, "Occasion");
        }
        validationService.validateOwner(presents.get(0).getOwner(), userContext);
        return presents.stream()
                .map(presentMapper::toDto)
                .toList();
    }

    //todo get presents by occasion

    public PresentIdeaDto updatePresent(PresentIdeaUpdateDto presentDto) {
        validationService.validateOwner(presentDto.getOwner(), userContext);
        var present = presentMapper.toEntity(presentDto);
        return presentMapper.toDto(presentIdeaRepository.save(present));
    }

    public void deletePresent(UUID presentId) {
        var presentIdea = presentIdeaRepository.findById(presentId).orElseThrow(() -> new DataNotFoundException(presentId));
        validationService.validateOwner(presentIdea.getOwner(), userContext);
        presentIdeaRepository.deleteById(presentId);
    }

    @Transactional
    public PresentPurchasedDto convertToPresentPurchased(UUID presentId) {
        var presentIdea = presentIdeaRepository.findById(presentId).orElseThrow(() -> new DataNotFoundException(presentId));
        validationService.validateOwner(presentIdea.getOwner(), userContext);
        var presentPurchased = presentConverter.convertToPresentPurchased(presentIdea);
        presentIdeaRepository.deleteById(presentId);
        return presentPurchased;
    }

    //get presentsPurchased by personId
}
