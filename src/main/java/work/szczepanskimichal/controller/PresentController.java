package work.szczepanskimichal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import work.szczepanskimichal.model.Occasion;
import work.szczepanskimichal.model.Person;
import work.szczepanskimichal.model.Present;
import work.szczepanskimichal.model.PresentCreateDto;
import work.szczepanskimichal.repository.OccasionRepository;
import work.szczepanskimichal.service.PresentService;

import java.util.UUID;

@RestController()
@RequestMapping("/present")
@RequiredArgsConstructor
public class PresentController {

    private final PresentService presentService;

    @PostMapping()
    public Present createPresent(@RequestBody PresentCreateDto presentDto) {
        return presentService.createPresent(presentDto);
    }

    @GetMapping()
    public Present getPerson(@RequestParam UUID id) {
        return presentService.getPresentById(id);
    }
}
