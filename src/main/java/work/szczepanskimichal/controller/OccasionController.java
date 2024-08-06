package work.szczepanskimichal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import work.szczepanskimichal.model.occasion.Occasion;
import work.szczepanskimichal.model.occasion.OccasionCreateDto;
import work.szczepanskimichal.model.occasion.OccasionDto;
import work.szczepanskimichal.model.occasion.OccasionUpdateDto;
import work.szczepanskimichal.service.OccasionService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/present/occasion")
@RequiredArgsConstructor
public class OccasionController {

    private final OccasionService occasionService;

    @PostMapping()
    public ResponseEntity<OccasionDto> createOccasion(@RequestBody OccasionCreateDto occasionDto) {
        return ResponseEntity.ok(occasionService.createOccasion(occasionDto));
    }

    @GetMapping("/{occasionId}")
    public Occasion getOccasionById(@PathVariable("occasionId") UUID occasionId) {
        return occasionService.getOccasionById(occasionId);
    }

    @GetMapping("/{personId}")
    public List<Occasion> getOccasionsByPerson(@PathVariable("personId") UUID personId) {
        return occasionService.getOccasionsByPersonId(personId);
    }

    @PatchMapping()
    public ResponseEntity<OccasionDto> updateOccasion(@RequestBody OccasionUpdateDto occasionDto) {
        return ResponseEntity.ok(occasionService.updateOccasion(occasionDto));
    }

    @DeleteMapping("/{occasionId}")
    public void deleteOccasion(@PathVariable("occasionId") UUID occasionId) {
        occasionService.deleteOccasion(occasionId);
    }
}
