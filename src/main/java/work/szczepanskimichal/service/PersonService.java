package work.szczepanskimichal.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import work.szczepanskimichal.model.Person;
import work.szczepanskimichal.repository.PersonRepository;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    Person createPerson(Person personIn) {
        return personRepository.save(personIn);
    }
}
