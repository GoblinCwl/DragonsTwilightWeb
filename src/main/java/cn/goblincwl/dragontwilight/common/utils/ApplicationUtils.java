package cn.goblincwl.dragontwilight.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

/**
 * @author ☪wl
 * @program dragons-twilight-web
 * @description 注入静态Bean工具类
 * @create 2020-06-17 16:44
 */
@Configuration
public class ApplicationUtils implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationUtils.applicationContext = applicationContext;
    }

    public static <T> T get(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    public static Object get(String name) {
        return applicationContext.getBean(name);
    }
}
