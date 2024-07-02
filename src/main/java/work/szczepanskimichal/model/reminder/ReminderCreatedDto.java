package work.szczepanskimichal.model.reminder;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder(toBuilder = true)
@Getter
public class ReminderCreatedDto {

    private UUID id;
    private UUID owner;
    private UUID occasionId;
    private String name;
    private boolean recurring;
}
