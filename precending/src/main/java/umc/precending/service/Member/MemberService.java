package umc.precending.service.Member;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.precending.domain.member.*;
import umc.precending.dto.member.RecommendShowDto;
import umc.precending.dto.person.NameScoreClubDto;
import umc.precending.dto.person.NameScoreCorporateDto;
import umc.precending.exception.RecommendGoodness.CannotChangeableRecommendException;
import umc.precending.exception.member.MemberNotLoginException;
import umc.precending.repository.member.*;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final RecommendRepository recommendRepository;
    private final MemberTodayRecommendRepository memberTodayRecommendRepository;
    private final CorporateRepository corporateRepository;
    private final ClubRepository clubRepository;
    private final Person_ClubRepository personClubRepository;
    private final Person_CorporateRepository personCorporateRepository;


    @Scheduled(cron = "0 0 8 * * *")
    @Transactional
    public void changeAllMemberRecommend(){
        memberTodayRecommendRepository.deleteAll();
        memberRepository.findAll().forEach(m->m.setMyTodayRecommendList(recommendRepository.selectRandom()));
    }

    @Transactional
    public void addCofrc(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        Member member=memberRepository.findMemberByUsername(authentication.getName()).orElseThrow(MemberNotLoginException::new);
        member.addCofRc();
    }

    @Transactional
    public void makeChangeableRecommendation(){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();
        Member member=memberRepository.findMemberByUsername(username).orElseThrow(MemberNotLoginException::new);
        member.makeChangeableRecommend();
    }

    public int showLeftRandomCount(){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String username= authentication.getName();
        Member member=memberRepository.findMemberByUsername(username).orElseThrow(MemberNotLoginException::new);
        if( !member.isChangeRecommend()){
            return 0;
        }
        else{
            return 1;
        }
    }


    @Transactional
    public void changeMyRecommendAll(){
       /* Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String username= authentication.getName();
        Member member=memberRepository.findMemberByUsername(username).get();
        List<MemberTodayRecommend> memberTodayRecommends=memberTodayRecommendRepository.findByMember_Id(member.getId());
        for (MemberTodayRecommend memberTodayRecommend : memberTodayRecommends) {
            memberTodayRecommendRepository.deleteById(memberTodayRecommend.getId());
        }
        member.setMyTodayRecommendList(recommendRepository.selectRandom());*/
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();
        Member member=memberRepository.findMemberByUsername(username).orElseThrow(MemberNotLoginException::new);
        if(member.isChangeRecommend()){
            List<Recommend> recommends=recommendRepository.selectRandom();
            List<MemberTodayRecommend> oldRecommends=memberTodayRecommendRepository.findByMemberName(username);
            for(int i=0;i<3;i++){
                oldRecommends.get(i).setRecommend(recommends.get(i));
            }
            member.makeNotChangeableRecommend();
        }
        else{
            throw new CannotChangeableRecommendException();
        }

    }



    public RecommendShowDto recommendShowDto(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();
        Member member=memberRepository.findMemberByUsername(username).orElseThrow(MemberNotLoginException::new);
        List<MemberTodayRecommend> memberTodayRecommends=memberTodayRecommendRepository.findByMemberName(username);
        RecommendShowDto recommendShowDto=new RecommendShowDto();
        recommendShowDto.setGood1(memberTodayRecommends.get(0).getRecommend().getGoodness());
        recommendShowDto.setGood2(memberTodayRecommends.get(1).getRecommend().getGoodness());
        recommendShowDto.setGood3(memberTodayRecommends.get(2).getRecommend().getGoodness());
        return recommendShowDto;
       /* return memberRepository.findMemberByUsername(username).map(m->new AdminTestDto2(m.getUsername(),m.getMemberTodayRecommends().get(0).getRecommend().getGoodness(),
                m.getMemberTodayRecommends().get(1).getRecommend().getGoodness(),m.getMemberTodayRecommends().get(2).getRecommend().getGoodness())).get();*/
    }
    public List<NameScoreCorporateDto> showHighestScoreCorporate(){
        List<Corporate> corporates=corporateRepository.findTop5ByScoreGreaterThanOrderByScoreDesc(0);
        List<NameScoreCorporateDto> result=corporates.stream().map(c->new NameScoreCorporateDto(c)).collect(Collectors.toList());
        return result;
    }
    public List<NameScoreClubDto> showHighestScoreClub(){
        List<Club> clubs=clubRepository.findTop5ByScoreGreaterThanOrderByScoreDesc(0);
        List<NameScoreClubDto> result=clubs.stream().map(c->new NameScoreClubDto(c)).collect(Collectors.toList());
        return result;
    }
    @Scheduled(cron = "0 0 8 * * *")
    @Transactional
    public void resetScore(){
        clubRepository.findAll().forEach(c->c.resetScore());
        corporateRepository.findAll().forEach(c->c.resetScore());
        personClubRepository.deleteAll();
        personCorporateRepository.deleteAll();
    }
}