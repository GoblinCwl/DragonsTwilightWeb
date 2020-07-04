package com.goblincwl.dragontwilight.yggdrasil.repository;

import com.goblincwl.dragontwilight.yggdrasil.entity.YggUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author ☪wl
 * @program dragons-twilight-web
 * @description Yggdrasil 用户 Repository
 * @create 2020-07-03 22:24
 */
@Repository
public interface YggUserRepository extends JpaRepository<YggUser, String> {
    YggUser findByUsername(String username);

}
