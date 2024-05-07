package work.szczepanskimichal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import work.szczepanskimichal.model.Person;
import work.szczepanskimichal.service.PersonService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    @PostMapping("/")
    public Person createPerson(@RequestBody Person person) {
        return personService.createPerson(person);
    }

    @GetMapping("/")
    public Person createPerson(@RequestParam UUID id) {
        return personService.getPersonById(id);
    }
}
