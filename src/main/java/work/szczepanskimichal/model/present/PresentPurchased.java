package work.szczepanskimichal.model.present;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "presents_purchased")
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Getter
public class PresentPurchased {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private UUID owner;

    @Column(nullable = false)
    private String name;

    private String description;

    private BigDecimal price;

    private String image_url;

    @Column(name = "fk_person_id")
    private UUID personId;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

}

