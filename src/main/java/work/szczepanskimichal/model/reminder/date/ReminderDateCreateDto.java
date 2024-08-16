package work.szczepanskimichal.model.reminder.date;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder(toBuilder = true)
@Getter
public class ReminderDateCreateDto {

    private UUID id;
    private LocalDateTime date;
    private UUID reminderId;
}