package umc.precending.repository.member;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.precending.domain.member.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findMemberByUsername(String username);
    Optional<Member> findByEmail(String email);
}
