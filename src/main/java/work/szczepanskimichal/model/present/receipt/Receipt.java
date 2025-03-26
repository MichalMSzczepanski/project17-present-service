package work.szczepanskimichal.model.present.receipt;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import work.szczepanskimichal.model.present.PresentIdea;
import work.szczepanskimichal.model.present.PresentPurchased;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "receipts")
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Getter
public class Receipt {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "imageUrl", nullable = false)
    private String imageUrl;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToOne
    @JoinColumn(name = "fk_present_idea_id", unique = true)
    private PresentIdea presentIdea;

    @OneToOne
    @JoinColumn(name = "fk_present_purchased_id", unique = true)
    private PresentPurchased presentPurchased;

}