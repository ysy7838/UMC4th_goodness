package umc.precending.repository.member;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import umc.precending.domain.member.Club;

import java.util.List;

public interface ClubRepository extends JpaRepository<Club, Long> {
    List<Club> findTop5ByScoreGreaterThanOrderByScoreDesc(int scoreThreshold);//내가 추가한것

    @Query(value = "select c from Club c order by function('RAND')")
    List<Club> findOneByRandom(Pageable pageable);
}
