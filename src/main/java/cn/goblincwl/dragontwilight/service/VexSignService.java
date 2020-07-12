package cn.goblincwl.dragontwilight.service;

import cn.goblincwl.dragontwilight.entity.slave.VexSign;

/**
 * @author ☪wl
 * @program dragons-twilight-web
 * @description VexSign数据 Service
 * @create 2020-06-16 22:46
 */
public interface VexSignService {
    VexSign findOne(VexSign vexSign);

    void update(VexSign vexSignResult);
}
