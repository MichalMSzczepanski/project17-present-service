package work.szczepanskimichal.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "persons")
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Getter
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private UUID owner;

    @Column(nullable = false)
    private String name;

    private String lastname;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Occasion> occasions;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PresentIdea> presentsIdeas;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PresentPurchased> presentsPurchased;


}
