package work.szczepanskimichal.service;

import work.szczepanskimichal.model.Occasion;
import work.szczepanskimichal.model.Reminder;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

abstract class OccasionAssembler {

    static Occasion assembleOccasion(String name, LocalDateTime occasionDate, Reminder reminder) {
        return Occasion.builder()
                .owner(UUID.randomUUID())
                .name(name)
                .date(occasionDate)
                .reminders(Set.of(reminder))
                .build();
    }
}
