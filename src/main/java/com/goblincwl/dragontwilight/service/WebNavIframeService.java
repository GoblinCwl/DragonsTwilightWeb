package com.goblincwl.dragontwilight.service;

import com.goblincwl.dragontwilight.common.entity.EntityMap;
import com.goblincwl.dragontwilight.entity.primary.WebNavIframe;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * @author ☪wl
 * @program dragons-twilight-web
 * @description 基础 Service
 * @create 2020-05-22 15:45
 */
public interface WebNavIframeService {
    List<WebNavIframe> findNavList(WebNavIframe webNavIframe);

    WebNavIframe findNavByUri(String webManagerIndeUri);

    Map<String, Object> getDashboardDate(EntityMap<String, Object> entityMap) throws ParseException, NoSuchMethodException, InvocationTargetException, IllegalAccessException;
}
