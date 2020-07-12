package cn.goblincwl.dragontwilight.yggdrasil.service.cache;

import cn.goblincwl.dragontwilight.yggdrasil.service.cache.dto.BruTimeContainer;
import cn.goblincwl.dragontwilight.yggdrasil.service.cache.dto.SessionContainer;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MemoryCacheService implements CacheService {
    private final Map<String, BruTimeContainer> bruteTimeCount;
    private final Map<String, SessionContainer> sessionContainerMap;
    public MemoryCacheService() {
        bruteTimeCount = new ConcurrentHashMap<>();
        sessionContainerMap = new ConcurrentHashMap<>();
    }

    @Override
    public int getBruteTimeCount(String username) {
        BruTimeContainer container = bruteTimeCount.get(username);
        if(container == null){
            return 0;
        }

        if(container.getExpiredTime() <= System.currentTimeMillis()){
            clearBruteTimeCount(username);
            return 0;
        }

        return container.getCount();
    }

    @Override
    public void increaseBruteTimeCount(String username) {
        BruTimeContainer container = bruteTimeCount.get(username);

        if(container == null){
            // 没有查到，开始新建
            container = BruTimeContainer.builder()
                    .count(1)
                    .expiredTime(System.currentTimeMillis() + 60000)
                    .build();
            bruteTimeCount.put(username, container);
            return;
        }

        if(container.getExpiredTime() <= System.currentTimeMillis()){
            // 更新为计次 1
            container = BruTimeContainer.builder()
                    .count(1)
                    .expiredTime(System.currentTimeMillis() + 60000)
                    .build();
            bruteTimeCount.put(username, container);
            return;
        }

        container.setCount(container.getCount() + 1);
        new Thread(() -> {
            bruteTimeCount.entrySet().removeIf(entry -> entry.getValue().getExpiredTime() <= System.currentTimeMillis());
        }).start();
    }

    @Override
    public void clearBruteTimeCount(String username) {
        bruteTimeCount.remove(username);
    }

    @Override
    public void putJoinSession(String serverId, String accessToken, String selectedProfile, String ip) {
        SessionContainer sessionContainer = SessionContainer.builder()
                .accessToken(accessToken)
                .selectedProfile(selectedProfile)
                .ip(ip)
                .expiredTime(System.currentTimeMillis() + 30000)
                .build();
        sessionContainerMap.put(serverId, sessionContainer);
        new Thread(() -> {
            sessionContainerMap.entrySet().removeIf((entry) -> entry.getValue().getExpiredTime() <= System.currentTimeMillis());
        }).start();
    }

    @Override
    public SessionContainer getJoinSession(String serverId) {
        SessionContainer sessionContainer = sessionContainerMap.get(serverId);
        if(sessionContainer == null){
            return null;
        }

        if(sessionContainer.getExpiredTime() <= System.currentTimeMillis()){
            sessionContainerMap.remove(serverId);
            return null;
        }

        return sessionContainer;
    }

    @Override
    public void removeJoinSession(String serverId) {
        sessionContainerMap.remove(serverId);
    }
}
