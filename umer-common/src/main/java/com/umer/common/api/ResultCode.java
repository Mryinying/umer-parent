package com.umer.common.api;

import lombok.Getter;

/**
 * 枚举了一些常用API操作码
 * Created by macro on 2019/4/19.
 */
@Getter
public enum ResultCode {
    SUCCESS(200, "操作成功"),
    FAILED(500, "操作失败"),
    UNAUTHORIZED(401, "token已经过期"),
    FORBIDDEN(403, "没有相关权限"),
    VALIDATE_FAILED(405, "参数检验失败");
    private int code;
    private String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
