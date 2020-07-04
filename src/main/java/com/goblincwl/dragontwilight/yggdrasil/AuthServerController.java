package com.goblincwl.dragontwilight.yggdrasil;

import com.goblincwl.dragontwilight.yggdrasil.dto.*;
import com.goblincwl.dragontwilight.yggdrasil.entity.YggToken;
import com.goblincwl.dragontwilight.yggdrasil.entity.YggUser;
import com.goblincwl.dragontwilight.yggdrasil.exception.YggdrasilException;
import com.goblincwl.dragontwilight.yggdrasil.mcdatamodels.mcprofile.MCProfile;
import com.goblincwl.dragontwilight.yggdrasil.mcdatamodels.mcuser.MCUser;
import com.goblincwl.dragontwilight.yggdrasil.service.YggTokenService;
import com.goblincwl.dragontwilight.yggdrasil.service.YggUserService;
import com.goblincwl.dragontwilight.yggdrasil.service.cache.CacheService;
import com.goblincwl.dragontwilight.yggdrasil.utils.minecraft.MCUUIDUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping(
        produces = MediaType.APPLICATION_JSON_VALUE,
        method = RequestMethod.POST)
public class AuthServerController {
    private final YggUserService yggUserService;
    private final YggTokenService yggTokenService;
    private final CacheService cacheService;

    private final Logger LOG = LoggerFactory.getLogger(AuthServerController.class);

