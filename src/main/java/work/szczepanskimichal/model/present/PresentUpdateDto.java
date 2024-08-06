package work.szczepanskimichal.model.present;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Builder(toBuilder = true)
@Getter
public class PresentUpdateDto {

    private UUID id;
    private UUID owner;
    private String name;
    private PresentType type;
    private String description;
    private BigDecimal price;
    private UUID occasionId;

}

