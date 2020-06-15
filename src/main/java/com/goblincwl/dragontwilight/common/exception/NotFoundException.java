package com.goblincwl.dragontwilight.common.exception;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


/**
 * @author ☪wl
 * @program dragons-twilight-web
 * @description 404异常处理
 * @create 2020-06-15 23:34
 */
@Controller
public class NotFoundException implements ErrorController {

    @Override
    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping(value = {"/error"})
    public Object error() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("code", "404");
        modelAndView.addObject("message", "NOT FOUND");
        modelAndView.setViewName("/error");
        return modelAndView;
    }
}
