package umc.precending.repository.member;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.precending.domain.member.Corporate;

import java.util.List;

public interface CorporateRepository extends JpaRepository<Corporate, Long> {
    boolean existsCorporateByRegistrationNumberOrEmail(String registrationNumber, String email);
    List<Corporate> findTop5ByScoreGreaterThanOrderByScoreDesc(int scoreThreshold);//내가 추가한것
}
