package umc.precending.repository.personTodayRecommendRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import umc.precending.domain.recommend.PersonTodayRecommend;

import java.util.List;

public interface PersonTodayRecommendRepository extends JpaRepository<PersonTodayRecommend,Long> {
    @Query("select ptr from PersonTodayRecommend ptr join fetch ptr.person p join fetch ptr.recommend r where p.username=:username order by ptr.id")
    List<PersonTodayRecommend> findByPersonName(String username);

    void deleteById(Long id);
}
