package cn.goblincwl.dragontwilight.common.exception;

import lombok.Getter;

/**
 * @author ☪wl
 * @program dragons-twilight-web
 * @description 自定义异常
 * @create 2020-06-15 23:11
 */
@Getter
public class DtWebException extends RuntimeException {

    private int code;
    private String message;

    public DtWebException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public DtWebException(String message) {
        this.message = message;
    }
}