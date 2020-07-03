package com.goblincwl.dragontwilight.service.impl;

import com.goblincwl.dragontwilight.repository.primary.BlessingTextureRepository;
import com.goblincwl.dragontwilight.service.BlessingTextureService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ☪wl
 * @program dragons-twilight-web
 * @description BS材质 ServiceImpl
 * @create 2020-06-14 18:34
 */
@Service
@Transactional
public class BlessingTextureServiceImpl implements BlessingTextureService {

    private final BlessingTextureRepository blessingTextureRepository;

    public BlessingTextureServiceImpl(BlessingTextureRepository blessingTextureRepository) {
        this.blessingTextureRepository = blessingTextureRepository;
    }
}
