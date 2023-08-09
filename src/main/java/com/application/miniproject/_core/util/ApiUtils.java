package com.application.miniproject._core.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@ToString
@Getter
public class ApiUtils<T> {
    private Integer status; // 에러시에 의미 있음.
    private String msg; // 에러시에 의미 있음. ex) badRequest
    private T data; // 에러시에는 구체적인 에러 내용 ex) username이 입력되지 않았습니다
    private Integer errCode;

    public ApiUtils(){
        this.status = HttpStatus.OK.value();
        this.msg = "success";
    }
    public ApiUtils(T data){
        this.status = HttpStatus.OK.value();
        this.msg = "success";
        this.errCode = 0;
        this.data = data; // 응답할 데이터 바디
    }

    public static <T> ApiResult<T> success(T response) {
        return new ApiResult<>("success", response, null);
    }

    public static ApiResult<?> error(String message, HttpStatus status) {
        return new ApiResult<>("fail", null, new ApiError(message, status.value()));
    }


    @AllArgsConstructor
    @Getter
    public static class ApiResult<T> {
        private final String status;
        private final T data;
        private final ApiError errCode;
    }

    @AllArgsConstructor
    @Getter
    public static class ApiError {
        private final String message;
        private final int status;
    }
}
