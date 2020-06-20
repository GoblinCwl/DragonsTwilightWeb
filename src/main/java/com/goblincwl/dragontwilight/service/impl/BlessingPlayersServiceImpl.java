package com.goblincwl.dragontwilight.service.impl;

import com.goblincwl.dragontwilight.dao.primary.BlessingPlayersRepository;
import com.goblincwl.dragontwilight.entity.primary.BlessingPlayers;
import com.goblincwl.dragontwilight.service.BlessingPlayersService;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ☪wl
 * @program dragons-twilight-web
 * @description BS Player 数据 ServiceImpl
 * @create 2020-06-20 22:11
 */
@Service
@Transactional
public class BlessingPlayersServiceImpl implements BlessingPlayersService {

    private final BlessingPlayersRepository blessingPlayersRepository;

    public BlessingPlayersServiceImpl(BlessingPlayersRepository blessingPlayersRepository) {
        this.blessingPlayersRepository = blessingPlayersRepository;
    }

    @Override
    public BlessingPlayers findOne(BlessingPlayers blessingPlayers) {
        return this.blessingPlayersRepository.findOne(Example.of(blessingPlayers)).orElse(null);
    }
}
