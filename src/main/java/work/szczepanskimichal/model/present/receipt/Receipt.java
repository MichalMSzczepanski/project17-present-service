package work.szczepanskimichal.model.present.receipt;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import work.szczepanskimichal.model.present.Present;

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

    @Column(name = "s3key", nullable = false)
    private String awsS3Url;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToOne
    @JoinColumn(
            name = "fk_present_id",
            nullable = false,
            unique = true
    )
    private Present present;

}