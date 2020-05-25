package com.goblincwl.dragontwilight.service;

import com.goblincwl.dragontwilight.entity.WebNavIframe;

import java.util.List;

/**
 * @author ☪wl
 * @program dragons-twilight-web
 * @description 基础 Service
 * @create 2020-05-22 15:45
 */
public interface WebNavIframeService {
    List<WebNavIframe> findNavList(WebNavIframe webNavIframe);
}
