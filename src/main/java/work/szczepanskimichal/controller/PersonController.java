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

    @PostMapping("/person/")
    public Person createPerson(@RequestBody Person person) {
        return personService.createPerson(person);
//        example json
//        {
//            "owner": "5df0d7d1-8326-4c38-844e-80de64e8ff84",
//                "name": "John",
//                "lastname": "Doe",
//                "occasions": [
//            {
//                "owner": "5df0d7d1-8326-4c38-844e-80de64e8ff84",
//                    "name": "Birthday",
//                    "date": "2024-05-06T12:00:00",
//                    "presentIdeas": [
//                {
//                    "owner": "5df0d7d1-8326-4c38-844e-80de64e8ff84",
//                        "name": "Gift 1",
//                        "type": "IDEA",
//                        "description": "Description of Gift 1",
//                        "price": 50.00
//                }
//      ]
//            }
//  ]
//        }

    }

    @GetMapping("/person/")
    public Person getPerson(@RequestParam UUID id) {
        return personService.getPersonById(id);
    }
}
