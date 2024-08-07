package work.szczepanskimichal.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import work.szczepanskimichal.model.reminder.Reminder;
import work.szczepanskimichal.model.reminder.ReminderCreateDto;
import work.szczepanskimichal.model.reminder.ReminderDto;
import work.szczepanskimichal.model.reminder.ReminderUpdateDto;

@Mapper(componentModel = "spring")
public abstract class ReminderMapper {

    public abstract Reminder toEntity(ReminderCreateDto reminderCreateDto);
    @Mapping(target = "occasion.id", source = "occasionId")
    public abstract Reminder toEntity(ReminderUpdateDto reminderUpdateDto);
    @Mapping(target = "occasionId", source = "occasion.id")
    public abstract ReminderDto toDto(Reminder reminder);

}
