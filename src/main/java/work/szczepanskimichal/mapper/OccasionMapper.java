package work.szczepanskimichal.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import work.szczepanskimichal.model.occasion.Occasion;
import work.szczepanskimichal.model.occasion.OccasionCreateDto;
import work.szczepanskimichal.model.occasion.OccasionDto;
import work.szczepanskimichal.model.occasion.OccasionUpdateDto;

@Mapper(componentModel = "spring")
public abstract class OccasionMapper {

    public abstract Occasion toEntity(OccasionDto occasionCreateDto);
    public abstract Occasion toEntity(OccasionCreateDto occasionCreateDto);
    @Mapping(target = "person.id", source = "personId")
    public abstract Occasion toEntity(OccasionUpdateDto occasionCreateDto);
    @Mapping(target = "personId", source = "person.id")
    public abstract OccasionDto toDto(Occasion occasion);

}
