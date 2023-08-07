package umc.precending.repository.member;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import umc.precending.domain.member.Club;
import umc.precending.domain.member.Corporate;
import umc.precending.domain.member.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findMemberByUsername(String username);

    @Query("select m from Member m where (m.authority='ROLE_CORPORATE' or m.authority='ROLE_CLUB') and m.name LIKE %:keyword% "+
            "ORDER BY (length(:keyword)/length(m.name)) desc, CASE WHEN m.name LIKE :startsWithKeyword% THEN 1 Else 2 End,m.name")
    List<Member> searchByGroupName(@Param("keyword") String keyword, @Param("startsWithKeyword") String startsWithKeyword, Pageable pageable);

    @Query("select m from Member m where (m.authority='ROLE_CORPORATE') and m.name LIKE %:keyword% "+
            "ORDER BY (length(:keyword)/length(m.name)) desc, CASE WHEN m.name LIKE :startsWithKeyword% THEN 1 Else 2 End,m.name")
    List<Corporate> searchByCorporateName(@Param("keyword") String keyword, @Param("startsWithKeyword") String startsWithKeyword, Pageable pageable);

    @Query("select m from Member m where (m.authority='ROLE_CLUB') and m.name LIKE %:keyword% "+
            "ORDER BY (length(:keyword)/length(m.name)) desc, CASE WHEN m.name LIKE :startsWithKeyword% THEN 1 Else 2 End,m.name")
    List<Club> searchByClubName(@Param("keyword") String keyword, @Param("startsWithKeyword") String startsWithKeyword, Pageable pageable);
}
