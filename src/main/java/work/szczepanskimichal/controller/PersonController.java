package work.szczepanskimichal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import work.szczepanskimichal.model.Person;
import work.szczepanskimichal.service.PersonService;

import java.util.UUID;

@RestController
@RequestMapping("/person")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    @PostMapping()
    public Person createPerson(@RequestBody Person person) {
        return personService.createPerson(person);
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
