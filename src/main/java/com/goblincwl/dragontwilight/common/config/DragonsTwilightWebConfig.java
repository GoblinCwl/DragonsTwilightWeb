package com.goblincwl.dragontwilight.common.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author ☪wl
 * @program dragons-twilight-web
 * @description 自定义配置项
 * @create 2020-05-24 0:39
 */
@Getter
@Setter
@Configuration
@PropertySource(value = "classpath:properties/dragonsTwilight.properties")
public class DragonsTwilightWebConfig {

    @Value("${adminQq}")
    private String adminQq;

    @Value("${sendEmailCount}")
    private String sendEmailCount;

    @Value("${sendEmailPassword}")
    private String sendEmailPassword;

    @Value("${sendEmailSMTPHost}")
    private String sendEmailSMTPHost;

    @Value("${sendEmailTransportProtocol}")
    private String sendEmailTransportProtocol;

    @Value("${sendEmailSMTPPort}")
    private String sendEmailSMTPPort;

}
