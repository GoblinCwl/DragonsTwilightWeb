package com.goblincwl.dragontwilight.yggdrasil.service.impl;

import com.goblincwl.dragontwilight.yggdrasil.entity.YggPlayerProfile;
import com.goblincwl.dragontwilight.yggdrasil.mcdatamodels.mcprofile.MCProfile;
import com.goblincwl.dragontwilight.yggdrasil.service.YggPlayerProfileService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

/**
 * @author ☪wl
 * @program dragons-twilight-web
 * @description Yggdrasil 角色 ServiceImpl
 * @create 2020-07-03 22:32
 */
@Service
@Transactional
public class YggPlayerProfileServiceImpl implements YggPlayerProfileService {
    @Override
    public MCProfile getBriefMCProfile(YggPlayerProfile profile) {
        return MCProfile.builder()
                .id(profile.getUUID())
                .name(profile.getName())
                .properties(new ArrayList<>())
                .build();
    }
}
