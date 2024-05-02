package work.szczepanskimichal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import work.szczepanskimichal.model.PresentIdea;

import java.util.UUID;

@Repository
public interface PresentIdeaRepository extends JpaRepository<PresentIdea, UUID> {


}
