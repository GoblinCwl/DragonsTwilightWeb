package cn.goblincwl.dragontwilight.yggdrasil;

import cn.goblincwl.dragontwilight.yggdrasil.dto.request.*;
import cn.goblincwl.dragontwilight.yggdrasil.dto.response.AuthenticateResponse;
import cn.goblincwl.dragontwilight.yggdrasil.dto.response.ErrorResponse;
import cn.goblincwl.dragontwilight.yggdrasil.dto.response.JSONResponse;
import cn.goblincwl.dragontwilight.yggdrasil.dto.response.RefreshResponse;
import cn.goblincwl.dragontwilight.yggdrasil.entity.YggToken;
import cn.goblincwl.dragontwilight.yggdrasil.entity.YggUser;
import cn.goblincwl.dragontwilight.yggdrasil.exception.YggdrasilException;
import cn.goblincwl.dragontwilight.yggdrasil.mcdatamodels.mcprofile.MCProfile;
import cn.goblincwl.dragontwilight.yggdrasil.mcdatamodels.mcuser.MCUser;
import cn.goblincwl.dragontwilight.yggdrasil.service.YggTokenService;
import cn.goblincwl.dragontwilight.yggdrasil.service.YggUserService;
import cn.goblincwl.dragontwilight.yggdrasil.service.cache.CacheService;
import cn.goblincwl.dragontwilight.yggdrasil.utils.MCUUIDUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
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

    /**
     * 登录认证
     *
     * @return cn.goblincwl.dragontwilight.yggdrasil.dto.response.JSONResponse
     * @create 2020/7/4 14:35
     * @author ☪wl
     */
    @RequestMapping(value = "/yggdrasil/authserver/authenticate", produces = MediaType.APPLICATION_JSON_VALUE)
    public JSONResponse authenticate(@RequestBody AuthenticateRequest request, HttpServletResponse response) {
        try {
            String username = request.getUsername();
            // 检查请求是否携带有 ClientToken，未携带则进行添加
            if (!StringUtils.hasText(request.getClientToken())) {
                request.setClientToken(MCUUIDUtil.getRandomNonWhipUUID());
            }

            // 开始密码爆破防御
            int count = cacheService.getBruteTimeCount(username);
            // 如果尝试次数大于等于三次，则直接抛出异常，防止继续尝试
            if (count >= 5) {
                throw new YggdrasilException("ForbiddenOperationException", "短时间登录次数过多，请稍后重试。");
            }
            // 防爆不存在问题，增加计数
            cacheService.increaseBruteTimeCount(username);

            // 根据客户端提供的信息获取用户
            YggUser yggUser;
            YggUser yggUserQuery = new YggUser();
            if (username.contains("@")) {
                //如果有@，则是邮箱登录
                yggUserQuery.setUsername(username);
            } else {
                //否则使用角色名登录
                yggUserQuery.setPlayerName(username);
            }
            yggUser = yggUserService.findOne(yggUserQuery);
            // 未找到用户则抛出异常
            if (yggUser == null) {
                throw new YggdrasilException("ForbiddenOperationException", "用户不存在");
            }
            // 密码不匹配，抛出异常
            if (!yggUser.getPassword().equals(request.getPassword())) {
                throw new YggdrasilException("ForbiddenOperationException", "密码错误");
            }

            //服务器只用一个玩家一个号，所以始终选定唯一的profile
            MCProfile userProfile = MCProfile.builder()
                    .id(yggUser.getUUID())
                    .name(yggUser.getPlayerName())
                    .build();
            ArrayList<MCProfile> availableProfiles = new ArrayList<>();
            availableProfiles.add(userProfile);

            //返回地JSON对象
            AuthenticateResponse authenticateResponse = AuthenticateResponse.builder()
                    .accessToken(MCUUIDUtil.getRandomNonWhipUUID())
                    .clientToken(request.getClientToken())
                    .availableProfiles(availableProfiles)
                    .selectedProfile(userProfile)
                    .user(MCUser.builder()
                            .id(yggUser.getUUID())
                            .properties(new ArrayList<>())
                            .build())
                    .build();


            // 登录成功，移除密码防爆
            cacheService.clearBruteTimeCount(username);
            // 储存登录缓存
            this.yggTokenService.createToken(yggUser, authenticateResponse.getAccessToken(), authenticateResponse.getClientToken());

            return authenticateResponse;

        } catch (Exception e) {
            String error = "ERROR";
            String errorMessage = "未知错误";
            if (e instanceof YggdrasilException) {
                error = ((YggdrasilException) e).getError();
                errorMessage = ((YggdrasilException) e).getErrorMessage();
            } else {
                LOG.error("yggdrasil登陆验证异常", e);
            }
            response.setStatus(HttpStatus.FORBIDDEN.value());
            return ErrorResponse.builder()
                    .error(error)
                    .errorMessage(errorMessage)
                    .build();
        }
    }

    /**
     * 令牌刷新
     *
     * @return cn.goblincwl.dragontwilight.yggdrasil.dto.response.JSONResponse
     * @create 2020/7/4 14:35
     * @author ☪wl
     */
    @PostMapping("/yggdrasil/authserver/refresh")
    public JSONResponse refresh(@RequestBody RefreshRequest request, HttpServletResponse response) {
        try {
            // 初始化 token， 准备拉取
            YggToken yggToken;

            // 判断进行的拉取模式
            if (!StringUtils.hasText(request.getClientToken())) {
                yggToken = yggTokenService.getTokenByAccessToken(request.getAccessToken());
            } else {
                yggToken = yggTokenService.getTokenByBothToken(request.getAccessToken(), request.getClientToken());
            }

            // 如果没能拉取到 token，抛出异常
            if (yggToken == null) {
                throw new YggdrasilException("ForbiddenOperationException", "无效的Token");
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

            this.yggTokenService.createToken(yggToken.getOwner(), refreshResponse.getAccessToken(), refreshResponse.getClientToken());
            return refreshResponse;
        } catch (Exception e) {
            String error = "ERROR";
            String errorMessage = "未知错误";
            if (e instanceof YggdrasilException) {
                error = ((YggdrasilException) e).getError();
                errorMessage = ((YggdrasilException) e).getErrorMessage();
            } else {
                LOG.error("yggdrasil令牌刷新异常", e);
            }
            response.setStatus(HttpStatus.FORBIDDEN.value());
            return ErrorResponse.builder()
                    .error(error)
                    .errorMessage(errorMessage)
                    .build();
        }
    }

    /**
     * 验证令牌
     *
     * @return void
     * @create 2020/7/4 14:35
     * @author ☪wl
     */
    @PostMapping("/yggdrasil/authserver/validate")
    public Object validate(@RequestBody ValidateRequest request, HttpServletResponse response) {
        // 声明这次查询的 token
        try {
            YggToken yggToken;

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
                throw new YggdrasilException("ForbiddenOperationException", "无效的Token");
            }

            // 如果有必要，进行 token 的时间延长
            this.yggTokenService.extendExpiredTime(yggToken);
        } catch (Exception e) {
            String error = "ERROR";
            String errorMessage = "未知错误";
            if (e instanceof YggdrasilException) {
                error = ((YggdrasilException) e).getError();
                errorMessage = ((YggdrasilException) e).getErrorMessage();
            } else {
                LOG.error("yggdrasil令牌验证异常", e);
            }
            response.setStatus(HttpStatus.FORBIDDEN.value());
            return ErrorResponse.builder()
                    .error(error)
                    .errorMessage(errorMessage)
                    .build();
        }
        return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
    }

    /**
     * 吊销零盘
     *
     * @return void
     * @create 2020/7/4 19:20
     * @author ☪wl
     */
    @PostMapping("/yggdrasil/authserver/invalidate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void invalidate(@RequestBody InvalidateRequest request) {
        // 声明这次使用到的 token
        YggToken yggToken;

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
    }

    /**
     * 登出
     *
     * @return java.lang.Object
     * @create 2020/7/4 19:20
     * @author ☪wl
     */
    @PostMapping("/yggdrasil/authserver/signout")
    public Object signOut(@RequestBody SignOutRequest request, HttpServletResponse response) {

        try {
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
            YggUser yggUserQuery = new YggUser();
            yggUserQuery.setUsername(request.getUsername());
            YggUser yggUser = this.yggUserService.findOne(yggUserQuery);

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
        } catch (Exception e) {
            String error = "ERROR";
            String errorMessage = "未知错误";
            if (e instanceof YggdrasilException) {
                error = ((YggdrasilException) e).getError();
                errorMessage = ((YggdrasilException) e).getErrorMessage();
            } else {
                LOG.error("yggdrasil登出验证异常", e);
            }
            response.setStatus(HttpStatus.FORBIDDEN.value());
            return ErrorResponse.builder()
                    .error(error)
                    .errorMessage(errorMessage)
                    .build();
        }
        return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
    }
}
