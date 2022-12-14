package cn.goblincwl.dragontwilight.service.impl;

import cn.goblincwl.dragontwilight.common.exception.DtWebException;
import cn.goblincwl.dragontwilight.repository.primary.WebOptionsRepository;
import cn.goblincwl.dragontwilight.entity.primary.WebOptions;
import cn.goblincwl.dragontwilight.service.WebOptionsService;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
            throw new DtWebException("密码获取失败!");
        }
    }

    @Override
    public List<WebOptions> findList(WebOptions webOptions) {
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("optKey", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("optValue", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("remarks", ExampleMatcher.GenericPropertyMatchers.contains());
        return this.webOptionsRepository.findAll(Example.of(webOptions, exampleMatcher), Sort.by(Sort.Direction.ASC, "id"));
    }

    @Override
    public String save(WebOptions webOptions) {
        this.webOptionsRepository.save(webOptions);
        return "保存成功";
    }

    @Override
    public String delete(WebOptions webOptions) {
        this.webOptionsRepository.delete(webOptions);
        return "删除成功";
    }

    @Override
    public WebOptions findOne(WebOptions webOptions) {
        return this.webOptionsRepository.findOne(Example.of(webOptions)).orElse(null);
    }

    @Override
    public Page<WebOptions> findPage(WebOptions webOptions, Pageable pageable) {
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("optKey", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("optValue", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("remarks", ExampleMatcher.GenericPropertyMatchers.contains());
        return this.webOptionsRepository.findAll(Example.of(webOptions, exampleMatcher), pageable);
    }

}
