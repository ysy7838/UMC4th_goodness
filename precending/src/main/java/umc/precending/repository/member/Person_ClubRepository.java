package umc.precending.repository.member;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import umc.precending.domain.member.Person_Club;

import java.util.List;

public interface Person_ClubRepository extends JpaRepository<Person_Club,Long> {
    boolean existsByPerson_IdAndClub_Id(Long personId, Long clubId);
    List<Person_Club> findTop5ByPerson_IdOrderByClub_ScoreDesc(Long id);

    @Query("select pc from Person_Club pc join fetch pc.club where pc.person.id=:personId ORDER BY pc.club.score DESC")
    List<Person_Club> findTop5ByPersonIdOrderByClubScoreDesc(@Param("personId") Long personId, Pageable pageable);

    @Query("select pc from Person_Club pc join fetch pc.club where pc.person.id=:personId ORDER BY pc.club.name")
    List<Person_Club> findTop5ByPersonIdOrderByClubName(@Param("personId") Long personId,Pageable pageable);

    void deleteByPerson_IdAndClub_Id(Long personId,Long clubId);
}
