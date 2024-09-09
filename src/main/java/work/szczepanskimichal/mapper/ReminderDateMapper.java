package work.szczepanskimichal.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import work.szczepanskimichal.model.reminder.date.*;

import java.util.Date;
import java.util.TimeZone;

@Mapper(componentModel = "spring")
public abstract class ReminderDateMapper {

    public abstract ReminderDate toEntity(ReminderDateCreateDto reminderDateCreateDto);
    @Mapping(target = "reminder.id", source = "reminderId")
    public abstract ReminderDate toEntity(ReminderDateUpdateDto reminderDateCreateDto);
    @Mapping(target = "reminder.id", source = "reminderId")
    public abstract ReminderDate toEntity(ReminderDateDto persistedReminderDateDto);
    @Mapping(target = "reminderId", source = "reminder.id")
    public abstract ReminderDateDto toDto(ReminderDate reminderDate);

    @Mapping(target = "reminderId", source = "reminder.id")
    @Mapping(target = "date", source = "date", qualifiedByName = "convertToUtcDate")
    public abstract ReminderDateCache toCache(ReminderDate reminderDate);

    @Named("convertToUtcDate")
    protected Date convertToUtcDate(Date date) {
        if (date == null) {
            return null;
        }

        long offset = TimeZone.getDefault().getOffset(date.getTime());
        return new Date(date.getTime() - offset);
    }

}
