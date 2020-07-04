package com.goblincwl.dragontwilight.yggdrasil.service.impl;

import com.goblincwl.dragontwilight.common.utils.CommonUtils;
import com.goblincwl.dragontwilight.yggdrasil.entity.YggPasswordLink;
import com.goblincwl.dragontwilight.yggdrasil.entity.YggUser;
import com.goblincwl.dragontwilight.yggdrasil.repository.YggPasswordLinkRepository;
import com.goblincwl.dragontwilight.yggdrasil.service.YggPasswordLinkService;
import com.goblincwl.dragontwilight.yggdrasil.utils.MCUUIDUtil;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author ☪wl
 * @program dragons-twilight-web
 * @description
 * @create 2020-07-04 23:51
 */
@Service
@Transactional
public class YggPasswordLinkServiceImpl implements YggPasswordLinkService {

    private final YggPasswordLinkRepository yggPasswordLinkRepository;

    public YggPasswordLinkServiceImpl(YggPasswordLinkRepository yggPasswordLinkRepository) {
        this.yggPasswordLinkRepository = yggPasswordLinkRepository;
    }

    @Override
    public void createEmailLink(YggUser yggUser, String serverUrl) throws MessagingException {
        StringBuilder contextBuild = new StringBuilder("点击此链接找回密码(10分钟内有效)：\n").append(serverUrl).append("/emailBackPassword/");
        //先查询此用户名下是否已经有过生效链接
        YggPasswordLink byUsername = this.yggPasswordLinkRepository.findByUsername(yggUser.getUsername());
        if (byUsername != null) {
            if (byUsername.getEndTime() > System.currentTimeMillis()) {
                //使用当前有效的UUID地址
                contextBuild.append(byUsername.getUUID());
                //发送邮件
                CommonUtils.sendMail(yggUser.getUsername(), "龙之暮-找回密码", contextBuild.toString());
                return;
            } else {
                //如果过期则删除邮件
                this.yggPasswordLinkRepository.delete(byUsername);
            }
        }
        YggPasswordLink yggPasswordLink = YggPasswordLink.builder()
                .UUID(MCUUIDUtil.getRandomNonWhipUUID())
                .username(yggUser.getUsername())
                //有效期10分钟
                .endTime(System.currentTimeMillis() + 600000)
                .build();
        this.yggPasswordLinkRepository.save(yggPasswordLink);
        contextBuild.append(yggPasswordLink.getUUID());
        //发送邮件
        CommonUtils.sendMail(yggUser.getUsername(), "龙之暮-找回密码", contextBuild.toString());
    }

    @Override
    public YggPasswordLink findOne(YggPasswordLink yggPasswordLink) {
        return this.yggPasswordLinkRepository.findOne(Example.of(yggPasswordLink)).orElse(null);
    }

    @Override
    public void delete(YggPasswordLink resultYggPasswordLink) {
        this.yggPasswordLinkRepository.delete(resultYggPasswordLink);
    }

}
