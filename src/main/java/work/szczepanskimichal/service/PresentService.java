package work.szczepanskimichal.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import work.szczepanskimichal.model.Occasion;
import work.szczepanskimichal.model.Present;
import work.szczepanskimichal.model.PresentCreateDto;
import work.szczepanskimichal.repository.OccasionRepository;
import work.szczepanskimichal.repository.PresentRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PresentService {

    private final PresentRepository presentRepository;
    private final OccasionService occasionService;

    public Present createPresent(PresentCreateDto presentDto) {
        var parentOccasion = occasionService.getOccasionById(presentDto.getOccasionId());
        var present = Present.builder()
                .owner(presentDto.getOwner())
                .name(presentDto.getName())
                .type(presentDto.getType())
                .description(presentDto.getDescription())
                .price(presentDto.getPrice())
                .occasion(parentOccasion)
                .build();
        return presentRepository.save(present);
    }

    public Present getPresentById(UUID id) {
        return presentRepository.findById(id).orElseThrow(RuntimeException::new);
    }
}
