package umc.precending.controller.email;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import umc.precending.dto.email.EmailCheckDto;
import umc.precending.dto.email.EmailRequestDto;
import umc.precending.response.Response;
import umc.precending.service.email.MailService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MailController {
    private final MailService mailService;


    @PostMapping("/mail/Send")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value="이메일 요청",notes = "이메일에 인증번호 요청을 위한 logic")
    @ApiImplicitParam(name="emailDto",value = "이메일 인증번호 위해서 필요합니다!")
    public Response mailSend(@RequestBody @Valid EmailRequestDto emailDto){
        mailService.joinEmail(emailDto.getEmail());
        return Response.success("5분안에 인증번호를 넣어주세요!");
    }
    @PostMapping("/mail/auth/check")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value="인증번호 요청",notes = "이메일 인증번호가 맞는지 확인하는 logic")
    @ApiImplicitParam(name = "emailCheckDto",value = "이메일과 인증번호 대조를 위해 필요합니다")
    public Response AuthCheck(@RequestBody @Valid EmailCheckDto emailCheckDto){
        mailService.CheckAuthNum(emailCheckDto.getEmail(),emailCheckDto.getAuthNum());
        return Response.success("인증되었습니다");
    }


}
