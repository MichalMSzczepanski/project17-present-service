package work.szczepanskimichal.model.reminder.date;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;
import java.util.UUID;

@Builder(toBuilder = true)
@Getter
public class ReminderDateCreateDto {

    private Date date;
    private UUID reminderId;
}