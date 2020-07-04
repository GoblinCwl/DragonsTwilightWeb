package com.goblincwl.dragontwilight.yggdrasil.exception;


import lombok.Builder;
import lombok.Data;

/**
 * @author ☪wl
 * @program dragons-twilight-web
 * @description 自定义异常
 * @create 2020-06-15 23:11
 */
@Builder
@Data
public class YggdrasilException extends RuntimeException {
    private String error;
    private String errorMessage;

    public YggdrasilException(String error, String errorMessage) {
        this.error = error;
        this.errorMessage = errorMessage;
    }

    public YggdrasilException(String message) {
        super(message);
    }
}