package cn.goblincwl.dragontwilight.yggdrasil.service.cache;


import cn.goblincwl.dragontwilight.yggdrasil.service.cache.dto.SessionContainer;

public interface CacheService {
    int getBruteTimeCount(String username);
    void increaseBruteTimeCount(String username);
    void clearBruteTimeCount(String username);
    void putJoinSession(String serverId, String accessToken, String selectedProfile, String ip);
    SessionContainer getJoinSession(String serverId);
    void removeJoinSession(String serverId);
}
