package cn.goblincwl.dragontwilight.service.impl;

import cn.goblincwl.dragontwilight.repository.slave.VexSignRepository;
import cn.goblincwl.dragontwilight.entity.slave.VexSign;
import cn.goblincwl.dragontwilight.service.VexSignService;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ☪wl
 * @program dragons-twilight-web
 * @description VexSign数据 ServiceImpl
 * @create 2020-06-16 22:46
 */
@Service
@Transactional
public class VexSignServiceImpl implements VexSignService {

    private final VexSignRepository vexSignRepository;

    public VexSignServiceImpl(VexSignRepository vexSignRepository) {
        this.vexSignRepository = vexSignRepository;
    }

    @Override
    public VexSign findOne(VexSign vexSign) {
        return this.vexSignRepository.findOne(Example.of(vexSign)).orElse(null);
    }

    @Override
    public void update(VexSign vexSignResult) {
        this.vexSignRepository.save(vexSignResult);
    }
}
