package com.goblincwl.dragontwilight.service.impl;

import com.goblincwl.dragontwilight.repository.primary.MinecraftQqPlayerRepository;
import com.goblincwl.dragontwilight.entity.primary.MinecraftQqPlayer;
import com.goblincwl.dragontwilight.service.MinecraftQqPlayerService;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ☪wl
 * @program dragons-twilight-web
 * @description MinecraftQq ServiceImpl
 * @create 2020-05-28 9:52
 */
@Service
@Transactional
public class MinecraftQqPlayerServiceImpl implements MinecraftQqPlayerService {

    private final MinecraftQqPlayerRepository minecraftQqPlayerRepository;

    public MinecraftQqPlayerServiceImpl(MinecraftQqPlayerRepository minecraftQqPlayerRepository) {
        this.minecraftQqPlayerRepository = minecraftQqPlayerRepository;
    }

    @Override
    public MinecraftQqPlayer findOne(MinecraftQqPlayer minecraftQqPlayer) {
        return this.minecraftQqPlayerRepository.findOne(Example.of(minecraftQqPlayer)).orElse(null);
    }

    @Override
    public List<MinecraftQqPlayer> findList(MinecraftQqPlayer minecraftQqPlayer) {
        return this.minecraftQqPlayerRepository.findAll(Example.of(minecraftQqPlayer));
    }
}
