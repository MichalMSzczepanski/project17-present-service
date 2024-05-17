package work.szczepanskimichal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import work.szczepanskimichal.model.present.Present;

import java.util.List;
import java.util.UUID;

@Repository
public interface PresentRepository extends JpaRepository<Present, UUID> {

    @Query("SELECT p FROM Present p JOIN p.occasion o WHERE o.id = :occasionId")
    List<Present> getPresentsByOccasionId(UUID occasionId);
}
