package work.szczepanskimichal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import work.szczepanskimichal.model.person.Person;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PersonRepository extends JpaRepository<Person, UUID> {

    @Query("SELECT p FROM Person p " +
            "JOIN FETCH p.occasions o " +
            "JOIN FETCH o.reminders r " +
            "JOIN FETCH r.reminderDates rd " +
            "LEFT JOIN FETCH o.presentIdeas pi " +
            "WHERE rd.id = :reminderDateId")
    Optional<Person> findPersonWithOccasionsAndRemindersByReminderDateId(@Param("reminderDateId") UUID reminderDateId);

}
