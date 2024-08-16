package work.szczepanskimichal.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import work.szczepanskimichal.context.UserContext;
import work.szczepanskimichal.exception.DataNotFoundException;
import work.szczepanskimichal.mapper.PersonMapper;
import work.szczepanskimichal.model.person.Person;
import work.szczepanskimichal.model.person.PersonCreateDto;
import work.szczepanskimichal.model.person.PersonDto;
import work.szczepanskimichal.model.person.PersonUpdateDto;
import work.szczepanskimichal.repository.PersonRepository;
import work.szczepanskimichal.service.util.ValidationService;

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
        var person = personRepository.findById(id).orElseThrow(() -> new DataNotFoundException(id));
        validationService.validateOwner(person.getOwner(), userContext);
        return personMapper.toDto(person);
    }

    public Person getPersonById(UUID id) {
        return personRepository.findById(id).orElseThrow(() -> new DataNotFoundException(id));
    }

    public Person getPersonWithOccasionsAndRemindersByReminderDateId(UUID reminderDateId) {
        return personRepository.findPersonWithOccasionsAndRemindersByReminderDateId(reminderDateId)
                .orElseThrow(() -> new DataNotFoundException(reminderDateId));
    }

    public List<PersonDto> getAllUserPersons() {
        var persons = personRepository.findAll();
        if (persons.isEmpty()) {
            throw new DataNotFoundException();
        }
        validationService.validateOwner(persons.get(0).getOwner(), userContext);
        return persons.stream()
                .map(personMapper::toDto)
                .toList();
    }

    public PersonDto updatePerson(PersonUpdateDto personDto) {
        validationService.validateOwner(personDto.getOwner(), userContext);
        var person = personMapper.toEntity(personDto);
        return personMapper.toDto(personRepository.save(person));
    }

    public void deletePersonById(UUID id) {
        var person = personRepository.findById(id).orElseThrow(() -> new DataNotFoundException(id));
        validationService.validateOwner(person.getOwner(), userContext);
        personRepository.deleteById(id);
    }

}
