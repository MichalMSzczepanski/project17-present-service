package work.szczepanskimichal.service;

import work.szczepanskimichal.model.reminder.date.ReminderDateCreateDto;

import java.util.Date;
import java.util.UUID;

abstract class ReminderDateAssembler {

    static ReminderDateCreateDto AssembleReminderDateCreateDto(Date date,
                                                               UUID reminderId) {
        return ReminderDateCreateDto.builder()
                .date(date)
                .reminderId(reminderId)
                .build();
    }
}
