package umc.precending.repository.member;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import umc.precending.domain.member.Person_Corporate;

import java.util.List;

public interface Person_CorporateRepository extends JpaRepository<Person_Corporate,Long> {

    @Query("select pc from Person_Corporate pc join fetch pc.corporate where pc.person.id=:personId ORDER BY pc.corporate.score DESC")
    List<Person_Corporate> findTop5ByPersonIdOrderByCorporateScoreDesc(@Param("personId") Long personId, Pageable pageable);

    @Query("select pc from Person_Corporate pc join fetch pc.corporate where pc.person.id=:personId ORDER BY pc.corporate.name")
    List<Person_Corporate> findTop5ByPersonIdOrderByCorporateName(@Param("personId") Long personId,Pageable pageable);

    void deleteByPerson_IdAndCorporate_Id(Long personId,Long corporateId);

}

