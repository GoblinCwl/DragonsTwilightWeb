package com.goblincwl.dragontwilight.service.base;

import com.goblincwl.dragontwilight.entity.base.WebNavIframe;

import java.util.List;

/**
 * @author ☪wl
 * @program dragons-twilight-web
 * @description 基础 Service
 * @create 2020-05-22 15:45
 */
public interface BaseService {
    List<WebNavIframe> findNavList(WebNavIframe webNavIframe);
}
