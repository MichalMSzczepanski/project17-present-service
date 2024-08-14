package work.szczepanskimichal.service.assembler;

import work.szczepanskimichal.model.occasion.OccasionCreateDto;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class OccasionAssembler {

    public static OccasionCreateDto assembleOccasion(String name, LocalDateTime occasionDate, UUID personId) {
        return OccasionCreateDto.builder()
                .owner(UUID.randomUUID())
                .name(name)
                .date(occasionDate)
                .personId(personId)
                .build();
    }
}
