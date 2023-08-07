package umc.precending.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class TwoResponse {
    private int code;
    private boolean success;
    private Result result1;
    private Result result2;

    public static TwoResponse success() {
        return new TwoResponse(0, true, null,null);
    }

    public static <T> TwoResponse success(T data1,T data2) {
        return new TwoResponse(0, true, new Success(data1),new Success(data2));
    }

}
