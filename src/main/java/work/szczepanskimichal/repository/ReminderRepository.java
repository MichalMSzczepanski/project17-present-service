package work.szczepanskimichal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import work.szczepanskimichal.model.reminder.Reminder;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReminderRepository extends JpaRepository<Reminder, UUID> {

    @Query("SELECT r FROM Reminder r JOIN r.occasion o WHERE o.id = :occasionId")
    List<Reminder> getRemindersByOccasionId(UUID occasionId);
}
