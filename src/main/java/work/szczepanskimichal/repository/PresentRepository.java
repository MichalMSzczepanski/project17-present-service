package work.szczepanskimichal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import work.szczepanskimichal.model.present.Present;

import java.util.UUID;

@Repository
public interface PresentRepository extends JpaRepository<Present, UUID> {


}
