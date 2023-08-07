package umc.precending.repository.member;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import umc.precending.domain.member.Corporate;

import java.util.List;
import java.util.Optional;

public interface CorporateRepository extends JpaRepository<Corporate, Long> {
    Optional<Corporate> findCorporateByRegistrationNumber(String registrationNumber);
    boolean existsCorporateByRegistrationNumber(String registrationNumber);
    List<Corporate> findTop5ByScoreGreaterThanOrderByScoreDesc(int scoreThreshold);//내가 추가한것

    @Query("select c from Corporate c order by function('RAND')")
    List<Corporate> findOneByRandom(Pageable pageable);
}
