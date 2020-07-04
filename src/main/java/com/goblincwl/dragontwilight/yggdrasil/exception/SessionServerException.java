package com.goblincwl.dragontwilight.yggdrasil.exception;


import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author ☪wl
 * @program dragons-twilight-web
 * @description 自定义异常
 * @create 2020-06-15 23:11
 */

@ResponseStatus(value = HttpStatus.NO_CONTENT, reason = "无效")
public class SessionServerException extends RuntimeException {

}