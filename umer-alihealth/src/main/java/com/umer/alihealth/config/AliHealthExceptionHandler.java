package com.umer.alihealth.config;

import com.taobao.api.ApiException;
import com.umer.alihealth.vo.AliResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 */
@RestControllerAdvice
public class AliHealthExceptionHandler {

    @ExceptionHandler(value = ApiException.class)
    public AliResult handle(ApiException e) {
        return AliResult.fail(e.getErrCode(),e.getErrMsg());
    }

}
