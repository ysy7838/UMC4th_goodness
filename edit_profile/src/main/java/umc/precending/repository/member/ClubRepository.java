package umc.precending.repository.member;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.precending.domain.member.Club;

import java.util.List;

public interface ClubRepository extends JpaRepository<Club, Long> {
    List<Club> findTop5ByScoreGreaterThanOrderByScoreDesc(int scoreThreshold);//내가 추가한것
}
