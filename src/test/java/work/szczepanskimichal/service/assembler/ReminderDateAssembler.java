package work.szczepanskimichal.service.assembler;

import work.szczepanskimichal.model.reminder.date.ReminderDateCreateDto;

import java.util.Date;
import java.util.UUID;

public abstract class ReminderDateAssembler {

    public static ReminderDateCreateDto AssembleReminderDateCreateDto(Date date,
                                                                      UUID reminderId) {
        return ReminderDateCreateDto.builder()
                .date(date)
                .reminderId(reminderId)
                .build();
    }
}
