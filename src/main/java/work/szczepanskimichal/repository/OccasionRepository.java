package work.szczepanskimichal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import work.szczepanskimichal.model.occasion.Occasion;

import java.util.List;
import java.util.UUID;

@Repository
public interface OccasionRepository extends JpaRepository<Occasion, UUID> {

    @Query("SELECT o FROM Occasion o JOIN o.person p WHERE p.id = :personId")
    List<Occasion> findAllByPersonId(@Param("personId") UUID personId);
}
