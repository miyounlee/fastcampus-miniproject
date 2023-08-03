package com.application.miniproject._core.error.exception;

import lombok.Getter;
import com.application.miniproject._core.util.ApiUtils;
import org.springframework.http.HttpStatus;

@Getter
public class Exception400 extends RuntimeException {
    private String key;
    private String value;

    public Exception400(String key, String value) {
        super(key+" : "+value);
        this.key = key;
        this.value = value;
    }

    public ApiUtils.ApiResult<?> body(){
        return ApiUtils.error(getMessage(), HttpStatus.BAD_REQUEST);
    }

    public HttpStatus status(){
        return HttpStatus.BAD_REQUEST;
    }
}
