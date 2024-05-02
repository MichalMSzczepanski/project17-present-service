package work.szczepanskimichal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import work.szczepanskimichal.model.Person;

import java.util.UUID;

@Repository
public interface PersonRepository extends JpaRepository<Person, UUID> {


}
