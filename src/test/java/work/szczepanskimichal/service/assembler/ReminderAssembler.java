package work.szczepanskimichal.service.assembler;

import work.szczepanskimichal.model.reminder.RecurringPeriods;
import work.szczepanskimichal.model.reminder.ReminderCreateDto;

import java.util.UUID;

public abstract class ReminderAssembler {

    public static ReminderCreateDto AssembleReminderCreateDto(String name,
                                                              RecurringPeriods recurring,
                                                              UUID occasionId) {
        return ReminderCreateDto.builder()
                .owner(UUID.randomUUID())
                .name(name)
                .occasionId(occasionId)
                .recurring(recurring)
                .build();
    }
}
