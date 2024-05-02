package work.szczepanskimichal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import work.szczepanskimichal.model.Occasion;

import java.util.UUID;

@Repository
public interface OccasionRepository extends JpaRepository<Occasion, UUID> {


}
