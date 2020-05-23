package com.goblincwl.dragontwilight.service.base.impl;

import com.goblincwl.dragontwilight.dao.BaseRepository;
import com.goblincwl.dragontwilight.entity.base.WebNavIframe;
import com.goblincwl.dragontwilight.service.base.BaseService;
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
public class BaseServiceImpl implements BaseService {

    private final BaseRepository baseRepository;

    public BaseServiceImpl(BaseRepository baseRepository) {
        this.baseRepository = baseRepository;
    }

    @Override
    public List<WebNavIframe> findNavList(WebNavIframe webNavIframe) {
        //根据slot字段正序排序
        return this.baseRepository.findAll(Example.of(webNavIframe), Sort.by(Sort.Direction.ASC, "slot"));
    }
}
