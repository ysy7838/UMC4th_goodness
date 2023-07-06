package umc.precending.repository.member;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.precending.domain.member.Corporate;

import java.util.Optional;

public interface CorporateRepository extends JpaRepository<Corporate, Long> {
    Optional<Corporate> findCorporateByRegistrationNumber(String registrationNumber);
    boolean existsCorporateByRegistrationNumber(String registrationNumber);
}
