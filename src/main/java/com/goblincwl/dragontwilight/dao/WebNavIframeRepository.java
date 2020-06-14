package com.goblincwl.dragontwilight.dao;

import com.goblincwl.dragontwilight.entity.WebNavIframe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * @author ☪wl
 * @program dragons-twilight-web
 * @description 基础 Repository
 * @create 2020-05-22 15:49
 */
@Repository
public interface WebNavIframeRepository extends JpaRepository<WebNavIframe, Integer> {
}
