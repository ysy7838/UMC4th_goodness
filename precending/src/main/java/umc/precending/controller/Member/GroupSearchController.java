package umc.precending.controller.Member;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import umc.precending.domain.member.Member;
import umc.precending.exception.member.MemberNotFoundException;
import umc.precending.repository.member.MemberRepository;
import umc.precending.response.Response;
import umc.precending.response.TwoResponse;
import umc.precending.service.Member.MemberSearchService;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/group")
public class GroupSearchController {
    private final MemberSearchService memberSearchService;
    private final MemberRepository memberRepository;


    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "실시간으로 기업,동아리보여주기",notes = "기업,동아리를 검색하는 로직")
    public Response showGroupNameAndType(@RequestParam String keyword){
        return Response.success(memberSearchService.groupNameTypeDtos(keyword));
    }


    @GetMapping("/show/search/club")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value="검색한 Club결과 반환하기",notes = "검색된 동아리 출력하는 로직")
    public Response showClub(@RequestParam String keyword,@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "5")int size){
        return Response.success(memberSearchService.clubListShow(keyword,page,size));
    }
    @GetMapping("/show/search/corporate")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value="검색한 Corporate 결과 반환하기",notes = "검색된 기업 출력하는 로직")
    public Response showCorporate(@RequestParam String keyword,@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "5")int size){
        return Response.success(memberSearchService.corporateListShow(keyword,page,size));
    }
    @GetMapping("/show/search/group")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value="검색한 group 결과 반환하며 로그인 시 최근검색어 저장",notes = "검색된 group 출력하는 로직")
    public TwoResponse showGroup(@RequestParam String keyword, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5")int size){
        if(getMember().isPresent()){
            memberSearchService.postKeyword(getMember().get(),keyword);
        }
        return TwoResponse.success(memberSearchService.clubListShow(keyword,page,size),memberSearchService.corporateListShow(keyword,page,size));
    }

    @GetMapping("/show/recent/search")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value="최근 검색어 보여주기",notes = "최근 검색어 보여주는 로직")
    public Response showRecentSearch(){
        if(getMember().isEmpty()){
            throw new MemberNotFoundException();
        }
        return Response.success(memberSearchService.getSearchList(getMember().get()));
    }

    @PostMapping("/delete/recent/search")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value="최근 검색어 삭제하기",notes = "최근 검색어 삭제하는 로직")
    public void deleteRecentSearch(String value){
        if(getMember().isEmpty()){
            throw new MemberNotFoundException();
        }
        memberSearchService.deleteSearch(getMember().get(),value);
    }

    public Optional<Member> getMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return memberRepository.findMemberByUsername(authentication.getName());
    }

}
