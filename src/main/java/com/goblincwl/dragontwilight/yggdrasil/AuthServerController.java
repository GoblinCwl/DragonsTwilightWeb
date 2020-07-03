package com.goblincwl.dragontwilight.yggdrasil;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.goblincwl.dragontwilight.common.exception.DtWebException;
import com.goblincwl.dragontwilight.yggdrasil.entity.YggPlayerProfile;
import com.goblincwl.dragontwilight.yggdrasil.entity.YggUser;
import com.goblincwl.dragontwilight.yggdrasil.exception.YggdrasilException;
import com.goblincwl.dragontwilight.yggdrasil.mcdatamodels.mcprofile.MCProfile;
import com.goblincwl.dragontwilight.yggdrasil.mcdatamodels.mcuser.MCUser;
import com.goblincwl.dragontwilight.yggdrasil.service.MinecraftTokenService;
import com.goblincwl.dragontwilight.yggdrasil.service.YggPlayerProfileService;
import com.goblincwl.dragontwilight.yggdrasil.service.YggUserService;
import com.goblincwl.dragontwilight.yggdrasil.service.cache.CacheService;
import com.goblincwl.dragontwilight.yggdrasil.utils.minecraft.MCUUIDUtil;
import com.goblincwl.dragontwilight.yggdrasil.dto.AuthenticateRequest;
import com.goblincwl.dragontwilight.yggdrasil.dto.AuthenticateResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping(
        produces = MediaType.APPLICATION_JSON_VALUE,
        method = RequestMethod.POST)
public class AuthServerController {
    private final YggUserService yggUserService;
    private final YggPlayerProfileService yggPlayerProfileService;
    private final MinecraftTokenService minecraftTokenService;
    private final CacheService cacheService;

    @Autowired
    public AuthServerController(YggUserService yggUserService, YggPlayerProfileService yggPlayerProfileService, MinecraftTokenService minecraftTokenService, CacheService cacheService) {
        this.yggUserService = yggUserService;
        this.yggPlayerProfileService = yggPlayerProfileService;
        this.minecraftTokenService = minecraftTokenService;
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
                        "      MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAxbWN+yehmLroY7YryXCF\n" +
                        "      9fcjKII1Y2thdpCInnM7hoIHfvcNep6gELxtFFn1JzCem3njeRr4p7k2e4buVyFw\n" +
                        "      U8UXEGGkxVR8mNgCWj46OYyf7MO813MRfavc0NRL5w1xNanYwRIxoxh0lgCG7Ios\n" +
                        "      Cgr3D66PUPKPvZ9w8fX/VqrhLt2IZgCFDlz0VsewY0yEnj7obxwFVaYe2wc8R1Z2\n" +
                        "      ZF6E+thRzYW3SpiKRX7CAcYZjiuy92YDgtp3RBL5ODNdU7EziwrcaG8gZ8cwAjQy\n" +
                        "      OohQ1zslv9wz0kFfBuH/uZcHYgb0qmTA74runaKtbKBrnKFKPVHVgXGOb50KVhE4\n" +
                        "      hwE1hz2HseT7qcyyrDfr0jGO+TRJqEih+LCCK433zFZLCJwlKLmDgK7UyKrCDYmR\n" +
                        "      Xg0+1TxzjeK+SdZ4vy0pFHtyyNp2tIqAm7sF3HcR0FUkoCCeWoy4wvM1XWRJPN9X\n" +
                        "      /K0WGZSVcHtaBau96MOwQD0XAABzhjugeH40VJDmtEMbng7Ipqhtu5gZCcz2D5aH\n" +
                        "      5YLFKy3Huv7uL3ueRmFUJAWhU5nnu5YDncFxpabmvqt3GBX2+KQuVA5TdH/4z05q\n" +
                        "      oCf1KMqotL+3BHLo96tNXl7GbXFtDBgInpzz/k3esBo4/zyPQlVZgr10ISV9WakN\n" +
                        "      DQ2JmPC9BxluiEWLhm3OKLMCAwEAAQ==")
                .build();
    }

    @RequestMapping(value = "/yggdrasil/authserver/authenticate", produces = MediaType.APPLICATION_JSON_VALUE)
    public AuthenticateResponse authenticate(@RequestBody @Valid AuthenticateRequest request, BindingResult bindingResult) {
        // 检查是否有参数绑定错误
        if (bindingResult.getAllErrors().size() != 0) {
            // 抛出绑定异常
            throw new DtWebException("绑定错误，无法识别输入数据！");
        }

        // 检查请求是否携带有 ClientToken，未携带则进行添加
        if (!StringUtils.hasText(request.getClientToken())) {
            request.setClientToken(MCUUIDUtil.getRandomNonWhipUUID());
        }

        // 开始密码爆破防御
        int count = cacheService.getBruteTimeCount(request.getUsername());
        // 如果尝试次数大于等于三次，则直接抛出异常，防止继续尝试
        if (count >= 3) {
            throw new YggdrasilException("在短时间内进行了多次失败登录，已经暂时禁止登录，请等待");
        }

        // 防爆不存在问题，增加计数
        cacheService.increaseBruteTimeCount(request.getUsername());

        // 根据客户端提供的信息获取用户
        YggUser yggUser = yggUserService.getUserByUsername(request.getUsername());

        // 未找到用户则抛出异常
        if (yggUser == null) {
            throw new YggdrasilException("用户名或密码错误");
        }

        // 密码不匹配，抛出异常
        // TODO: 在以后的版本中对此算法进行改良，要求使用加密
        if (!yggUser.getPassword().equals(request.getPassword())) {
            throw new YggdrasilException("用户名或密码错误");
        }

        // 初始化回复
        AuthenticateResponse authenticateResponse = AuthenticateResponse.builder()
                .accessToken(MCUUIDUtil.getRandomNonWhipUUID())
                .clientToken(request.getClientToken())
                .build();

        // 构建用户可用角色信息
        List<MCProfile> availableProfiles = new ArrayList<>();
        for (YggPlayerProfile profile : yggUser.getProfiles()) {
            availableProfiles.add(yggPlayerProfileService.getBriefMCProfile(profile));
        }

        authenticateResponse.setAvailableProfiles(availableProfiles);

        // 构建用户选择角色信息
        if (yggUser.getSelectedProfile() == null) {
            authenticateResponse.setSelectedProfile(null);
        } else {
            authenticateResponse.setSelectedProfile(yggPlayerProfileService.getBriefMCProfile(yggUser.getSelectedProfile()));
        }

        // 构建可选的用户信息
        if (request.getRequestUser()) {
            MCUser user = yggUserService.getMCUserBySodaUser(yggUser);
            authenticateResponse.setUser(user);
        }

        // 登录成功，移除密码防爆
        cacheService.clearBruteTimeCount(request.getUsername());

        // 储存登录缓存
        this.minecraftTokenService.createToken(yggUser, authenticateResponse.getAccessToken(), authenticateResponse.getClientToken(), yggUser.getSelectedProfile());
        return authenticateResponse;
    }

