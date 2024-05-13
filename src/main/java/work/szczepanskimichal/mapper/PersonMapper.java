package work.szczepanskimichal.mapper;

import org.mapstruct.Mapper;
import work.szczepanskimichal.model.*;

@Mapper(componentModel = "spring")
public abstract class PersonMapper {

    public abstract Person toEntity(PersonCreateDto personCreateDto);

    public abstract PersonCreatedDto toDto(Person person);

}
