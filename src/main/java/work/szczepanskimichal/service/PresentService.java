package work.szczepanskimichal.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import work.szczepanskimichal.mapper.PresentMapper;
import work.szczepanskimichal.model.Present;
import work.szczepanskimichal.model.PresentCreateDto;
import work.szczepanskimichal.model.PresentCreatedDto;
import work.szczepanskimichal.repository.PresentRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PresentService {

    private final PresentRepository presentRepository;
    private final OccasionService occasionService;
    private final PresentMapper presentMapper;

    public PresentCreatedDto createPresent(PresentCreateDto presentDto) {
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
}
