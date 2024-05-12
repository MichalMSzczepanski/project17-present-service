package work.szczepanskimichal.model;

import jakarta.persistence.*;
import jdk.jfr.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "presents")
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Getter
public class Present {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private UUID owner;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private PresentType type;

    private String description;

    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "fk_occasion_id"
    )
    private Occasion occasion;

    @ManyToOne
    @JoinColumn(name = "fk_person_id"
    )
    private Person person;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

}

