package umc.precending.repository.member;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.precending.domain.member.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {
    boolean existsPersonByEmail(String email);
}
