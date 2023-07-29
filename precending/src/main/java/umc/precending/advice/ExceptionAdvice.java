package umc.precending.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import umc.precending.exception.RecommendGoodness.CannotChangeableRecommendException;
import umc.precending.exception.email.AuthNumNotCorrectException;
import umc.precending.exception.member.*;
import umc.precending.exception.person.PersonAddClubException;
import umc.precending.exception.person.PersonAddCorporateException;
import umc.precending.exception.token.TokenNotCorrectException;
import umc.precending.response.Response;

@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response methodArgumentNotValidException(MethodArgumentNotValidException e) {
        return Response.failure(400, e.getBindingResult().getFieldError().getDefaultMessage());
    }

    @ExceptionHandler(MemberNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response memberNotFoundException() {
        return Response.failure(404, "조건에 부합하는 회원을 찾지 못하였습니다.");
    }

    @ExceptionHandler(MemberDuplicateException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Response memberDuplicateException() {
        return Response.failure(409, "입력한 정보로 가입된 사용자가 존재합니다.");
    }

    @ExceptionHandler(MemberLoginFailureException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response memberLoginFailureException() {
        return Response.failure(400, "아이디 혹은 비밀번호가 일치하지 않습니다. 다시 한 번 확인해주세요.");
    }

    @ExceptionHandler(TokenNotCorrectException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response tokenNotCorrectException() {
        return Response.failure(400, "저장된 토큰의 정보와 일치하지 않습니다.");
    }

    @ExceptionHandler(AuthNumNotCorrectException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response AuthNumNotCorrect(){
        return Response.failure(400,"인증 번호가 일치하지 않습니다");
    }

    @ExceptionHandler(PersonAddCorporateException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Response MemberAddCorporateException(){
        return Response.failure(409,"이미 이 기업을 추천하셨습니다");
    }

    @ExceptionHandler(PersonAddClubException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Response MemberAddClubException(){
        return Response.failure(409,"이미 이 동아리를 추천하셨습니다");
    }

    @ExceptionHandler(CannotChangeableRecommendException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Response CannotChangeableRecommendException(){return Response.failure(409,"당신은 기회가 없습니다.");}

    @ExceptionHandler(MemberNotLoginException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Response MemberNotLoginException(){
        return Response.failure(409,"로그인이 되어있지 않습니다");
    }

    @ExceptionHandler(MemberWrongTypeException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Response MemberWrongTypeException(){
        return Response.failure(409,"club,corporate가 헷갈리신것 같습니다");
    }

}
