package work.szczepanskimichal.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import work.szczepanskimichal.model.Person;
import work.szczepanskimichal.model.PersonCreateDto;
import work.szczepanskimichal.repository.PersonRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    public Person createPerson(PersonCreateDto personDto) {
        var person = Person.builder()
                .owner(personDto.getOwner())
                .name(personDto.getName())
                .lastname(personDto.getLastname())
                .build();
        return personRepository.save(person);
    }

    public Person getPersonById(UUID id) {
        return personRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public void deletePersonById(UUID id) {
        personRepository.deleteById(id);
    }
}
