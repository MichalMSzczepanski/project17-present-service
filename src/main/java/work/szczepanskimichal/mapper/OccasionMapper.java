package work.szczepanskimichal.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import work.szczepanskimichal.model.occasion.Occasion;
import work.szczepanskimichal.model.occasion.OccasionCreateDto;
import work.szczepanskimichal.model.occasion.OccasionCreatedDto;

@Mapper(componentModel = "spring")
public abstract class OccasionMapper {

    public abstract Occasion toEntity(OccasionCreateDto occasionCreateDto);

    @Mapping(target = "personId", source = "person.id")
    public abstract OccasionCreatedDto toDto(Occasion occasion);

}
