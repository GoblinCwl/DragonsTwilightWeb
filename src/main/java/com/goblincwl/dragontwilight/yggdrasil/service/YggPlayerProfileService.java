package com.goblincwl.dragontwilight.yggdrasil.service;

import com.goblincwl.dragontwilight.yggdrasil.entity.YggPlayerProfile;
import com.goblincwl.dragontwilight.yggdrasil.mcdatamodels.mcprofile.MCProfile;

/**
 * @author ☪wl
 * @program dragons-twilight-web
 * @description Yggdrasil 角色 Service
 * @create 2020-07-03 22:32
 */
public interface YggPlayerProfileService {
    MCProfile getBriefMCProfile(YggPlayerProfile profile);
}
