package work.szczepanskimichal.model.reminder.date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import work.szczepanskimichal.model.reminder.Reminder;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "reminder_dates")
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Getter
public class ReminderDate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(
            name = "fk_reminder_id",
            nullable = false
    )
    @JsonIgnore
    private Reminder reminder;

    @Column(name = "date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime date;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}