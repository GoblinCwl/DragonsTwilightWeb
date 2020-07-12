package cn.goblincwl.dragontwilight.service;

import cn.goblincwl.dragontwilight.entity.primary.WebOptions;

/**
 * @author ☪wl
 * @program dragons-twilight-web
 * @description WEB设置 Service
 * @create 2020-05-26 15:32
 */
public interface WebOptionsService {
    WebOptions findByKey(String key);
}
