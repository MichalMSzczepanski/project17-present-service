package work.szczepanskimichal.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import work.szczepanskimichal.model.*;

@Mapper(componentModel = "spring")
public abstract class OccasionMapper {

    public abstract Occasion toEntity(OccasionCreateDto occasionCreateDto);

    @Mapping(target = "personId", source = "person.id")
    public abstract OccasionCreatedDto toDto(Occasion occasion);

}
