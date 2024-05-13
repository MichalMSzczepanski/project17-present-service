package work.szczepanskimichal.model;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
@Getter
public class PresentCreatedDto {

    private UUID id;
    private UUID owner;
    private String name;
    private PresentType type;
    private String description;
    private BigDecimal price;
    private UUID occasionId;

}

