package cn.goblincwl.dragontwilight.yggdrasil.repository;

import cn.goblincwl.dragontwilight.yggdrasil.entity.YggPasswordLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author ☪wl
 * @program dragons-twilight-web
 * @description
 * @create 2020-07-04 23:53
 */
@Repository
public interface YggPasswordLinkRepository extends JpaRepository<YggPasswordLink, String> {
    YggPasswordLink findByUsername(String userName);
}
