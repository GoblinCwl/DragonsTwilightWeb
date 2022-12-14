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
        meta.put("serverName", "????????????????????????");
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
     * ????????????
     *
     * @return cn.goblincwl.dragontwilight.yggdrasil.dto.response.JSONResponse
     * @create 2020/7/4 14:35
     * @author ???wl
     */
    @RequestMapping(value = "/yggdrasil/authserver/authenticate", produces = MediaType.APPLICATION_JSON_VALUE)
    public JSONResponse authenticate(@RequestBody AuthenticateRequest request, HttpServletResponse response) {
        try {
            String username = request.getUsername();
            // ??????????????????????????? ClientToken???????????????????????????
            if (!StringUtils.hasText(request.getClientToken())) {
                request.setClientToken(MCUUIDUtil.getRandomNonWhipUUID());
            }

            // ????????????????????????
            int count = cacheService.getBruteTimeCount(username);
            // ?????????????????????????????????????????????????????????????????????????????????
            if (count >= 5) {
                throw new YggdrasilException("ForbiddenOperationException", "????????????????????????????????????????????????");
            }
            // ????????????????????????????????????
            cacheService.increaseBruteTimeCount(username);

            // ??????????????????????????????????????????
            YggUser yggUser;
            YggUser yggUserQuery = new YggUser();
            if (username.contains("@")) {
                //?????????@?????????????????????
                yggUserQuery.setUsername(username);
            } else {
                //???????????????????????????
                yggUserQuery.setPlayerName(username);
            }
            yggUser = yggUserService.findOne(yggUserQuery);
            // ??????????????????????????????
            if (yggUser == null) {
                throw new YggdrasilException("ForbiddenOperationException", "???????????????");
            }
            // ??????????????????????????????
            if (!yggUser.getPassword().equals(request.getPassword())) {
                throw new YggdrasilException("ForbiddenOperationException", "????????????");
            }

            //??????????????????????????????????????????????????????????????????profile
            MCProfile userProfile = MCProfile.builder()
                    .id(yggUser.getUUID())
                    .name(yggUser.getPlayerName())
                    .build();
            ArrayList<MCProfile> availableProfiles = new ArrayList<>();
            availableProfiles.add(userProfile);

            //?????????JSON??????
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


            // ?????????????????????????????????
            cacheService.clearBruteTimeCount(username);
            // ??????????????????
            this.yggTokenService.createToken(yggUser, authenticateResponse.getAccessToken(), authenticateResponse.getClientToken());

            return authenticateResponse;

        } catch (Exception e) {
            String error = "ERROR";
            String errorMessage = "????????????";
            if (e instanceof YggdrasilException) {
                error = ((YggdrasilException) e).getError();
                errorMessage = ((YggdrasilException) e).getErrorMessage();
            } else {
                LOG.error("yggdrasil??????????????????", e);
            }
            response.setStatus(HttpStatus.FORBIDDEN.value());
            return ErrorResponse.builder()
                    .error(error)
                    .errorMessage(errorMessage)
                    .build();
        }
    }

    /**
     * ????????????
     *
     * @return cn.goblincwl.dragontwilight.yggdrasil.dto.response.JSONResponse
     * @create 2020/7/4 14:35
     * @author ???wl
     */
    @PostMapping("/yggdrasil/authserver/refresh")
    public JSONResponse refresh(@RequestBody RefreshRequest request, HttpServletResponse response) {
        try {
            // ????????? token??? ????????????
            YggToken yggToken;

            // ???????????????????????????
            if (!StringUtils.hasText(request.getClientToken())) {
                yggToken = yggTokenService.getTokenByAccessToken(request.getAccessToken());
            } else {
                yggToken = yggTokenService.getTokenByBothToken(request.getAccessToken(), request.getClientToken());
            }

            // ????????????????????? token???????????????
            if (yggToken == null) {
                throw new YggdrasilException("ForbiddenOperationException", "?????????Token");
            }

            // ????????????????????? token
            RefreshResponse refreshResponse = RefreshResponse.builder()
                    .accessToken(UUID.randomUUID().toString().replace("-", ""))
                    .clientToken(yggToken.getClientToken())
                    .build();

            // ?????????????????????????????????????????????
            if (request.getRequestUser()) {
                refreshResponse.setUser(yggUserService.getMCUserByYggUser(yggToken.getOwner()));
            } else {
                refreshResponse.setUser(null);
            }

            this.yggTokenService.createToken(yggToken.getOwner(), refreshResponse.getAccessToken(), refreshResponse.getClientToken());
            return refreshResponse;
        } catch (Exception e) {
            String error = "ERROR";
            String errorMessage = "????????????";
            if (e instanceof YggdrasilException) {
                error = ((YggdrasilException) e).getError();
                errorMessage = ((YggdrasilException) e).getErrorMessage();
            } else {
                LOG.error("yggdrasil??????????????????", e);
            }
            response.setStatus(HttpStatus.FORBIDDEN.value());
            return ErrorResponse.builder()
                    .error(error)
                    .errorMessage(errorMessage)
                    .build();
        }
    }

    /**
     * ????????????
     *
     * @return void
     * @create 2020/7/4 14:35
     * @author ???wl
     */
    @PostMapping("/yggdrasil/authserver/validate")
    public Object validate(@RequestBody ValidateRequest request, HttpServletResponse response) {
        // ????????????????????? token
        try {
            YggToken yggToken;

            // ??????????????????????????? clientToken
            if (StringUtils.hasText(request.getClientToken())) {
                // ?????? clientToken???????????????
                yggToken = this.yggTokenService.getTokenByBothToken(request.getAccessToken(), request.getClientToken());
            } else {
                // ???????????? ????????????
                yggToken = this.yggTokenService.getTokenByAccessToken(request.getAccessToken());
            }

            // ??????????????????????????????????????????
            if (yggToken == null) {
                throw new YggdrasilException("ForbiddenOperationException", "?????????Token");
            }

            // ???????????????????????? token ???????????????
            this.yggTokenService.extendExpiredTime(yggToken);
        } catch (Exception e) {
            String error = "ERROR";
            String errorMessage = "????????????";
            if (e instanceof YggdrasilException) {
                error = ((YggdrasilException) e).getError();
                errorMessage = ((YggdrasilException) e).getErrorMessage();
            } else {
                LOG.error("yggdrasil??????????????????", e);
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
     * ????????????
     *
     * @return void
     * @create 2020/7/4 19:20
     * @author ???wl
     */
    @PostMapping("/yggdrasil/authserver/invalidate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void invalidate(@RequestBody InvalidateRequest request) {
        // ???????????????????????? token
        YggToken yggToken;

        // ???????????? clientToken??? ????????????
        if (StringUtils.hasText(request.getClientToken())) {
            // ??????????????????
            yggToken = this.yggTokenService.getTokenByBothToken(request.getAccessToken(), request.getClientToken());
        } else {
            // ??????????????????
            yggToken = this.yggTokenService.getTokenByAccessToken(request.getAccessToken());
        }

        // ?????????????????????
        if (yggToken != null) {
            this.yggTokenService.deleteToken(yggToken);
        }
    }

    /**
     * ??????
     *
     * @return java.lang.Object
     * @create 2020/7/4 19:20
     * @author ???wl
     */
    @PostMapping("/yggdrasil/authserver/signout")
    public Object signOut(@RequestBody SignOutRequest request, HttpServletResponse response) {

        try {
            // ??????????????????????????????
            int count = cacheService.getBruteTimeCount(request.getUsername());
            // ??????????????????
            if (count >= 5) {
                // ????????????
                throw new YggdrasilException("ForbiddenOperationException", "??????????????????????????????????????????????????????????????????");
            }

            // ??????????????????
            cacheService.increaseBruteTimeCount(request.getUsername());

            // ???????????????????????????
            YggUser yggUserQuery = new YggUser();
            yggUserQuery.setUsername(request.getUsername());
            YggUser yggUser = this.yggUserService.findOne(yggUserQuery);

            // ???????????????????????????
            if (yggUser == null) {
                // ????????????????????????????????????
                throw new YggdrasilException("ForbiddenOperationException", "??????????????????/??????");
            }

            // ??????????????????
            if (yggUser.getPassword().equals(request.getPassword())) {
                // ????????????????????? token
                this.yggTokenService.deleteAllTokensOfUser(yggUser);
                // ???????????????????????????
                cacheService.clearBruteTimeCount(request.getUsername());
            } else {
                // ????????????????????????
                throw new YggdrasilException("ForbiddenOperationException", "??????????????????/??????");
            }
        } catch (Exception e) {
            String error = "ERROR";
            String errorMessage = "????????????";
            if (e instanceof YggdrasilException) {
                error = ((YggdrasilException) e).getError();
                errorMessage = ((YggdrasilException) e).getErrorMessage();
            } else {
                LOG.error("yggdrasil??????????????????", e);
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
