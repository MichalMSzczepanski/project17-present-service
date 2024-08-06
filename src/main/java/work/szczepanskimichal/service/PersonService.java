package work.szczepanskimichal.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import work.szczepanskimichal.context.UserContext;
import work.szczepanskimichal.exception.OwnerMissmatchException;
import work.szczepanskimichal.mapper.PersonMapper;
import work.szczepanskimichal.model.person.Person;
import work.szczepanskimichal.model.person.PersonCreateDto;
import work.szczepanskimichal.model.person.PersonDto;
import work.szczepanskimichal.model.person.PersonUpdateDto;
import work.szczepanskimichal.repository.PersonRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;
    private final UserContext userContext;

    public PersonDto createPerson(PersonCreateDto personDto) {
        validatePersonOwner(personDto.getOwner());
        var person = personMapper.toEntity(personDto);
        return personMapper.toDto(personRepository.save(person));
    }

    public PersonDto getPersonDtoById(UUID id) {
        //todo check if user is person owner
        //todo fix generic exception
        return personMapper.toDto(personRepository.findById(id).orElseThrow(RuntimeException::new));
    }

    public Person getPersonById(UUID id) {
        //todo check if user is person owner
        //todo fix generic exception
        return personRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public List<Person> getAllPersons() {
        //todo fetch persons belonging to user
        return personRepository.findAll();
    }

    public PersonDto updatePerson(PersonUpdateDto personDto) {
        var person = personMapper.toEntity(personDto);
        return personMapper.toDto(personRepository.save(person));
    }

    public void deletePersonById(UUID id) {
        //todo check if user is person owner
        //todo check if person exists
        personRepository.deleteById(id);
    }

    private void validatePersonOwner(UUID personOwnerId) {
        if (personOwnerId == userContext.getUserId()) {
            throw new OwnerMissmatchException(userContext.getUserId(), personOwnerId);
        }
    }
}
