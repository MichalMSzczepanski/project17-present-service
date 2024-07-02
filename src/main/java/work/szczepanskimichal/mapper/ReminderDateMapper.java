package work.szczepanskimichal.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import work.szczepanskimichal.model.reminder.date.ReminderDate;
import work.szczepanskimichal.model.reminder.date.ReminderDateCreateDto;
import work.szczepanskimichal.model.reminder.date.ReminderDateCreatedDto;

@Mapper(componentModel = "spring")
public abstract class ReminderDateMapper {

    public abstract ReminderDate toEntity(ReminderDateCreateDto reminderDateCreateDto);

    @Mapping(target = "reminderId", source = "reminderDate.id")
    public abstract ReminderDateCreatedDto toDto(ReminderDate reminderDate);

}
