package work.szczepanskimichal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import work.szczepanskimichal.model.Occasion;
import work.szczepanskimichal.model.OccasionCreateDto;
import work.szczepanskimichal.model.Person;
import work.szczepanskimichal.repository.OccasionRepository;
import work.szczepanskimichal.service.OccasionService;
import work.szczepanskimichal.service.PersonService;

import java.util.UUID;

@RestController
@RequestMapping("/occasion")
@RequiredArgsConstructor
public class OccasionController {

    private final OccasionService occasionService;

    @PostMapping()
    public Occasion createOccasion(@RequestBody OccasionCreateDto occasionDto) {
        return occasionService.createOccasion(occasionDto);
    }

    @GetMapping()
    public Occasion getOccasionById(@RequestParam("id") UUID id) {
        return occasionService.getOccasionById(id);
    }
}
