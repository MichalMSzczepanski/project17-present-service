package work.szczepanskimichal.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Builder(toBuilder = true)
@Getter
public class OccasionCreateDto {

    private UUID owner;

    private String name;

    private LocalDateTime date;

    private UUID personId;

}
