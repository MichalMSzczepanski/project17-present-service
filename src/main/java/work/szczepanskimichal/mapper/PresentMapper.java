package work.szczepanskimichal.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import work.szczepanskimichal.model.present.PresentIdea;
import work.szczepanskimichal.model.present.PresentCreateDto;
import work.szczepanskimichal.model.present.PresentIdeaDto;
import work.szczepanskimichal.model.present.PresentIdeaUpdateDto;

@Mapper(componentModel = "spring")
public abstract class PresentMapper {

    public abstract PresentIdea toEntity(PresentCreateDto presentCreatedDto);

    @Mapping(target = "occasion.id", source = "occasionId")
    public abstract PresentIdea toEntity(PresentIdeaUpdateDto presentUpdatedDto);

    @Mapping(target = "occasionId", source = "occasion.id")
    public abstract PresentIdeaDto toDto(PresentIdea presentIdea);

    @Mapping(target = "occasion.id", source = "occasionId")
    public abstract PresentIdea toEntity(PresentIdeaDto presentIdea);

}
