package umc.precending.repository.member;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.precending.domain.member.Club;

public interface ClubRepository extends JpaRepository<Club, Long> {
}
