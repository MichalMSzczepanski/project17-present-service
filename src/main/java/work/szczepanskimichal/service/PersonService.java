package work.szczepanskimichal.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import work.szczepanskimichal.context.UserContext;
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
    private final ValidationService validationService;

    public PersonDto createPerson(PersonCreateDto personDto) {
        validationService.validateOwner(personDto.getOwner(), userContext);
        var person = personMapper.toEntity(personDto);
        return personMapper.toDto(personRepository.save(person));
    }

    public PersonDto getPersonDtoById(UUID id) {
        //todo fix generic exception
        return personMapper.toDto(personRepository.findById(id).orElseThrow(RuntimeException::new));
    }

    public Person getPersonById(UUID id) {
        //todo fix generic exception
        return personRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public List<Person> getAllPersons() {
        //todo fetch persons belonging to user
        return personRepository.findAll();
    }

    public PersonDto updatePerson(PersonUpdateDto personDto) {
        validationService.validateOwner(personDto.getOwner(), userContext);
        var person = personMapper.toEntity(personDto);
        return personMapper.toDto(personRepository.save(person));
    }

    public void deletePersonById(UUID id) {
        //todo fix generic exception
        var person = personRepository.findById(id).orElseThrow(RuntimeException::new);
        validationService.validateOwner(person.getOwner(), userContext);
        personRepository.deleteById(id);
    }

}
