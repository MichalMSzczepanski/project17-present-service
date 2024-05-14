package work.szczepanskimichal.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import work.szczepanskimichal.model.present.Present;
import work.szczepanskimichal.model.present.PresentCreateDto;
import work.szczepanskimichal.model.present.PresentCreatedDto;

@Mapper(componentModel = "spring")
public abstract class PresentMapper {

    public abstract Present toEntity(PresentCreateDto presentCreatedDto);

    @Mapping(target = "occasionId", source = "occasion.id")
    public abstract PresentCreatedDto toDto(Present present);

}
