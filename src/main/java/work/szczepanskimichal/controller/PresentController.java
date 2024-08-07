package work.szczepanskimichal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import work.szczepanskimichal.model.present.Present;
import work.szczepanskimichal.model.present.PresentCreateDto;
import work.szczepanskimichal.model.present.PresentDto;
import work.szczepanskimichal.model.present.PresentUpdateDto;
import work.szczepanskimichal.service.PresentService;

import java.util.List;
import java.util.UUID;

@RestController()
@RequestMapping("/v1/present")
@RequiredArgsConstructor
public class PresentController {

    private final PresentService presentService;

    @PostMapping()
    public ResponseEntity<PresentDto> createPresent(@RequestBody PresentCreateDto presentDto) {
        return ResponseEntity.ok(presentService.createPresent(presentDto));
    }

    @GetMapping("/{presentId}")
    public Present getPresent(@PathVariable UUID presentId) {
        return presentService.getPresentById(presentId);
    }

    @GetMapping("/byoccasion/{occasionId}")
    public List<Present> getPresentsByOccasion(@PathVariable UUID occasionId) {
        return presentService.getPresentsByOccasionId(occasionId);
    }

    //todo get presents by personId

    //todo get presents by type

    @PatchMapping()
    public ResponseEntity<PresentDto> updatePresent(@RequestBody PresentUpdateDto presentDto) {
        return ResponseEntity.ok(presentService.updatePresent(presentDto));
    }

    @DeleteMapping("/{presentId}")
    public void deletePresent(@PathVariable UUID presentId) {
        presentService.deletePresent(presentId);
    }
}
