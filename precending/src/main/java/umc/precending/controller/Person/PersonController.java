package umc.precending.controller.Person;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import umc.precending.dto.person.AddPointClubDto;
import umc.precending.dto.person.AddPointCorporateDto;
import umc.precending.service.PersonService.PersonService;
import umc.precending.response.Response;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/person")
public class PersonController {
    private final PersonService personService;

    @PatchMapping("/add/cheer/corporate")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "개인이 기업에 점수 주기", notes = "개인이 기업에 점수를 주는 로직")
    @ApiImplicitParam(name = "addPointCorporateDto", value = "기업의 이메일을 입력해주세요")
    public void addCorporate(@RequestBody AddPointCorporateDto addPointCorporateDto) {
        personService.addScoreCorporate(addPointCorporateDto.getCorporateId());
    }
    @PatchMapping ("/add/cheer/club")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "개인이 동아리에 점수 주기", notes = "개인이 동아리에 점수를 주는 로직")
    @ApiImplicitParam(name = "addPointClubDto", value = "동아리의 이메일을 입력해주세요")
    public void addClub(@RequestBody AddPointClubDto addPointClubDto) {
        personService.addScoreClub(addPointClubDto.getClubId());
    }


    @GetMapping("/show/myCorporate")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "내가 응원한 기업을 보기", notes = "내가 응원한 기업을 볼 수 있는 로직")
    public Response showMyCorporate(){
        return Response.success(personService.showMyCorporate());
    }

    @GetMapping("/show/myClub")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "내가 응원한 동아리를 보기", notes = "내가 응원한 동아리를 볼 수 있는 로직")
    public Response showMyClub(){
        return Response.success(personService.showMyClub());
    }



    @GetMapping("/show/top5/Corporate")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value="가장 많은 점수를 받은 기업을 보기",notes = "가장 많은 점수를 받은 상위 5개 기업을 볼 수 있는 로직")
    public Response showTop5Corporate(){
        return Response.success(personService.showHighestScoreCorporate());
    }
    @GetMapping("/show/top5/Club")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value="가장 많은 점수를 받은 동아리 보기",notes = "가장 많은 점수를 받은 상위 5개 동아리를 볼 수 있는 로직")
    public Response showTop5Club(){
        return Response.success(personService.showHighestScoreClub());
    }

}