package work.szczepanskimichal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import work.szczepanskimichal.model.person.Person;
import work.szczepanskimichal.model.person.PersonCreateDto;
import work.szczepanskimichal.model.person.PersonCreatedDto;
import work.szczepanskimichal.service.PersonService;

import java.util.UUID;

@RestController
@RequestMapping("/person")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    @PostMapping()
    public ResponseEntity<PersonCreatedDto> createPerson(@RequestBody PersonCreateDto personDto) {
        return ResponseEntity.ok(personService.createPerson(personDto));
    }

    @GetMapping()
    public Person getPerson(@RequestParam UUID id) {
        return personService.getPersonById(id);
    }

    @DeleteMapping
    public void deletePerson(@RequestParam UUID id) {
        personService.deletePersonById(id);
    }
}
