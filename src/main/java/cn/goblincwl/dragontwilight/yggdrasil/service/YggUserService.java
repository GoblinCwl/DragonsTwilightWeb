package cn.goblincwl.dragontwilight.yggdrasil.service;

import cn.goblincwl.dragontwilight.yggdrasil.entity.YggUser;
import cn.goblincwl.dragontwilight.yggdrasil.mcdatamodels.mcuser.MCUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;


/**
 * @author ☪wl
 * @program dragons-twilight-web
 * @description Yggdrasil 用户 Service
 * @create 2020-07-03 22:23
 */
public interface YggUserService {
    MCUser getMCUserByYggUser(YggUser yggUser);

    YggUser register(String email, String password, String playerName, String ipAddr);

    YggUser findOne(YggUser yggUser);

    void update(YggUser yggUser);

    void changePassword(YggUser yggUser, String linkUUID);

    List<YggUser> findList(YggUser yggUser);

    Page<YggUser> findPage(YggUser yggUser, Pageable pageable);
}
