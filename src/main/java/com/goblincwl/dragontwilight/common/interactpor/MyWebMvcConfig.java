package com.goblincwl.dragontwilight.common.interactpor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author ☪wl
 * @program dragons-twilight-web
 * @description 自定义拦截器
 * @create 2020-05-26 17:07
 */
@Configuration
public class MyWebMvcConfig implements WebMvcConfigurer {

    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            //注册拦截器
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(new LoginHandlerInterceptor()).addPathPatterns("/webManager/**")
                        .excludePathPatterns("/webManager/login**");
            }

        };

    }
}
