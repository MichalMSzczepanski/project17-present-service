package work.szczepanskimichal.model.reminder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import work.szczepanskimichal.model.occasion.Occasion;
import work.szczepanskimichal.model.reminder.date.ReminderDate;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "reminders")
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Getter
public class Reminder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private UUID owner;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(
            name = "fk_occasion_id",
            nullable = false)
    @JsonIgnore
    private Occasion occasion;

    @OneToMany(
            mappedBy = "reminder",
            cascade = CascadeType.ALL
    )
    private Set<ReminderDate> reminderDates;

    @Column(nullable = false)
    private boolean recurring;
}
