package work.szczepanskimichal.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import work.szczepanskimichal.model.Person;
import work.szczepanskimichal.repository.PersonRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    public Person createPerson(Person person) {
        return personRepository.save(person);
    }

    public Person getPersonById(UUID id) {
        return personRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public void deletePersonById(UUID id) {
        personRepository.deleteById(id);
    }
}
