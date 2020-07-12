package cn.goblincwl.dragontwilight.common.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ☪wl
 * @program dragons-twilight-web
 * @description 自定义充当实体类的Map
 * @create 2020-06-14 18:11
 */
public class EntityMap<K, V> extends HashMap<K, V> {

    public EntityMap() {
    }

    public EntityMap(Map<K, V> map) {
        for (Entry<K, V> kvEntry : map.entrySet()) {
            this.put(kvEntry.getKey(), kvEntry.getValue());
        }
    }

    @Override
    public String toString() {
        StringBuilder toString = new StringBuilder();
        for (Entry<K, V> kvEntry : this.entrySet()) {
            toString.append(kvEntry.getKey()).append("=").append(kvEntry.getValue()).append("\n");
        }
        return toString.toString();
    }
}
