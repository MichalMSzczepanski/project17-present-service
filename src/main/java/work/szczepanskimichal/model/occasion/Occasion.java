package work.szczepanskimichal.model.occasion;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import work.szczepanskimichal.model.person.Person;
import work.szczepanskimichal.model.present.Present;
import work.szczepanskimichal.model.reminder.Reminder;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "occasions")
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Getter
public class Occasion {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private UUID owner;

    @Column(nullable = false)
    private String name;

    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(
            name = "fk_person_id",
            nullable = false
    )
    @JsonIgnore
    private Person person;

    @OneToMany(
            mappedBy = "occasion",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<Present> presentIdeas = new HashSet<>();

    @OneToMany(
            mappedBy = "occasion",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<Reminder> reminders = new HashSet<>();

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
