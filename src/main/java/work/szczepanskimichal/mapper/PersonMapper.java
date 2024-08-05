package work.szczepanskimichal.mapper;

import org.mapstruct.Mapper;
import work.szczepanskimichal.model.person.Person;
import work.szczepanskimichal.model.person.PersonCreateDto;
import work.szczepanskimichal.model.person.PersonDto;
import work.szczepanskimichal.model.person.PersonUpdateDto;

@Mapper(componentModel = "spring")
public abstract class PersonMapper {

    public abstract Person toEntity(PersonCreateDto personCreateDto);
    public abstract Person toEntity(PersonUpdateDto personUpdateDto);
    public abstract PersonDto toDto(Person person);

}
