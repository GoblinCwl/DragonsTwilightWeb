package com.goblincwl.dragontwilight.service.impl;

import com.goblincwl.dragontwilight.dao.primary.BlessingUuidRepository;
import com.goblincwl.dragontwilight.entity.primary.BlessingUuid;
import com.goblincwl.dragontwilight.service.BlessingUuidService;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ☪wl
 * @program dragons-twilight-web
 * @description BS角色UUID数据 ServiceImpl
 * @create 2020-06-16 23:04
 */
@Service
@Transactional
public class BlessingUuidServiceImpl implements BlessingUuidService {

    private final BlessingUuidRepository blessingUuidRepository;

    public BlessingUuidServiceImpl(BlessingUuidRepository blessingUuidRepository) {
        this.blessingUuidRepository = blessingUuidRepository;
    }

    @Override
    public BlessingUuid findONe(BlessingUuid blessingUuid) {
        return this.blessingUuidRepository.findOne(Example.of(blessingUuid)).orElse(null);
    }
}
