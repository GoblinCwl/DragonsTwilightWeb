package cn.goblincwl.dragontwilight.yggdrasil.repository;

import cn.goblincwl.dragontwilight.yggdrasil.entity.YggUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * @author ☪wl
 * @program dragons-twilight-web
 * @description Yggdrasil 用户 Repository
 * @create 2020-07-03 22:24
 */
@Repository
public interface YggUserRepository extends JpaRepository<YggUser, String> {
    YggUser findByUsername(String username);

    YggUser findByUUID(String UUID);

    YggUser findByPlayerName(String playerName);
}
