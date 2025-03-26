package work.szczepanskimichal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import work.szczepanskimichal.model.present.PresentIdea;
import work.szczepanskimichal.model.present.PresentPurchased;

import java.util.List;
import java.util.UUID;

@Repository
public interface PresentPurchasedRepository extends JpaRepository<PresentPurchased, UUID> {

}
