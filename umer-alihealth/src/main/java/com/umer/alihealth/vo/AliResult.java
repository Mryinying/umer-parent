package com.umer.alihealth.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AliResult<T> implements Serializable {

    private String message;
    private T data;
    private String responseCode;

    public AliResult(String message, String responseCode) {
        this.message = message;
        this.responseCode = responseCode;
    }

    public static <T> AliResult<T> fail(String message,String responseCode) {
        return new AliResult<T>(message, responseCode);
    }

    public static <T> AliResult<T> success(T data) {
        return new AliResult<T>("success", data, "500");
    }
}
