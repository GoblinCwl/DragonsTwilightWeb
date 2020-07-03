package com.goblincwl.dragontwilight.yggdrasil.service.impl;

import com.goblincwl.dragontwilight.yggdrasil.entity.MinecraftToken;
import com.goblincwl.dragontwilight.yggdrasil.entity.YggPlayerProfile;
import com.goblincwl.dragontwilight.yggdrasil.entity.YggUser;
import com.goblincwl.dragontwilight.yggdrasil.repository.MinecraftTokenRepository;
import com.goblincwl.dragontwilight.yggdrasil.service.MinecraftTokenService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * @author ☪wl
 * @program dragons-twilight-web
 * @description
 * @create 2020-07-03 22:38
 */
@Service
@Transactional
public class MinecraftTokenServiceImpl implements MinecraftTokenService {

    private final MinecraftTokenRepository minecraftTokenRepository;

    public MinecraftTokenServiceImpl(MinecraftTokenRepository minecraftTokenRepository) {
        this.minecraftTokenRepository = minecraftTokenRepository;
    }

    @Override
    public void createToken(YggUser yggUser, String accessToken, String clientToken, YggPlayerProfile selectedProfile) {
        this.minecraftTokenRepository.findByOwner(yggUser).ifPresent(this.minecraftTokenRepository::delete);

        MinecraftToken minecraftToken = MinecraftToken.builder()
                .accessToken(accessToken)
                .clientToken(clientToken)
                .expiredTime(System.currentTimeMillis() + 259200000L)
                .owner(yggUser)
                .playerProfile(selectedProfile)
                .UUID(UUID.randomUUID().toString().replace("-", ""))
                .build();
        this.minecraftTokenRepository.save(minecraftToken);
    }
}
