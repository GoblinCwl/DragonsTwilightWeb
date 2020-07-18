package cn.goblincwl.dragontwilight.yggdrasil.service.impl;

import cn.goblincwl.dragontwilight.yggdrasil.mcdatamodels.mcuser.MCUser;
import cn.goblincwl.dragontwilight.common.exception.DtWebException;
import cn.goblincwl.dragontwilight.yggdrasil.entity.YggPasswordLink;
import cn.goblincwl.dragontwilight.yggdrasil.entity.YggUser;
import cn.goblincwl.dragontwilight.yggdrasil.repository.YggUserRepository;
import cn.goblincwl.dragontwilight.yggdrasil.service.YggPasswordLinkService;
import cn.goblincwl.dragontwilight.yggdrasil.service.YggUserService;
import cn.goblincwl.dragontwilight.yggdrasil.utils.MCUUIDUtil;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
                .regDate(new Date())
                .isAdmin(0)
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

    @Override
    public List<YggUser> findList(YggUser yggUser) {
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("UUID", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("playerName", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("username", ExampleMatcher.GenericPropertyMatchers.contains());
        return this.yggUserRepository.findAll(Example.of(yggUser, exampleMatcher),
                Sort.by(Sort.Direction.ASC, "playerName"));
    }

    @Override
    public Page<YggUser> findPage(YggUser yggUser, Pageable pageable) {
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("UUID", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("playerName", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("username", ExampleMatcher.GenericPropertyMatchers.contains());
        return this.yggUserRepository.findAll(Example.of(yggUser, exampleMatcher),
                pageable);
    }

}
