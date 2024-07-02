package work.szczepanskimichal.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import work.szczepanskimichal.model.reminder.Reminder;
import work.szczepanskimichal.model.reminder.ReminderCreateDto;
import work.szczepanskimichal.model.reminder.ReminderCreatedDto;

@Mapper(componentModel = "spring")
public abstract class ReminderMapper {

    public abstract Reminder toEntity(ReminderCreateDto presentCreatedDto);

    @Mapping(target = "occasionId", source = "occasion.id")
    public abstract ReminderCreatedDto toDto(Reminder reminder);

}
