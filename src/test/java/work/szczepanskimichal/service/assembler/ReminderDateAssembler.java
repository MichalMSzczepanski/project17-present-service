package work.szczepanskimichal.service.assembler;

import work.szczepanskimichal.model.reminder.date.ReminderDateCreateDto;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class ReminderDateAssembler {

    public static ReminderDateCreateDto AssembleReminderDateCreateDto(LocalDateTime date,
                                                                      UUID reminderId) {
        return ReminderDateCreateDto.builder()
                .date(date)
                .reminderId(reminderId)
                .build();
    }
}
