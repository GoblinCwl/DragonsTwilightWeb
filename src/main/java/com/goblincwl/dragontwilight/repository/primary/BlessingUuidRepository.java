package com.goblincwl.dragontwilight.repository.primary;

import com.goblincwl.dragontwilight.entity.primary.BlessingUuid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author ☪wl
 * @program dragons-twilight-web
 * @description BS角色UUID数据 Repository
 * @create 2020-06-16 23:05
 */
@ResponseBody
public interface BlessingUuidRepository extends JpaRepository<BlessingUuid, Integer> {
}
