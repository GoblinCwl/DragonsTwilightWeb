package com.goblincwl.dragontwilight.yggdrasil.service;

import com.goblincwl.dragontwilight.yggdrasil.entity.YggUser;
import com.goblincwl.dragontwilight.yggdrasil.mcdatamodels.mcuser.MCUser;


/**
 * @author ☪wl
 * @program dragons-twilight-web
 * @description Yggdrasil 用户 Service
 * @create 2020-07-03 22:23
 */
public interface YggUserService {
    YggUser getUserByUsername(String username);

    MCUser getMCUserByYggUser(YggUser yggUser);

    YggUser getUserByProfileUUID(String selectedProfile);

    YggUser getUserByPlayerName(String playerName);

    YggUser register(String email, String password, String playerName, String ipAddr);

    YggUser findOne(YggUser yggUser);

    void update(YggUser yggUser);

    void changePassword(YggUser yggUser, String linkUUID);
}
