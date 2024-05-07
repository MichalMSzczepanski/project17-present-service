package work.szczepanskimichal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import work.szczepanskimichal.model.Reminder;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReminderRepository extends JpaRepository<Reminder, UUID> {

//    @Query(value = "SELECT r FROM Reminder r " +
//            "LEFT JOIN FETCH r.occasion o " +
////            "JOIN FETCH o.person p " +
//            "WHERE r.id = :reminderId")
//    Optional<Reminder> fetchById(@Param("reminderId") UUID reminderId);

}
