package work.szczepanskimichal.model.occasion;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder(toBuilder = true)
@Getter
public class OccasionUpdateDto {

    private UUID id;
    private String name;
    private LocalDateTime date;
    private UUID personId;

}
