package cn.goblincwl.dragontwilight.yggdrasil.repository;

import cn.goblincwl.dragontwilight.yggdrasil.entity.YggToken;
import cn.goblincwl.dragontwilight.yggdrasil.entity.YggUser;
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
public interface YggTokenRepository extends JpaRepository<YggToken, String> {
    Optional<YggToken> findByOwner(YggUser yggUser);

    Optional<YggToken> findByAccessTokenAndClientToken(String accessToken, String clientToken);

    Optional<YggToken> findByAccessToken(String accessToken);

    Optional<YggToken> findByUUID(String uuid);
}
