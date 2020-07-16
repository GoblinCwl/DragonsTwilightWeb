package cn.goblincwl.dragontwilight.common.exception;

import lombok.Getter;
import org.springframework.validation.BindingResult;

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

    /**
     * 用于参数校验异常处理
     *
     * @param bindingResult 参数校验返回对象
     * @return void
     * @create 2020/7/16 23:05
     * @author ☪wl
     */
    public static void ValidException(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new DtWebException(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
    }
}