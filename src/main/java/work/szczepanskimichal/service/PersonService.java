package work.szczepanskimichal.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import work.szczepanskimichal.model.Person;
import work.szczepanskimichal.repository.OccasionRepository;
import work.szczepanskimichal.repository.PersonRepository;
import work.szczepanskimichal.repository.PresentRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;
    private final OccasionRepository occasionRepository;
    private final PresentRepository presentRepository;

    public Person createPerson(Person person) {
        return personRepository.save(person);
    }

    public Person getPersonById(UUID id) {
        return personRepository.findById(id).orElseThrow(RuntimeException::new);
    }
}
