package work.szczepanskimichal.service.assembler;

import work.szczepanskimichal.model.reminder.Recurrence;
import work.szczepanskimichal.model.reminder.ReminderCreateDto;

import java.util.UUID;

public abstract class ReminderAssembler {

    public static ReminderCreateDto AssembleReminderCreateDto(String name,
                                                              Recurrence recurring,
                                                              UUID occasionId) {
        return ReminderCreateDto.builder()
                .owner(UUID.fromString("989fc7f9-9c4e-4fca-b96c-603aea01909c"))
                .name(name)
                .occasionId(occasionId)
                .recurring(recurring)
                .build();
    }
}
