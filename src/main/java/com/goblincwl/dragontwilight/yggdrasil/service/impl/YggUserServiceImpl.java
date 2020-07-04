package com.goblincwl.dragontwilight.yggdrasil.service.impl;

import com.goblincwl.dragontwilight.yggdrasil.entity.YggUser;
import com.goblincwl.dragontwilight.yggdrasil.mcdatamodels.mcprofile.MCProfile;
import com.goblincwl.dragontwilight.yggdrasil.mcdatamodels.mcuser.MCUser;
import com.goblincwl.dragontwilight.yggdrasil.repository.YggUserRepository;
import com.goblincwl.dragontwilight.yggdrasil.service.YggUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

/**
 * @author ☪wl
 * @program dragons-twilight-web
 * @description Yggdrasil 用户 ServiceImpl
 * @create 2020-07-03 22:23
 */
@Service
@Transactional
public class YggUserServiceImpl implements YggUserService {

    private final YggUserRepository yggUserRepository;

    public YggUserServiceImpl(YggUserRepository yggUserRepository) {
        this.yggUserRepository = yggUserRepository;
    }

    @Override
    public YggUser getUserByUsername(String username) {
        return this.yggUserRepository.findByUsername(username);
    }

    @Override
    public MCUser getMCUserByYggUser(YggUser yggUser) {
        if (yggUser == null) {
            return null;
        }

        return MCUser.builder()
                .id(yggUser.getUUID())
                .properties(new ArrayList<>())
                .build();
    }

    @Override
    public YggUser getUserByProfileUUID(String selectedProfile) {
        return this.yggUserRepository.findByUUID(selectedProfile);
    }

    @Override
    public YggUser getUserByPlayerName(String playerName) {
        return this.yggUserRepository.findByPlayerName(playerName);
    }

}
