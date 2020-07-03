package com.goblincwl.dragontwilight.yggdrasil.utils.minecraft;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class MCUUIDUtil {
    /**
     * 通过传入玩家名称获取这个玩家的离线无连接符UUID
     * @param playerName 玩家名称
     * @return 返回 UUID 字符串
     */
    public static String getPlayerOfflineNonWhipUUIDStrByPlayerName(String playerName){
        return UUID.nameUUIDFromBytes(("OfflinePlayer:" + playerName).getBytes(StandardCharsets.UTF_8)).toString().replace("-", "");
    }

    /**
     * 通过传入玩家名称获取这个玩家的离线完整UUID
     * @param playerName 玩家名称
     * @return 返回 UUID 字符串
     */
    public static String getPlayerOfflineUUIDStrByPlayerName(String playerName){
        return UUID.nameUUIDFromBytes(("OfflinePlayer:" + playerName).getBytes(StandardCharsets.UTF_8)).toString();
    }

    /**
     * 生成随机的无连接符字符串
     * @return 返回随机的无连接符字符串
     */
    public static String getRandomNonWhipUUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }
}
