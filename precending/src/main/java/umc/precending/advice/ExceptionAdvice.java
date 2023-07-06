package umc.precending.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import umc.precending.exception.member.MemberDuplicateException;
import umc.precending.exception.member.MemberLoginFailureException;
import umc.precending.exception.member.MemberNotFoundException;
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
}
