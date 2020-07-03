package com.goblincwl.dragontwilight.yggdrasil.service;

import com.goblincwl.dragontwilight.yggdrasil.entity.YggPlayerProfile;
import com.goblincwl.dragontwilight.yggdrasil.entity.YggUser;

/**
 * @author ☪wl
 * @program dragons-twilight-web
 * @description
 * @create 2020-07-03 22:38
 */
public interface MinecraftTokenService {
    void createToken(YggUser yggUser, String accessToken, String clientToken, YggPlayerProfile selectedProfile);
}
