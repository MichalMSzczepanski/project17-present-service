package work.szczepanskimichal.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder(toBuilder = true)
@Getter
public class OccasionCreateDto {

    private UUID owner;

    private String name;

    private LocalDateTime date;

    private UUID personId;

}
