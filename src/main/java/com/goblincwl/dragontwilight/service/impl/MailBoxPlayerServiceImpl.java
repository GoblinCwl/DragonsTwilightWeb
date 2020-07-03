package com.goblincwl.dragontwilight.service.impl;

import com.goblincwl.dragontwilight.repository.slave.MailboxPlayerRepository;
import com.goblincwl.dragontwilight.entity.slave.MailboxPlayer;
import com.goblincwl.dragontwilight.service.MailboxPlayerService;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ☪wl
 * @program dragons-twilight-web
 * @description MailBox Player邮箱数据 ServiceImpl
 * @create 2020-06-17 17:53
 */
@Service
@Transactional
public class MailBoxPlayerServiceImpl implements MailboxPlayerService {

    private final MailboxPlayerRepository mailboxPlayerRepository;

    public MailBoxPlayerServiceImpl(MailboxPlayerRepository mailboxPlayerRepository) {
        this.mailboxPlayerRepository = mailboxPlayerRepository;
    }

    @Override
    public List<MailboxPlayer> findList(MailboxPlayer mailboxPlayer) {
        return this.mailboxPlayerRepository.findAll(Example.of(mailboxPlayer));
    }
}
