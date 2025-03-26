package work.szczepanskimichal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import work.szczepanskimichal.model.present.PresentIdea;

import java.util.List;
import java.util.UUID;

@Repository
public interface PresentIdeaRepository extends JpaRepository<PresentIdea, UUID> {

    @Query("SELECT p FROM PresentIdea p JOIN p.occasion o WHERE o.id = :occasionId")
    List<PresentIdea> getPresentIdeasByOccasionId(UUID occasionId);
}
