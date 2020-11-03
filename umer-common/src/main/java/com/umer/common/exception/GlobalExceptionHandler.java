package com.umer.common.exception;

import com.umer.common.api.Result;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = ApiException.class)
    public Result<Object> handle(ApiException e) {
        return Result.failed(e.getCode(),e.getMessage());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result<Object> handleValidException(MethodArgumentNotValidException e) {
        return getResult(e.getBindingResult());
    }

    @ExceptionHandler(value = BindException.class)
    public Result<Object> handleValidException(BindException e) {
        return getResult(e.getBindingResult());
    }

    @ExceptionHandler(value = Exception.class)
    public Result<Object> handleValidException(Exception e) {
        return Result.failed(e.getMessage());
    }

    private Result<Object> getResult(BindingResult bindingResult2) {
        String message = null;
        if (bindingResult2.hasErrors()) {
            FieldError fieldError = bindingResult2.getFieldError();
            if (fieldError != null) {
                message = fieldError.getField()+fieldError.getDefaultMessage();
            }
        }
        return Result.validateFailed(message);
    }
}
