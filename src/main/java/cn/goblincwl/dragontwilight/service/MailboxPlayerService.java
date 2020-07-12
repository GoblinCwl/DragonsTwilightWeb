package cn.goblincwl.dragontwilight.service;

import cn.goblincwl.dragontwilight.entity.slave.MailboxPlayer;

import java.util.List;

/**
 * @author ☪wl
 * @program dragons-twilight-web
 * @description MailBox Player邮箱数据 Service
 * @create 2020-06-17 17:53
 */
public interface MailboxPlayerService {
    List<MailboxPlayer> findList(MailboxPlayer mailboxPlayer);
}
