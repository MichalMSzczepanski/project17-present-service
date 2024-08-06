package work.szczepanskimichal.model.reminder;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder(toBuilder = true)
@Getter
public class ReminderUpdateDto {

    private UUID id;
    private UUID occasionId;
    private String name;
    private boolean recurring;
}