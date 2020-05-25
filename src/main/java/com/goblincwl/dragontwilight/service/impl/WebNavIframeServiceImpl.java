package com.goblincwl.dragontwilight.service.impl;

import com.goblincwl.dragontwilight.dao.WebNavIframeRepository;
import com.goblincwl.dragontwilight.entity.WebNavIframe;
import com.goblincwl.dragontwilight.service.WebNavIframeService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ☪wl
 * @program dragons-twilight-web
 * @description 基础 ServiceImpl
 * @create 2020-05-22 15:45
 */
@Service
@Transactional
public class WebNavIframeServiceImpl implements WebNavIframeService {

    private final WebNavIframeRepository webNavIframeRepository;

    public WebNavIframeServiceImpl(WebNavIframeRepository webNavIframeRepository) {
        this.webNavIframeRepository = webNavIframeRepository;
    }

    @Override
    public List<WebNavIframe> findNavList(WebNavIframe webNavIframe) {
        //根据slot字段正序排序
        return this.webNavIframeRepository.findAll(Example.of(webNavIframe), Sort.by(Sort.Direction.ASC, "slot"));
    }
}
