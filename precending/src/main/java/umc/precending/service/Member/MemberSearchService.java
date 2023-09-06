package umc.precending.service.Member;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.precending.domain.member.Member;
import umc.precending.dto.member.GroupNameTypeDto;
import umc.precending.dto.member.GroupShowDto;
import umc.precending.repository.member.MemberRepository;

import java.util.List;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberSearchService {
    private final MemberRepository memberRepository;
    private final RedisTemplate redisTemplate;

    //사용자가 검색 입력을 할 때 유사한 7개의 club,corporate를 반환
    public List<GroupNameTypeDto> groupNameTypeDtos(String keyword){
        Pageable pageable = PageRequest.of(0, 7);
        return memberRepository.searchByGroupName(keyword,keyword,pageable).stream().map(m->new GroupNameTypeDto(m)).collect(Collectors.toList());
    }
    //입력한 검색어에 기반한 기업들을 페이지,size 별로 보여준다
    public List<GroupShowDto> corporateListShow(String keyword, int page, int size){
        Pageable pageable=PageRequest.of(page,size);
        return memberRepository.searchByCorporateName(keyword,keyword,pageable).stream().map(c->new GroupShowDto(c)).collect(Collectors.toList());
    }
    //입력한 검색어에 기반한 동아리들을 페이지,size 별로 보여준다
    public List<GroupShowDto> clubListShow(String keyword,int page,int size){
        Pageable pageable=PageRequest.of(page,size);
        return memberRepository.searchByClubName(keyword,keyword,pageable).stream().map(c->new GroupShowDto(c)).collect(Collectors.toList());
    }
    //최근 검색어를 저장하는 로직
    public void postKeyword(Member member, String keyword){
        if(keyword==null){
            return;
        }
        String key="search::"+member.getUsername();
        ListOperations listOperations=redisTemplate.opsForList();
        for(Object pastKeyword:listOperations.range(key,0,listOperations.size(key))){
            if(String.valueOf(pastKeyword).equals(keyword)){
                return;
            }
        }
        if(listOperations.size(key)<5){
            listOperations.rightPush(key,keyword);
        }
        else if(listOperations.size(key)==5){
            listOperations.leftPop(key);
            listOperations.rightPush(key,keyword);
        }
    }
    //최근 검색어를 보여주는 로직
    public List<String> getSearchList(Member member){
        String key="search::"+member.getUsername();
        ListOperations listOperations=redisTemplate.opsForList();
        return listOperations.range(key,0,listOperations.size(key));
    }
    //최근 검색어를 삭제하는 로직
    public void deleteSearch(Member member,String value){
        String key="search::"+member.getUsername();
        ListOperations listOperations=redisTemplate.opsForList();
        listOperations.remove(key, 1, value);
    }
}