    @Autowired
    public AuthServerController(YggUserService yggUserService, YggTokenService yggTokenService, CacheService cacheService) {
        this.yggUserService = yggUserService;
        this.yggTokenService = yggTokenService;
        this.cacheService = cacheService;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InfoResponse {
        private Map<String, String> meta;
        private List<String> skinDomains;
        private String signaturePublickey;
    }

    @GetMapping("/yggdrasil/")
    public InfoResponse info() {
        Map<String, String> meta = new HashMap<>();
        meta.put("serverName", "龙之暮认证服务器");
        meta.put("implementationName", "Dragon's Twilight Auth");
        meta.put("implementationVersion", "v1.0");

        ArrayList<String> skinDomains = new ArrayList<>();
        skinDomains.add("mc.goblincwl.cn");
        return InfoResponse.builder()
                .meta(meta)
                .skinDomains(skinDomains)
                .signaturePublickey("-----BEGIN PUBLIC KEY-----\n" +
                        "MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAzO3WcNDiMbeDipfZfrCz\n" +
                        "g4DCr/T++v4NkSpqBfo4wJK561WQPth+1Lc/wBZpcmdF4RSniDCoU9x7AfusVw8L\n" +
                        "hSSrjLYprKzwgxHxJfRLA4WnnakZWo9R9CWHlWgl6DOXa3zBonVxQKf1yKvWDcjU\n" +
                        "NHevsob05a1xg1VPoO9mZWYnJq3k4Sx0+sohwxR3aI+h7ao10cgZv9YmWHHaIINr\n" +
                        "Hqakq6p+LatykZXPb7bMvcAfDrETUUi1zCuUWaiVR2WMvqi9lUbA5XBQsn5PL8kW\n" +
                        "qk9qKoCfYZ5wZjD9y7mfyBlkhCnSv6OMSk/ofKTLcFv3LEBBV6xvgmLI7ahOV3K/\n" +
                        "pxKcJbqTBmFoVVAz5eCoTJ6oi7VtmMMETxjKvPVXrPIs/j6pk0r6C5kDdHjDRORI\n" +
                        "dTZ8WfsaEPQMhkGF9AGz+ojd4I0QOcVUDrN71WZJqiins1DA9BGq3fbpJkfDFLSq\n" +
                        "ueKG9TPmYYn0L4jc1vbYV1XmXPfGb3UImSl2LZQ4eQiq0GaGUi42Ee1vT7Sf5kEt\n" +
                        "rurOuILcBpttm/ZE6FTq9VoK5lA/cOkE7kBFv+EKmFjg2Ueq0B6ZHM5IORh9ffjM\n" +
                        "FJQM3Ou3OvhrsBFHEP0G6RtmAS5LRV1Vh6MO9IG1CYTtsSBSi7bGkUcb8o1iLvHY\n" +
                        "hRGQnrmBFD4oyQRZJ0LekYsCAwEAAQ==\n" +
                        "-----END PUBLIC KEY-----")
                .build();
    }

    @RequestMapping(value = "/yggdrasil/authserver/authenticate", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object authenticate(@RequestBody @Valid AuthenticateRequest request, BindingResult bindingResult) {
        try {
            // 检查是否有参数绑定错误
            if (bindingResult.getAllErrors().size() != 0) {
                // 抛出绑定异常
                throw new YggdrasilException("ForbiddenOperationException", "请输入正确的用户名和密码！");
            }

            // 检查请求是否携带有 ClientToken，未携带则进行添加
            if (!StringUtils.hasText(request.getClientToken())) {
                request.setClientToken(MCUUIDUtil.getRandomNonWhipUUID());
            }

            // 开始密码爆破防御
            int count = cacheService.getBruteTimeCount(request.getUsername());
            // 如果尝试次数大于等于三次，则直接抛出异常，防止继续尝试
            if (count >= 5) {
                throw new YggdrasilException("ForbiddenOperationException", "短时间登录次数过多，请稍后重试。");
            }
            // 防爆不存在问题，增加计数
            cacheService.increaseBruteTimeCount(request.getUsername());

            // 根据客户端提供的信息获取用户
            YggUser yggUser = yggUserService.getUserByUsername(request.getUsername());
            // 未找到用户则抛出异常
            if (yggUser == null) {
                throw new YggdrasilException("ForbiddenOperationException", "用户不存在");
            }
            // 密码不匹配，抛出异常
            if (!yggUser.getPassword().equals(request.getPassword())) {
                throw new YggdrasilException("ForbiddenOperationException", "密码错误");
            }

            // 初始化回复
            AuthenticateResponse authenticateResponse = AuthenticateResponse.builder()
                    .accessToken(MCUUIDUtil.getRandomNonWhipUUID())
                    .clientToken(request.getClientToken())
                    .build();

            // 构建用户可用角色信息
//            List<MCProfile> availableProfiles = new ArrayList<>();
//            availableProfiles.add(yggPlayerProfileService.getBriefMCProfile(yggUser));
//            authenticateResponse.setAvailableProfiles(availableProfiles);

            // 构建用户选择角色信息
            authenticateResponse.setSelectedProfile(
                    MCProfile.builder()
                            .id(yggUser.getProfileUUID())
                            .name(yggUser.getProfileName())
                            .properties(new ArrayList<>())
                            .build()
            );

            // 构建可选的用户信息
            if (request.getRequestUser()) {
                MCUser user = yggUserService.getMCUserByYggUser(yggUser);
                authenticateResponse.setUser(user);
            }

            // 登录成功，移除密码防爆
            cacheService.clearBruteTimeCount(request.getUsername());

            // 储存登录缓存
            this.yggTokenService.createToken(yggUser, authenticateResponse.getAccessToken(), authenticateResponse.getClientToken());
            return authenticateResponse;
        } catch (Exception e) {
            if (e instanceof YggdrasilException) {
                LOG.error(((YggdrasilException) e).getErrorMessage());
                return e;
            } else {
                LOG.error("认证服务器异常", e);
                return YggdrasilException.builder()
                        .error("ForbiddenOperationException")
                        .errorMessage("未知错误，请联系服主")
                        .build();
            }
        }
    }

    @PostMapping("/yggdrasil/authserver/refresh")
    public RefreshResponse refresh(@RequestBody @Valid RefreshRequest request, BindingResult bindingResult) {
        // 检查参数绑定是否有错
        if (bindingResult.getAllErrors().size() != 0) {
            throw new YggdrasilException("ForbiddenOperationException", "绑定错误，无法识别输入数据！");
        }

        // 初始化 token， 准备拉取
        YggToken yggToken = null;

        // 判断进行的拉取模式
        if (!StringUtils.hasText(request.getClientToken())) {
            yggToken = yggTokenService.getTokenByAccessToken(request.getAccessToken());
        } else {
            yggToken = yggTokenService.getTokenByBothToken(request.getAccessToken(), request.getClientToken());
        }

        // 如果没能拉取到 token，抛出异常
        if (yggToken == null) {
            throw new YggdrasilException("ForbiddenOperationException", "无法找到目标 token");
        }

        // 开始构建回复新 token
        RefreshResponse refreshResponse = RefreshResponse.builder()
                .accessToken(UUID.randomUUID().toString().replace("-", ""))
                .clientToken(yggToken.getClientToken())
                .build();

        // 如果请求了用户，则发送用户数据
        if (request.getRequestUser()) {
            refreshResponse.setUser(yggUserService.getMCUserByYggUser(yggToken.getOwner()));
        } else {
            refreshResponse.setUser(null);
        }
        // 否则返回的角色为空，沿用老Token数据
        this.yggTokenService.createToken(yggToken.getOwner(), refreshResponse.getAccessToken(), refreshResponse.getClientToken());
        return refreshResponse;
    }

    @PostMapping("/yggdrasil/authserver/validate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void validate(@RequestBody @Valid ValidateRequest request, HttpServletResponse response, BindingResult bindingResult) throws IOException {
        // 检测入参情况
        if (bindingResult.getAllErrors().size() != 0) {
            throw new YggdrasilException("ForbiddenOperationException", "绑定错误，无法识别输入数据！");
        }

        // 声明这次查询的 token
        YggToken yggToken = null;

        // 确认请求中是否含有 clientToken
        if (StringUtils.hasText(request.getClientToken())) {
            // 存在 clientToken，进行查询
            yggToken = this.yggTokenService.getTokenByBothToken(request.getAccessToken(), request.getClientToken());
        } else {
            // 不存在， 进行查询
            yggToken = this.yggTokenService.getTokenByAccessToken(request.getAccessToken());
        }

        // 如果没有查询到，抛出无效异常
        if (yggToken == null) {
            throw new YggdrasilException("ForbiddenOperationException", "该 Token 无效");
        }

        // 如果有必要，进行 token 的时间延长
        this.yggTokenService.extendExpiredTime(yggToken);
        // 关闭输出流
        response.getOutputStream().close();
    }

    //
    @PostMapping("/yggdrasil/authserver/invalidate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void invalidate(@RequestBody @Valid InvalidateRequest request, HttpServletResponse response, BindingResult bindingResult) throws IOException {
        // 检查传入参数
        if (bindingResult.getAllErrors().size() != 0) {
            throw new YggdrasilException("ForbiddenOperationException", "绑定错误，无法识别输入数据！");
        }

        // 声明这次使用到的 token
        YggToken yggToken = null;

        // 如果存在 clientToken， 进行查询
        if (StringUtils.hasText(request.getClientToken())) {
            // 尝试进行查询
            yggToken = this.yggTokenService.getTokenByBothToken(request.getAccessToken(), request.getClientToken());
        } else {
            // 尝试进行查询
            yggToken = this.yggTokenService.getTokenByAccessToken(request.getAccessToken());
        }

        // 存在，进行删除
        if (yggToken != null) {
            this.yggTokenService.deleteToken(yggToken);
        }

        // 关闭流
        response.getOutputStream().close();
    }

    //
    @PostMapping("/yggdrasil/authserver/signout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void signOut(@RequestBody @Valid SignOutRequest request, BindingResult bindingResult) {
        // 进行参数绑定检查
        if (bindingResult.getAllErrors().size() != 0) {
            throw new YggdrasilException("ForbiddenOperationException", "绑定错误，无法识别输入数据！");
        }

        // 检查是否存在爆破攻击
        int count = cacheService.getBruteTimeCount(request.getUsername());
        // 存在爆破攻击
        if (count >= 5) {
            // 抛出异常
            throw new YggdrasilException("ForbiddenOperationException", "在短期内进行了太多的尝试，请稍等一会儿再继续");
        }

        // 增加尝试记录
        cacheService.increaseBruteTimeCount(request.getUsername());

        // 通过用户名获取用户
        YggUser yggUser = this.yggUserService.getUserByUsername(request.getUsername());

        // 如果没有获取到用户
        if (yggUser == null) {
            // 抛出用户无法被获取到异常
            throw new YggdrasilException("ForbiddenOperationException", "错误的用户名/密码");
        }

        // 进行密码比对
        if (yggUser.getPassword().equals(request.getPassword())) {
            // 删除用户所有的 token
            this.yggTokenService.deleteAllTokensOfUser(yggUser);
            // 清除用户的尝试计数
            cacheService.clearBruteTimeCount(request.getUsername());
        } else {
            // 抛出密码错误异常
            throw new YggdrasilException("ForbiddenOperationException", "错误的用户名/密码");
        }
    }
}
