package cn.goblincwl.dragontwilight.yggdrasil.service;

import cn.goblincwl.dragontwilight.yggdrasil.entity.YggToken;
import cn.goblincwl.dragontwilight.yggdrasil.entity.YggUser;

/**
 * @author ☪wl
 * @program dragons-twilight-web
 * @description
 * @create 2020-07-03 22:38
 */
public interface YggTokenService {
    void createToken(YggUser yggUser, String accessToken, String clientToken);

    YggToken getTokenByAccessToken(String accessToken);

    YggToken getTokenByBothToken(String accessToken, String clientToken);

    void extendExpiredTime(YggToken yggToken);

    void deleteToken(YggToken yggToken);

    void deleteAllTokensOfUser(YggUser yggUser);
}
