package work.szczepanskimichal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import work.szczepanskimichal.model.reminder.date.ReminderDate;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReminderDateRepository extends JpaRepository<ReminderDate, UUID> {

    @Query("SELECT r FROM ReminderDate r JOIN r.reminder o WHERE o.id = :reminderId")
    List<ReminderDate> getReminderDatesByReminderId(UUID reminderId);
}
