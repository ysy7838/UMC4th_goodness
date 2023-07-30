package umc.precending.service.PersonService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.precending.domain.member.*;
import umc.precending.dto.person.NameScoreClubDto;
import umc.precending.dto.person.NameScoreCorporateDto;
import umc.precending.exception.member.MemberNotFoundException;
import umc.precending.exception.member.MemberWrongTypeException;
import umc.precending.exception.person.PersonAddClubException;
import umc.precending.exception.person.PersonAddCorporateException;
import umc.precending.repository.member.*;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PersonService {
    private final PersonRepository personRepository;
    private final CorporateRepository corporateRepository;
    private final ClubRepository clubRepository;
    private final MemberRepository memberRepository;
    private final Person_CorporateRepository personCorporateRepository;
    private final Person_ClubRepository personClubRepository;
    @Transactional
    public void addScoreCorporate(String CorporateUsername,Member member){
        Member member1=memberRepository.findMemberByUsername(CorporateUsername).orElseThrow(MemberNotFoundException::new);
        Corporate corporate;
        Person person;
        if(member1 instanceof Corporate && member instanceof Person){
            corporate=(Corporate) member1;
            person=(Person)member;
        }
       else{
           throw new MemberWrongTypeException();
        }
        if(!personCorporateRepository.existsByPerson_IdAndCorporate_Id(person.getId(), corporate.getId())){
            person.addMyCorporate(corporate);
        }
        else{
            throw new PersonAddCorporateException();
        }
    }
    @Transactional
    public void addScoreClub(String ClubUsername,Member member){
        Member member1=memberRepository.findMemberByUsername(ClubUsername).orElseThrow(MemberNotFoundException::new);
        Club club;
        Person person;
        if(member1 instanceof Club && member instanceof Person){
            club=(Club)member1;
            person=(Person)member;
        }
        else{
            throw new MemberWrongTypeException();
        }
        if(!personClubRepository.existsByPerson_IdAndClub_Id(person.getId(), club.getId())){
            person.addMyClub(club);
        }
        else{
            throw new PersonAddClubException();
        }
    }

    public List<NameScoreCorporateDto> showMyCorporate(Member member){
       Person person;
       if(member instanceof Person){
           person=(Person)member;
       }
       else{
           throw new MemberWrongTypeException();
       }
        Pageable pageable = PageRequest.of(0, 5);
        List<Person_Corporate> personCorporates=personCorporateRepository.findTop5ByPersonIdOrderByCorporateName(person.getId(),pageable);
        List<NameScoreCorporateDto> result=personCorporates.stream().map(c->new NameScoreCorporateDto(c.getCorporate())).collect(Collectors.toList());
        return result;
    }
    public List<NameScoreClubDto> showMyClub(Member member){
        Person person;
        if(member instanceof Person){
            person=(Person)member;
        }
        else{
            throw new MemberWrongTypeException();
        }
        Pageable pageable = PageRequest.of(0, 5);
        List<Person_Club> personClubs=personClubRepository.findTop5ByPersonIdOrderByClubName(person.getId(),pageable);
        List<NameScoreClubDto> result=personClubs.stream().map(c->new NameScoreClubDto(c.getClub())).collect(Collectors.toList());
        return result;
    }


}
