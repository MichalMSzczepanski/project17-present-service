package work.szczepanskimichal.model.present;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import work.szczepanskimichal.model.occasion.Occasion;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "present_ideas")
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Getter
public class PresentIdea {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private UUID owner;

    @Column(nullable = false)
    private String name;

    private String description;

    private BigDecimal price;

    @ManyToOne
    @JoinColumn(
            name = "fk_occasion_id",
            nullable = false
    )
    @JsonIgnore
    private Occasion occasion;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

}

