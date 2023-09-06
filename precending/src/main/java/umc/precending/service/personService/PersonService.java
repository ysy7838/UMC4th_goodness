package umc.precending.service.personService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.precending.domain.member.*;
import umc.precending.dto.person.NameScoreClubDto;
import umc.precending.dto.person.NameScoreCorporateDto;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PersonService {
    //사용자가 응원할 corporate을 선택하고 그 corporate에 점수를 주는 로직
    @Transactional
    public void addScoreCorporate(String CorporateUsername,Member member){

    }
    @Transactional
    public void cancelScoreCorporate(String corporateUsername,Member member){

    }
    //사용자가 응원할 club을 선택하고 그 club에 점수를 주는 로직
    @Transactional
    public void addScoreClub(String ClubUsername,Member member){

    }
    @Transactional
    public void cancelScoreClub(String clubUsername,Member member){

    }
    //로그인한 사용자가 자기가 선택한 corporate를 알파벳 순서대로 상위 5개를 보여준다

    public List<NameScoreCorporateDto> showMyCorporate(Member member){
        return null;
    }

    //로그인한 사용자가 자기가 선택한 club을 알파벳 순서대로 상위 5개를 보여준다
    public List<NameScoreClubDto> showMyClub(Member member){
        return null;
    }
}
