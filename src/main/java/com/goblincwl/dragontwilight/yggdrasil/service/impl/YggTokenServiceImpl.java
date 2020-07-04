package com.goblincwl.dragontwilight.yggdrasil.service.impl;

import com.goblincwl.dragontwilight.yggdrasil.entity.YggToken;
import com.goblincwl.dragontwilight.yggdrasil.entity.YggUser;
import com.goblincwl.dragontwilight.yggdrasil.repository.YggTokenRepository;
import com.goblincwl.dragontwilight.yggdrasil.service.YggTokenService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

/**
 * @author ☪wl
 * @program dragons-twilight-web
 * @description
 * @create 2020-07-03 22:38
 */
@Service
@Transactional
public class YggTokenServiceImpl implements YggTokenService {

    private final YggTokenRepository yggTokenRepository;

    public YggTokenServiceImpl(YggTokenRepository yggTokenRepository) {
        this.yggTokenRepository = yggTokenRepository;
    }

    @Override
    public void createToken(YggUser yggUser, String accessToken, String clientToken) {
        Optional<YggToken> optionalYggToken = this.yggTokenRepository.findByOwner(yggUser);
        optionalYggToken.ifPresent(this.yggTokenRepository::delete);

        YggToken yggToken = YggToken.builder()
                .accessToken(accessToken)
                .clientToken(clientToken)
                .expiredTime(System.currentTimeMillis() + 259200000L)
                .owner(yggUser)
                .UUID(UUID.randomUUID().toString().replace("-", ""))
                .build();
        this.yggTokenRepository.save(yggToken);
    }

    @Override
    public YggToken getTokenByAccessToken(String accessToken) {
        YggToken yggToken = this.yggTokenRepository.findByAccessToken(accessToken).orElse(null);
        if (yggToken == null) {
            return null;
        }

        if (yggToken.getExpiredTime() <= System.currentTimeMillis()) {
            this.yggTokenRepository.delete(yggToken);
            return null;
        }

        return yggToken;
    }

    @Override
    public YggToken getTokenByBothToken(String accessToken, String clientToken) {
        YggToken yggToken = this.yggTokenRepository.findByAccessTokenAndClientToken(accessToken, clientToken).orElse(null);
        if (yggToken == null) {
            return null;
        }

        if (yggToken.getExpiredTime() <= System.currentTimeMillis()) {
            this.yggTokenRepository.delete(yggToken);
            return null;
        }

        return yggToken;
    }

    @Override
    public void extendExpiredTime(YggToken yggToken) {
        this.yggTokenRepository.findByUUID(yggToken.getUUID()).ifPresent(item -> {
            if (item.getExpiredTime() - System.currentTimeMillis() <= 86400000L) {
                item.setExpiredTime(System.currentTimeMillis() + 259200000L);
                yggTokenRepository.save(item);
            }
        });
    }

    @Override
    public void deleteToken(YggToken yggToken) {
        if (yggToken == null) {
            return;
        }

        this.yggTokenRepository.delete(yggToken);
    }

    @Override
    public void deleteAllTokensOfUser(YggUser yggUser) {
        this.yggTokenRepository.findByOwner(yggUser).ifPresent(this.yggTokenRepository::delete);
    }
}