//    @PostMapping("/yggdrasil/authserver/refresh")
//    public RefreshResponse refresh(@RequestBody @Valid RefreshRequest request, BindingResult bindingResult) {
//        // 检查参数绑定是否有错
//        if(bindingResult.getAllErrors().size() != 0){
//            throw new RefreshBindingException("绑定错误，无法识别输入数据！");
//        }
//
//        // 初始化 token， 准备拉取
//        MinecraftToken minecraftToken = null;
//
//        // 判断进行的拉取模式
//        if(!StringUtils.hasText(request.getClientToken())){
//            minecraftToken = minecraftTokenService.getTokenByAccessToken(request.getAccessToken());
//        } else {
//            minecraftToken = minecraftTokenService.getTokenByBothToken(request.getAccessToken(), request.getClientToken());
//        }
//
//        // 如果没能拉取到 token，抛出异常
//        if(minecraftToken == null){
//            throw new RefreshTokenNotFoundException("无法找到目标 token");
//        }
//
//        // 开始构建回复新 token
//        RefreshResponse refreshResponse = RefreshResponse.builder()
//                .accessToken(UUID.randomUUID().toString().replace("-", ""))
//                .clientToken(minecraftToken.getClientToken())
//                .build();
//
//        // 如果请求了用户，则发送用户数据
//        if(request.getRequestUser()){
//            refreshResponse.setUser(sodaUserService.getMCUserBySodaUser(minecraftToken.getOwner()));
//        } else {
//            refreshResponse.setUser(null);
//        }
//
//        // 如果选择了新的 PlayerProfile
//        if(request.getSelectedProfile() != null){
//            // 获取请求中的 PlayerProfile
//            MCProfile mcProfile = request.getSelectedProfile();
//            // 开始检查该用户是否具有这个 PlayerProfile
//            for(SodaPlayerProfile playerProfile: minecraftToken.getOwner().getProfiles()){
//                if(playerProfile.getUUID().equals(mcProfile.getId())){
//                    refreshResponse.setSelectedProfile(sodaPlayerProfileService.getBriefMCProfile(playerProfile));
//                    minecraftTokenService.createToken(minecraftToken.getOwner(), refreshResponse.getAccessToken(), refreshResponse.getClientToken(), playerProfile);
//                    return refreshResponse;
//                }
//            }
//            throw new RefreshUnableToLoadMCProfileException("无法找到目标玩家数据");
//        } else {
//            // 否则返回的角色为空，沿用老Token数据
//            minecraftTokenService.createToken(minecraftToken.getOwner(), refreshResponse.getAccessToken(), refreshResponse.getClientToken(), minecraftToken.getPlayerProfile());
//            return refreshResponse;
//        }
//    }
//
//    @PostMapping("/yggdrasil/authserver/validate")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void validate(@RequestBody @Valid ValidateRequest request, HttpServletResponse response, BindingResult bindingResult) throws IOException {
//        // 检测入参情况
//        if(bindingResult.getAllErrors().size() != 0){
//            throw new ValidateBindingException("绑定错误，无法识别输入数据！");
//        }
//
//        // 声明这次查询的 token
//        MinecraftToken minecraftToken = null;
//
//        // 确认请求中是否含有 clientToken
//        if(StringUtils.hasText(request.getClientToken())){
//            // 存在 clientToken，进行查询
//            minecraftToken = minecraftTokenService.getTokenByBothToken(request.getAccessToken(), request.getClientToken());
//        } else {
//            // 不存在， 进行查询
//            minecraftToken = minecraftTokenService.getTokenByAccessToken(request.getAccessToken());
//        }
//
//        // 如果没有查询到，抛出无效异常
//        if(minecraftToken == null){
//            throw new ValidateInvalidTokenException("该 Token 无效");
//        }
//
//        // 如果有必要，进行 token 的时间延长
//        minecraftTokenService.extendExpiredTime(minecraftToken);
//        // 关闭输出流
//        response.getOutputStream().close();
//    }
//
//    @PostMapping("/yggdrasil/authserver/invalidate")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void invalidate(@RequestBody @Valid InvalidateRequest request, HttpServletResponse response, BindingResult bindingResult) throws IOException {
//        // 检查传入参数
//        if(bindingResult.getAllErrors().size() != 0){
//            throw new InvalidateBindingException("绑定错误，无法识别输入数据！");
//        }
//
//        // 声明这次使用到的 token
//        MinecraftToken minecraftToken = null;
//
//        // 如果存在 clientToken， 进行查询
//        if(StringUtils.hasText(request.getClientToken())){
//            // 尝试进行查询
//            minecraftToken = minecraftTokenService.getTokenByBothToken(request.getAccessToken(), request.getClientToken());
//        } else {
//            // 尝试进行查询
//            minecraftToken = minecraftTokenService.getTokenByAccessToken(request.getAccessToken());
//        }
//
//        // 存在，进行删除
//        if(minecraftToken != null){
//            minecraftTokenService.deleteToken(minecraftToken);
//        }
//
//        // 关闭流
//        response.getOutputStream().close();
//    }
//
//    @PostMapping("/yggdrasil/authserver/signout")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void signOut(@RequestBody @Valid SignOutRequest request, BindingResult bindingResult){
//        // 进行参数绑定检查
//        if(bindingResult.getAllErrors().size() != 0){
//            throw new SignOutBindingException();
//        }
//
//        // 检查是否存在爆破攻击
//        int count = cacheService.getBruteTimeCount(request.getUsername());
//        // 存在爆破攻击
//        if(count >= 3){
//            // 抛出异常
//            throw new SignOutTimingAttackException("在短期内进行了太多的尝试，请稍等一会儿再继续");
//        }
//
//        // 增加尝试记录
//        cacheService.increaseBruteTimeCount(request.getUsername());
//
//        // 通过用户名获取用户
//        SodaUser user = sodaUserService.getUserByUsername(request.getUsername());
//
//        // 如果没有获取到用户
//        if(user == null){
//            // 抛出用户无法被获取到异常
//            throw new SignOutSodaUserNotFoundException("错误的用户名/密码");
//        }
//
//        // 进行密码比对
//        if(user.getPassword().equals(request.getPassword())){
//            // 删除用户所有的 token
//            minecraftTokenService.deleteAllTokensOfUser(user);
//            // 清除用户的尝试计数
//            cacheService.clearBruteTimeCount(request.getUsername());
//        } else {
//            // 抛出密码错误异常
//            throw new SignOutBadCredentialsException("错误的用户名/密码");
//        }
//    }
}
