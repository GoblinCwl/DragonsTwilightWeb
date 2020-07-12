package cn.goblincwl.dragontwilight.yggdrasil.service;

import cn.goblincwl.dragontwilight.yggdrasil.entity.YggPasswordLink;
import cn.goblincwl.dragontwilight.yggdrasil.entity.YggUser;

import javax.mail.MessagingException;

/**
 * @author ☪wl
 * @program dragons-twilight-web
 * @description
 * @create 2020-07-04 23:51
 */
public interface YggPasswordLinkService {

    void createEmailLink(YggUser yggUser, String serverUrl) throws MessagingException;

    YggPasswordLink findOne(YggPasswordLink yggPasswordLink);

    void delete(YggPasswordLink resultYggPasswordLink);
}
