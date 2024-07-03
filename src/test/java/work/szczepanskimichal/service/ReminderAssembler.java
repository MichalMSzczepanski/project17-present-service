package work.szczepanskimichal.service;

import work.szczepanskimichal.model.reminder.ReminderCreateDto;

import java.util.UUID;

abstract class ReminderAssembler {

    static ReminderCreateDto AssembleReminderCreateDto(String name,
                                                       boolean recurring,
                                                       UUID occasionId) {
        return ReminderCreateDto.builder()
                .owner(UUID.randomUUID())
                .name(name)
                .occasionId(occasionId)
                .recurring(recurring)
                .build();
    }
}
