package work.szczepanskimichal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import work.szczepanskimichal.model.present.PresentPurchased;

import java.util.UUID;

@Repository
public interface PresentPurchasedRepository extends JpaRepository<PresentPurchased, UUID> {

}
