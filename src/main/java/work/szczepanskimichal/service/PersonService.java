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

    public Person getPersonById(UUID id) {
        return personRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    public PersonDto updatePerson(PersonUpdateDto personDto) {
        var person = personMapper.toEntity(personDto);
        return personMapper.toDto(personRepository.save(person));
    }

    public void deletePersonById(UUID id) {
        personRepository.deleteById(id);
    }

    private void validatePersonOwner(UUID personOwnerId) {
        if (personOwnerId == userContext.getUserId()) {
            throw new OwnerMissmatchException(userContext.getUserId(), personOwnerId);
        }
    }
}
