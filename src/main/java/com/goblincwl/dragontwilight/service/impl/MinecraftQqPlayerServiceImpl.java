package com.goblincwl.dragontwilight.service.impl;

import com.goblincwl.dragontwilight.dao.MinecraftQqPlayerRepository;
import com.goblincwl.dragontwilight.entity.MinecraftQqPlayer;
import com.goblincwl.dragontwilight.service.MinecraftQqPlayerService;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
    public String findQqByPlayerName(String playerName) {
        MinecraftQqPlayer minecraftQqPlayer = new MinecraftQqPlayer();
        minecraftQqPlayer.setName(playerName);
        Optional<MinecraftQqPlayer> qqPlayer = this.minecraftQqPlayerRepository.findOne(Example.of(minecraftQqPlayer));
        return qqPlayer.map(MinecraftQqPlayer::getQq).orElse(null);
    }
}
