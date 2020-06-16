package com.goblincwl.dragontwilight.service;

import com.goblincwl.dragontwilight.entity.primary.MinecraftQqPlayer;

import java.util.List;

/**
 * @author ☪wl
 * @program dragons-twilight-web
 * @description MinecraftQq Service
 * @create 2020-05-28 9:52
 */
public interface MinecraftQqPlayerService {

    MinecraftQqPlayer findOne(MinecraftQqPlayer minecraftQqPlayer);

    List<MinecraftQqPlayer> findList(MinecraftQqPlayer minecraftQqPlayer);
}
