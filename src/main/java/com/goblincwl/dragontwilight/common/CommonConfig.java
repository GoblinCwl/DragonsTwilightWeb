package com.goblincwl.dragontwilight.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author ☪wl
 * @program dragons-twilight-web
 * @description 自定义配置项
 * @create 2020-05-24 0:39
 */
@Configuration
@PropertySource(value = "classpath:dragonsTwilight.properties")
public class CommonConfig {

}
