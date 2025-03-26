package work.szczepanskimichal.model.present;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Builder(toBuilder = true)
@Getter
public class PresentIdeaUpdateDto {

    private UUID id;
    private UUID owner;
    private String name;
    private String description;
    private BigDecimal price;
    private UUID occasionId;

}

