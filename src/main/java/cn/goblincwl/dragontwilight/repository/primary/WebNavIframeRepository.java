package cn.goblincwl.dragontwilight.repository.primary;

import cn.goblincwl.dragontwilight.entity.primary.WebNavIframe;
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
