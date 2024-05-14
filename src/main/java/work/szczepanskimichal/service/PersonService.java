package work.szczepanskimichal.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import work.szczepanskimichal.mapper.PersonMapper;
import work.szczepanskimichal.model.person.Person;
import work.szczepanskimichal.model.person.PersonCreateDto;
import work.szczepanskimichal.model.person.PersonCreatedDto;
import work.szczepanskimichal.repository.PersonRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;

    public PersonCreatedDto createPerson(PersonCreateDto personDto) {
        var person = personMapper.toEntity(personDto);
        return personMapper.toDto(personRepository.save(person));
    }

    public Person getPersonById(UUID id) {
        return personRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public void deletePersonById(UUID id) {
        personRepository.deleteById(id);
    }
}
