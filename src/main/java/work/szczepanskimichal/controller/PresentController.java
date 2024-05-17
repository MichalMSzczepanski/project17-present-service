package work.szczepanskimichal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import work.szczepanskimichal.model.present.Present;
import work.szczepanskimichal.model.present.PresentCreateDto;
import work.szczepanskimichal.model.present.PresentCreatedDto;
import work.szczepanskimichal.service.PresentService;

import java.util.List;
import java.util.UUID;

@RestController()
@RequestMapping("/present")
@RequiredArgsConstructor
public class PresentController {

    private final PresentService presentService;

    @PostMapping
    public ResponseEntity<PresentCreatedDto> createPresent(@RequestBody PresentCreateDto presentDto) {
        return ResponseEntity.ok(presentService.createPresent(presentDto));
    }

    @GetMapping()
    public Present getPerson(@RequestParam UUID id) {
        return presentService.getPresentById(id);
    }

    @GetMapping("/all")
    public List<Present> getPresentsByOccasions(@RequestParam UUID occasionId) {
        return presentService.getPresentsByOccasionId(occasionId);
    }
}
