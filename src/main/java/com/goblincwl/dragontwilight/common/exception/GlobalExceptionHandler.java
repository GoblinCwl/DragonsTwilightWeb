package com.goblincwl.dragontwilight.common.exception;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author ☪wl
 * @program dragons-twilight-web
 * @description 全局异常处理
 * @create 2020-06-15 23:14
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    @ResponseBody
    @ExceptionHandler(DtWebException.class)
    public ModelAndView handleDtWebException(DtWebException dtWebException) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("code", dtWebException.getCode());
        modelAndView.addObject("message", StringUtils.isEmpty(dtWebException.getMessage()) ? "无文本消息." : dtWebException.getMessage());
        modelAndView.setViewName("/error");
        return modelAndView;
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object error(Exception exception) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("code", "500");
        modelAndView.addObject("message", "系统异常，请联系管理员。");
        modelAndView.setViewName("/error");
        return modelAndView;
    }

}