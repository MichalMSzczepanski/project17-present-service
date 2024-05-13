package work.szczepanskimichal.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder(toBuilder = true)
@Getter
public class PresentCreateDto {

    private UUID owner;

    private String name;

    private PresentType type;

    private String description;

    private BigDecimal price;

    private UUID occasionId;

}

