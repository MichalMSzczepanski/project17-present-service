package work.szczepanskimichal.mapper;

import org.mapstruct.Mapper;
import work.szczepanskimichal.model.person.Person;
import work.szczepanskimichal.model.person.PersonCreateDto;
import work.szczepanskimichal.model.person.PersonCreatedDto;

@Mapper(componentModel = "spring")
public abstract class PersonMapper {

    public abstract Person toEntity(PersonCreateDto personCreateDto);

    public abstract PersonCreatedDto toDto(Person person);

}
