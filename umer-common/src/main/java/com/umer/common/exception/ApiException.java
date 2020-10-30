package com.umer.common.exception;


import com.umer.common.api.ResultCode;
import lombok.Data;

/**
 * 自定义API异常
 */
@Data
public class ApiException extends RuntimeException {

    private int code;

    public ApiException() {
        super();
    }

    public ApiException(ResultCode code) {
        super(code.getMessage());
        this.code = code.getCode();
    }

    public ApiException(ResultCode code, String msg) {
        super(msg);
        this.code = code.getCode();
    }

    public ApiException(int code, String message) {
        super(message);
        this.code = code;
    }

    public ApiException(Throwable cause) {
        super(cause);
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
