package work.szczepanskimichal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import work.szczepanskimichal.model.present.PresentCreateDto;
import work.szczepanskimichal.model.present.PresentIdeaDto;
import work.szczepanskimichal.model.present.PresentIdeaUpdateDto;
import work.szczepanskimichal.model.present.PresentPurchasedDto;
import work.szczepanskimichal.service.PresentService;

import java.util.List;
import java.util.UUID;

@RestController()
@RequestMapping("/v1/present")
@RequiredArgsConstructor
public class PresentController {

    private final PresentService presentService;

    @PostMapping()
    public ResponseEntity<PresentIdeaDto> createPresentIdea(@RequestBody PresentCreateDto presentDto) {
        return ResponseEntity.ok(presentService.createPresentIdea(presentDto));
    }

    @GetMapping("/{presentId}")
    public PresentIdeaDto getPresentIdea(@PathVariable UUID presentId) {
        return presentService.getPresentDtoById(presentId);
    }

    @GetMapping("/byoccasion/{occasionId}")
    public List<PresentIdeaDto> getPresentIdeasByOccasion(@PathVariable UUID occasionId) {
        return presentService.getPresentsByOccasionId(occasionId);
    }

    //todo get presents by personId


    //todo get presents by type

    @PatchMapping()
    public ResponseEntity<PresentIdeaDto> updatePresentIdea(@RequestBody PresentIdeaUpdateDto presentDto) {
        return ResponseEntity.ok(presentService.updatePresent(presentDto));
    }

    @DeleteMapping("/{presentId}")
    public void deletePresentIdea(@PathVariable UUID presentId) {
        presentService.deletePresent(presentId);
    }

    @PostMapping("/{presentId}/convertToPurchased")
    public ResponseEntity<PresentPurchasedDto> convertToPresentPurchased(@PathVariable UUID presentId) {
        return ResponseEntity.ok(presentService.convertToPresentPurchased(presentId));
    }
}
