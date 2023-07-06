package umc.precending.repository.member;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.precending.domain.member.Person;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findPersonByPhone(String phone);
    boolean existsPersonByPhone(String phone);
}
