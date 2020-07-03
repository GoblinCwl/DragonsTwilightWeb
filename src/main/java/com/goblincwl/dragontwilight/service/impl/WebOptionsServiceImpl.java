package com.goblincwl.dragontwilight.service.impl;

import com.goblincwl.dragontwilight.repository.primary.WebOptionsRepository;
import com.goblincwl.dragontwilight.entity.primary.WebOptions;
import com.goblincwl.dragontwilight.service.WebOptionsService;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author ☪wl
 * @program dragons-twilight-web
 * @description WEB设置 ServiceImpl
 * @create 2020-05-26 15:32
 */
@Service
@Transactional
public class WebOptionsServiceImpl implements WebOptionsService {

    private final WebOptionsRepository webOptionsRepository;

    public WebOptionsServiceImpl(WebOptionsRepository webOptionsRepository) {
        this.webOptionsRepository = webOptionsRepository;
    }

    @Override
    public WebOptions findByKey(String key) {
        WebOptions webOptions = new WebOptions();
        webOptions.setOptKey(key);
        Optional<WebOptions> optional = this.webOptionsRepository.findOne(Example.of(webOptions));
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new RuntimeException("密码获取失败!");
        }
    }
}
