package com.goblincwl.dragontwilight.service.impl;

import com.goblincwl.dragontwilight.dao.primary.BlessingUsersRepository;
import com.goblincwl.dragontwilight.service.BlessingUsersService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ☪wl
 * @program dragons-twilight-web
 * @description BS用户 ServiceImpl
 * @create 2020-06-14 18:33
 */
@Service
@Transactional
public class BlessingUsersServiceImpl implements BlessingUsersService {

    private final BlessingUsersRepository blessingUsersRepository;

    public BlessingUsersServiceImpl(BlessingUsersRepository blessingUsersRepository) {
        this.blessingUsersRepository = blessingUsersRepository;
    }

    @Override
    public String getPlayerNameByUserName(String userName) {
        return this.blessingUsersRepository.getPlayerNameByUserName(userName);
    }
}
