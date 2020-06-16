package com.goblincwl.dragontwilight.dao.slave;

import com.goblincwl.dragontwilight.entity.slave.VexSign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author ☪wl
 * @program dragons-twilight-web
 * @description VexSign2签到数据
 * @create 2020-06-16 22:16
 */
@Repository
public interface VexSignRepository extends JpaRepository<VexSign, Integer> {
}
