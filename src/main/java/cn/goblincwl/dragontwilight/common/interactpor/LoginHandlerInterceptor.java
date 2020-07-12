package cn.goblincwl.dragontwilight.common.interactpor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ☪wl
 * @program dragons-twilight-web
 * @description 登录拦截器
 * @create 2020-05-26 17:06
 */
//注册拦截器   要注册拦截器必须实现HandlerInterceptor
public class LoginHandlerInterceptor implements HandlerInterceptor {

    //在目标方法执行前
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //   获得session中携带的值,判断
        Object isLogin = request.getSession().getAttribute("isLogin");
        //判断如名字为空说明没登录拦截
        if (isLogin == null) {
            //放入错误消息
            request.setAttribute("msg", "请先登录");
            request.getRequestDispatcher("/webManager/loginPage").forward(request, response);
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}