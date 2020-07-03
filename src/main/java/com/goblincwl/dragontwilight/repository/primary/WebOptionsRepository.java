package com.goblincwl.dragontwilight.repository.primary;

import com.goblincwl.dragontwilight.entity.primary.WebOptions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author ☪wl
 * @program dragons-twilight-web
 * @description WEB设置 Repository
 * @create 2020-05-26 15:32
 */
@Repository
public interface WebOptionsRepository extends JpaRepository<WebOptions, Integer> {
}
