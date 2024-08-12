package work.szczepanskimichal.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import work.szczepanskimichal.model.reminder.date.*;

@Mapper(componentModel = "spring")
public abstract class ReminderDateMapper {

    public abstract ReminderDate toEntity(ReminderDateCreateDto reminderDateCreateDto);
    @Mapping(target = "reminder.id", source = "reminderId")
    public abstract ReminderDate toEntity(ReminderDateUpdateDto reminderDateCreateDto);
    @Mapping(target = "reminderId", source = "reminder.id")
    public abstract ReminderDateDto toDto(ReminderDate reminderDate);

    @Mapping(target = "reminderId", source = "reminder.id")
    public abstract ReminderDateCache toCache(ReminderDate reminderDate);
}
