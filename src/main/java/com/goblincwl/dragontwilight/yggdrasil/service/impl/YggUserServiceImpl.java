package com.goblincwl.dragontwilight.yggdrasil.service.impl;

import com.goblincwl.dragontwilight.common.exception.DtWebException;
import com.goblincwl.dragontwilight.yggdrasil.entity.YggPasswordLink;
import com.goblincwl.dragontwilight.yggdrasil.entity.YggUser;
import com.goblincwl.dragontwilight.yggdrasil.mcdatamodels.mcuser.MCUser;
import com.goblincwl.dragontwilight.yggdrasil.repository.YggUserRepository;
import com.goblincwl.dragontwilight.yggdrasil.service.YggPasswordLinkService;
import com.goblincwl.dragontwilight.yggdrasil.service.YggUserService;
import com.goblincwl.dragontwilight.yggdrasil.utils.MCUUIDUtil;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

/**
 * @author ☪wl
 * @program dragons-twilight-web
 * @description Yggdrasil 用户 ServiceImpl
 * @create 2020-07-03 22:23
 */
@Service
@Transactional
public class YggUserServiceImpl implements YggUserService {

    private final YggUserRepository yggUserRepository;
    private final YggPasswordLinkService yggPasswordLinkService;

    public YggUserServiceImpl(YggUserRepository yggUserRepository, YggPasswordLinkService yggPasswordLinkService) {
        this.yggUserRepository = yggUserRepository;
        this.yggPasswordLinkService = yggPasswordLinkService;
    }

    @Override
    public YggUser getUserByUsername(String username) {
        return this.yggUserRepository.findByUsername(username);
    }

    @Override
    public MCUser getMCUserByYggUser(YggUser yggUser) {
        if (yggUser == null) {
            return null;
        }

        return MCUser.builder()
                .id(yggUser.getUUID())
                .properties(new ArrayList<>())
                .build();
    }

    @Override
    public YggUser getUserByProfileUUID(String selectedProfile) {
        return this.yggUserRepository.findByUUID(selectedProfile);
    }

    @Override
    public YggUser getUserByPlayerName(String playerName) {
        return this.yggUserRepository.findByPlayerName(playerName);
    }

    @Override
    public YggUser register(String email, String password, String playerName, String ipAddr) {
        //先查询邮箱和用户名是否被占用
        YggUser byUsername = this.yggUserRepository.findByUsername(email);
        if (byUsername != null) {
            throw new DtWebException("此邮箱已经注册过账号,email");
        }

        //查询游戏昵称是否被占用
        YggUser byPlayerName = this.yggUserRepository.findByPlayerName(playerName);
        if (byPlayerName != null) {
            throw new DtWebException("此用户名已被占用,playerName");
        }

        YggUser yggUser = YggUser.builder()
                .username(email)
                .playerName(playerName)
                .password(password)
                .UUID(MCUUIDUtil.getRandomNonWhipUUID())
                .ipAddr(ipAddr)
                .build();

        return this.yggUserRepository.save(yggUser);
    }

    @Override
    public YggUser findOne(YggUser yggUser) {
        return this.yggUserRepository.findOne(Example.of(yggUser)).orElse(null);
    }

    @Override
    public void update(YggUser yggUser) {

    }

    @Override
    public void changePassword(YggUser yggUser, String linkUUID) {
        //删除链接
        YggPasswordLink yggPasswordLink = new YggPasswordLink();
        yggPasswordLink.setUUID(linkUUID);
        this.yggPasswordLinkService.delete(yggPasswordLink);
        //修改密码
        YggUser queryYggUser = new YggUser();
        queryYggUser.setUsername(yggUser.getUsername());
        this.yggUserRepository.findOne(Example.of(queryYggUser)).ifPresent(inYggUser -> {
            inYggUser.setPassword(yggUser.getPassword());
            yggUserRepository.save(inYggUser);
        });
    }

}
