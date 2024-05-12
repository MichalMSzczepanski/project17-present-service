package work.szczepanskimichal.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import work.szczepanskimichal.model.Occasion;
import work.szczepanskimichal.model.Person;
import work.szczepanskimichal.model.Present;
import work.szczepanskimichal.repository.OccasionRepository;
import work.szczepanskimichal.repository.PersonRepository;
import work.szczepanskimichal.repository.PresentRepository;

import java.util.HashSet;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;
    private final OccasionRepository occasionRepository;
    private final PresentRepository presentRepository;

    public Person createPerson(Person person) {
//        person.toBuilder()
//                .occasions(null)
//                .presents(null)
//                .build();
//        var persistedPerson = personRepository.save(person);
//
//        for (Occasion occasion : person.getOccasions()) {
//            var persistedOCcasion = occasionRepository.save(occasion.toBuilder()
//                    .person(persistedPerson)
//                    .build());
//            for (Present present : occasion.getPresentIdeas()) {
//                presentRepository.save(present.toBuilder()
//                        .occasion(persistedOCcasion)
//                        .build());
//            }
//        }
//        return personRepository.findById(persistedPerson.getId()).get();
//        person.setOccassionsPersonRelationShip();
        return personRepository.save(person);
    }

    public Person getPersonById(UUID id) {
        return personRepository.findById(id).orElseThrow(RuntimeException::new);
    }
}
