package work.szczepanskimichal.model.occasion;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
public class OccasionCreatedDto {

    private UUID id;
    private UUID owner;
    private String name;
    private LocalDateTime date;
    private UUID personId;

}
