package cn.goblincwl.dragontwilight.common.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ☪wl
 * @program dragons-twilight-web
 * @description 自定义充当实体类的Map
 * @create 2020-06-14 18:11
 */
public class EntityMap extends HashMap<String, Object> implements Serializable {

    private static final long serialVersionUID = -7395909900748895806L;

    public EntityMap() {
    }

    public EntityMap(Map<String, Object> map) {
        for (Entry<String, Object> kvEntry : map.entrySet()) {
            this.put(kvEntry.getKey(), kvEntry.getValue());
        }
    }
}
