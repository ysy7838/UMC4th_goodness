package umc.precending.controller.auth;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import umc.precending.dto.auth.*;
import umc.precending.dto.token.TokenRequestDto;
import umc.precending.response.Response;
import umc.precending.service.auth.AuthService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/auth/sign-up/person")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "회원가입 - 개인", notes = "개인 회원으로 가입하기 위한 로직")
    @ApiImplicitParam(name = "signUpDto", value = "회원가입을 위한 데이터가 담겨진 DTO")
    public void signUp(@RequestBody @Valid MemberPersonSignUpDto signUpDto) {
        authService.signUp(signUpDto);
    }

    @PostMapping("/auth/sign-up/corporate")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "회원가입 - 기업", notes = "단체 회원 - 기업으로 가입하기 위한 로직")
    @ApiImplicitParam(name = "signUpDto", value = "회원가입을 위한 데이터가 담겨진 DTO")
    public void signUp(@RequestBody @Valid MemberCorporateSignUpDto signUpDto) {
        authService.signUp(signUpDto);
    }

    @PostMapping("/auth/sign-up/club")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "회원가입 - 동아리", notes = "단체 회원 - 동아리로 가입하기 위한 로직")
    @ApiImplicitParam(name = "signUpDto", value = "회원가입을 위한 데이터가 담겨진 DTO")
    public void signUp(@RequestBody @Valid MemberClubSignUpDto signUpDto) {
        authService.signUp(signUpDto);
    }

    @PostMapping("/auth/sign-in")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "로그인", notes = "로그인하기 위한 로직")
    @ApiImplicitParam(name = "signInDto", value = "로그인을 위한 데이터가 담겨진 DTO")
    public Response signIn(@RequestBody @Valid MemberSignInDto signInDto) {
        return Response.success(authService.signIn(signInDto));
    }

    @PostMapping("/auth/reissue")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "토큰 재발급", notes = "만료 기간이 완료된 토큰을 재발급하는 로직")
    @ApiImplicitParam(name = "requestDto", value = "토큰 재발급에 필요한 정보들이 담긴 DTO")
    public Response reIssue(@RequestBody @Valid TokenRequestDto requestDto) {
        return Response.success(authService.reIssue(requestDto));
    }

    @PostMapping("/auth/forgot-password")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "비밀번호 링크", notes = "비밀번호 재설정 링크를 메일로 보내주는 로직")
    public ResponseEntity<String> forgotPassword(@RequestParam String email) {
        return new ResponseEntity<>(authService.forgotPassword(email), HttpStatus.OK);
    }

    @PostMapping("/auth/set-password")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "비밀번호 재설정", notes = "비밀번호를 재설정하는 로직")
    public ResponseEntity<String> setPassword(@RequestParam String email, @RequestHeader String newPassword) {
        return new ResponseEntity<>(authService.setPassword(email, newPassword), HttpStatus.OK);
    }
}
