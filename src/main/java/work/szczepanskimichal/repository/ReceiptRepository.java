package work.szczepanskimichal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import work.szczepanskimichal.model.present.receipt.Receipt;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, UUID> {

    Optional<Receipt> findByPresentIdeaId(UUID presentId);

}
