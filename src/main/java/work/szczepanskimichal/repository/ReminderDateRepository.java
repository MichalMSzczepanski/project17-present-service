package work.szczepanskimichal.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import work.szczepanskimichal.model.reminder.date.ReminderDate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface ReminderDateRepository extends JpaRepository<ReminderDate, UUID> {

    @Query("SELECT r FROM ReminderDate r JOIN r.reminder o WHERE o.id = :reminderId")
    List<ReminderDate> getReminderDatesByReminderId(UUID reminderId);

    @Query("SELECT r FROM ReminderDate r WHERE r.date BETWEEN :startOfDay AND :endOfDay")
    Set<ReminderDate> getReminderDatesForNext24h(
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("endOfDay") LocalDateTime endOfDay);

    void deleteReminderDatesByDateBefore(LocalDateTime date);
}
