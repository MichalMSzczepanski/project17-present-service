package work.szczepanskimichal.model.reminder;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder(toBuilder = true)
@Getter
public class ReminderCreateDto {

    private UUID owner;
    private String name;
    private UUID occasionId;
    private Recurrence recurring;
}
