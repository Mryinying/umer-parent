package com.umer.auth.exception;

import com.umer.common.api.Result;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局处理Oauth2抛出的异常
 * Created by macro on 2020/7/17.
 */
@RestControllerAdvice
public class Oauth2ExceptionHandler {
    @ExceptionHandler(value = OAuth2Exception.class)
    public Result<String> handleOauth2(OAuth2Exception e) {
        return Result.failed(e.getMessage());
    }
}
