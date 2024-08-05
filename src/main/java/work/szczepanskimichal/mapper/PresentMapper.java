package work.szczepanskimichal.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import work.szczepanskimichal.model.present.Present;
import work.szczepanskimichal.model.present.PresentCreateDto;
import work.szczepanskimichal.model.present.PresentDto;
import work.szczepanskimichal.model.present.PresentUpdateDto;

@Mapper(componentModel = "spring")
public abstract class PresentMapper {

    public abstract Present toEntity(PresentCreateDto presentCreatedDto);
    public abstract Present toEntity(PresentUpdateDto presentCreatedDto);

    @Mapping(target = "occasionId", source = "occasion.id")
    public abstract PresentDto toDto(Present present);

}
