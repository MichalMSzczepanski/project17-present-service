package work.szczepanskimichal.service;

import work.szczepanskimichal.model.Occasion;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

abstract class OccasionAssembler {

    static Occasion assembleOccasion(String name, LocalDateTime occasionDate) {
        return Occasion.builder()
                .owner(UUID.randomUUID())
                .name(name)
                .date(occasionDate)
                .build();
    }
}
