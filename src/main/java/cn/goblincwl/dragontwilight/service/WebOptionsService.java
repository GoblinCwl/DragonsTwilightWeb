package cn.goblincwl.dragontwilight.service;

import cn.goblincwl.dragontwilight.entity.primary.WebOptions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author ☪wl
 * @program dragons-twilight-web
 * @description WEB设置 Service
 * @create 2020-05-26 15:32
 */
public interface WebOptionsService {
    WebOptions findByKey(String key);

    List<WebOptions> findList(WebOptions webOptions);

    String save(WebOptions webOptions);

    String delete(WebOptions webOptions);

    WebOptions findOne(WebOptions webOptions);

    Page<WebOptions> findPage(WebOptions webOptions, Pageable pageable);
}
