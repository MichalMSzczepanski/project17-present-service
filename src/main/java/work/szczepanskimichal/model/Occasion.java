package work.szczepanskimichal.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "occasions")
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Getter
//@JsonPropertyOrder({"id", "owner", "name", "date", "person", "presentIdeas", "createdAt"})
public class Occasion {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private UUID owner;

    @Column(nullable = false)
    private String name;

    private LocalDateTime date;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(
            name = "fk_person_id",
            nullable = false
    )
    @JsonIgnore
    private Person person;

    @OneToMany(
            mappedBy = "occasion",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<Present> presentIdeas = new HashSet<>();

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

//    @JsonCreator
//    public Occasion(@JsonProperty("presentIdeas") Set<Present> presents) {
//        if (presents != null) {
//            this.presentIdeas = presents.stream()
//                    .map(p -> p.toBuilder()
//                            .occasion(this)
//                            .build()).collect(Collectors.toSet());
//        }
//    }

}
