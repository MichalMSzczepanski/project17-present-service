package work.szczepanskimichal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import work.szczepanskimichal.model.occasion.Occasion;
import work.szczepanskimichal.model.occasion.OccasionCreateDto;
import work.szczepanskimichal.model.occasion.OccasionCreatedDto;
import work.szczepanskimichal.service.OccasionService;

import java.util.UUID;

@RestController
@RequestMapping("/occasion")
@RequiredArgsConstructor
public class OccasionController {

    private final OccasionService occasionService;

    @PostMapping()
    public ResponseEntity<OccasionCreatedDto> createOccasion(@RequestBody OccasionCreateDto occasionDto) {
        return ResponseEntity.ok(occasionService.createOccasion(occasionDto));
    }

    @GetMapping()
    public Occasion getOccasionById(@RequestParam("id") UUID id) {
        return occasionService.getOccasionById(id);
    }
}
