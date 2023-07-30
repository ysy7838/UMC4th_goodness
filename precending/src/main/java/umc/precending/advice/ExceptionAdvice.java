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
import umc.precending.exception.category.CategoryDuplicateException;
import umc.precending.exception.category.CategoryNotFoundException;
import umc.precending.exception.email.AuthNumNotCorrectException;
import umc.precending.exception.image.ImageNotSupportedException;
import umc.precending.exception.member.MemberDuplicateException;
import umc.precending.exception.member.MemberLoginFailureException;
import umc.precending.exception.member.MemberNotFoundException;
import umc.precending.exception.post.PostAuthException;
import umc.precending.exception.post.PostNewsNotSupportedException;
import umc.precending.exception.post.PostNotFoundException;
import umc.precending.exception.post.PostVerifyException;
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

    @ExceptionHandler(ImageNotSupportedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response imageNotSupportedException(ImageNotSupportedException e) {
        return Response.failure(400, e.getMessage());
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


    @ExceptionHandler(MemberWrongTypeException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Response MemberWrongTypeException(){
        return Response.failure(409,"club,corporate가 헷갈리신것 같습니다");
    }


    @ExceptionHandler(CategoryDuplicateException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Response categoryDuplicateException() {
        return Response.failure(409, "이미 존재하는 카테고리입니다.");
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response categoryNotFoundException() {
        return Response.failure(404, "카테고리를 찾을 수 없습니다.");
    }

    @ExceptionHandler(PostNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response postNotFoundException() {
        return Response.failure(404, "조건에 부합하는 게시글을 찾지 못하였습니다.");
    }

    @ExceptionHandler(PostAuthException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response postAuthException() {
        return Response.failure(400, "사용자가 게시글 작성자가 일치하지 않습니다.");
    }

    @ExceptionHandler(PostVerifyException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response postVerifyException() {
        return Response.failure(400, "인증이 불가능한 게시글이거나, 이미 인증된 게시글입니다.");
    }

    @ExceptionHandler(PostNewsNotSupportedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response postNewsNotSupportedException(PostNewsNotSupportedException e) {
        return Response.failure(400, e.getMessage());
    }

}
