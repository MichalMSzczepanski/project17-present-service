package work.szczepanskimichal.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(name = "persons")
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Getter
@JsonPropertyOrder({"id", "owner", "name", "lastname", "occasions", "presents", "createdAt"})
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private UUID owner;

    @Column(nullable = false)
    private String name;

    private String lastname;

    @OneToMany(
            mappedBy = "person",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<Occasion> occasions = new HashSet<>();

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @JsonCreator
    public Person(@JsonProperty("occasions") Set<Occasion> occasions) {
        if (occasions != null) {
            this.occasions = occasions.stream()
                    .map(o -> o.toBuilder()
                            .person(this)
                            .build()).collect(Collectors.toSet());
        }
    }
}
