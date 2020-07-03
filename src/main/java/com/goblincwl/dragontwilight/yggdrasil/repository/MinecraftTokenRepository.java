package com.goblincwl.dragontwilight.yggdrasil.repository;

import com.goblincwl.dragontwilight.yggdrasil.entity.MinecraftToken;
import com.goblincwl.dragontwilight.yggdrasil.entity.YggUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author ☪wl
 * @program dragons-twilight-web
 * @description
 * @create 2020-07-03 22:41
 */
@Repository
public interface MinecraftTokenRepository extends JpaRepository<MinecraftToken, String> {
    Optional<MinecraftToken> findByOwner(YggUser yggUser);
}
