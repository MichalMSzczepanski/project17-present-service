package work.szczepanskimichal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import work.szczepanskimichal.model.person.Person;
import work.szczepanskimichal.model.person.PersonCreateDto;
import work.szczepanskimichal.model.person.PersonCreatedDto;
import work.szczepanskimichal.service.PersonService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/present/person")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    @PostMapping()
    public ResponseEntity<PersonCreatedDto> createPerson(@RequestBody PersonCreateDto personDto) {
        return ResponseEntity.ok(personService.createPerson(personDto));
    }

    @GetMapping("/{personId}")
    public Person getPerson(@PathVariable UUID personId) {
        return personService.getPersonById(personId);
    }

    @GetMapping("/all")
    public List<Person> getAllPersons() {
        return personService.getAllPersons();
    }

    @DeleteMapping("/{personId}")
    public void deletePerson(@PathVariable UUID personId) {
        personService.deletePersonById(personId);
    }
}
