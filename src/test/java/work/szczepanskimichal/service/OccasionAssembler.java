package work.szczepanskimichal.service;

import work.szczepanskimichal.model.Occasion;
import work.szczepanskimichal.model.Reminder;

import java.time.LocalDateTime;
import java.util.Set;

abstract class OccasionAssembler {

    static Occasion assembleOccasion(String name, LocalDateTime occasionDate, Reminder reminder) {
        return Occasion.builder()
                .name(name)
                .date(occasionDate)
                .reminders(Set.of(reminder))
                .build();
    }
}
