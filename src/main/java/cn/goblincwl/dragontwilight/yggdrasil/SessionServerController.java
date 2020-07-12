package cn.goblincwl.dragontwilight.yggdrasil;

import cn.goblincwl.dragontwilight.yggdrasil.dto.request.JoinRequest;
import cn.goblincwl.dragontwilight.yggdrasil.dto.response.ErrorResponse;
import cn.goblincwl.dragontwilight.yggdrasil.entity.YggToken;
import cn.goblincwl.dragontwilight.yggdrasil.entity.YggUser;
import cn.goblincwl.dragontwilight.yggdrasil.exception.YggdrasilException;
import cn.goblincwl.dragontwilight.yggdrasil.mcdatamodels.mcprofile.MCProfile;
import cn.goblincwl.dragontwilight.yggdrasil.service.YggTokenService;
import cn.goblincwl.dragontwilight.yggdrasil.service.YggUserService;
import cn.goblincwl.dragontwilight.yggdrasil.service.cache.CacheService;
import cn.goblincwl.dragontwilight.yggdrasil.service.cache.dto.SessionContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

// TODO: 需要完善
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
public class SessionServerController {
    private final YggTokenService yggTokenService;
    private final CacheService cacheService;
    private final YggUserService yggUserService;
    private final Logger LOG = LoggerFactory.getLogger(SessionServerController.class);

    @Autowired
    public SessionServerController(YggTokenService yggTokenService, CacheService cacheService, YggUserService yggUserService) {
        this.yggTokenService = yggTokenService;
        this.cacheService = cacheService;
        this.yggUserService = yggUserService;
    }

    /**
     * 客户端进入服务器
     *
     * @return java.lang.Object
     * @create 2020/7/4 19:21
     * @author ☪wl
     */
    @PostMapping("/yggdrasil/sessionserver/session/minecraft/join")
    public Object join(@RequestBody JoinRequest request, HttpServletRequest httpRequest, HttpServletResponse response) {
        try {
            // 查询是否存在这个 token
            YggToken yggToken = yggTokenService.getTokenByAccessToken(request.getAccessToken());
            if (yggToken == null) {
                // 没有查询到，抛出异常
                throw new YggdrasilException("ForbiddenOperationException", "无效的Token");
            }

            // 如果这个 token 未选定角色，抛出异常
            if (!yggToken.getOwner().getUUID().equals(request.getSelectedProfile())) {
                throw new YggdrasilException("ForbiddenOperationException", "无效的玩家");
            }

            // 放入登录会话 TODO: 新加入了 IP 支持
            cacheService.putJoinSession(request.getServerId(), request.getAccessToken(), request.getSelectedProfile(), httpRequest.getRemoteAddr());
        } catch (Exception e) {
            String error = "ERROR";
            String errorMessage = "未知错误";
            if (e instanceof YggdrasilException) {
                error = ((YggdrasilException) e).getError();
                errorMessage = ((YggdrasilException) e).getErrorMessage();
            } else {
                LOG.error("yggdrasil客户端进入服务器异常", e);
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
     * 服务端验证客户端
     *
     * @return java.lang.Object
     * @create 2020/7/4 19:27
     * @author ☪wl
     */
    // TODO: 接口需要更新，需要传出详细的角色信息，需要对IP进行判断，检测异常抛出机制问题
    @GetMapping("/yggdrasil/sessionserver/session/minecraft/hasJoined")
    public Object hasJoined(@RequestParam("username") String username,
                            @RequestParam("serverId") String serverId,
                            @RequestParam(value = "ip", required = false) String ip) {
        YggUser yggUser;
        try {
            SessionContainer sessionContainer = cacheService.getJoinSession(serverId);
            if (sessionContainer == null) {
                throw new YggdrasilException("ForbiddenOperationException", "无效的ServerID");
            }

            yggUser = this.yggUserService.getUserByProfileUUID(sessionContainer.getSelectedProfile());

            if (yggUser == null) {
                throw new YggdrasilException("ForbiddenOperationException", "无效的用户");
            }

            if (!yggUser.getPlayerName().equals(username)) {
                throw new YggdrasilException("ForbiddenOperationException", "无效的玩家");
            }
        } catch (Exception e) {
            LOG.error("yggdrasil服务端验证客户端异常", e);
            return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
        }
        return MCProfile.builder()
                .id(yggUser.getUUID())
                .name(yggUser.getPlayerName())
                .properties(new ArrayList<>())
                .build();
    }

    /**
     * 查询角色属性
     *
     * @return java.lang.Object
     * @create 2020/7/4 19:30
     * @author ☪wl
     */
    // TODO: 对于 unsigned 的处理，参数校验
    @GetMapping(value = "/yggdrasil/sessionserver/session/minecraft/profile/{uuid}")
    public Object profile(@PathVariable("uuid") String uuid,
                          @RequestParam(value = "unsigned", required = false) Boolean unsigned) {

        YggUser yggUser;
        try {
            yggUser = this.yggUserService.getUserByProfileUUID(uuid);

            if (yggUser == null) {
                throw new YggdrasilException("ForbiddenOperationException", "无效的玩家");
            }
        } catch (Exception e) {
            LOG.error("yggdrasil查询角色属性异常", e);
            return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
        }

        return MCProfile.builder()
                .id(yggUser.getUUID())
                .name(yggUser.getPlayerName())
                .properties(new ArrayList<>())
                .build();

    }

    /**
     * 按名称批量查询角色
     *
     * @param mcProfileNames 玩家名称数组
     * @return java.util.List<cn.goblincwl.dragontwilight.yggdrasil.mcdatamodels.mcprofile.MCProfile>
     * @create 2020/7/4 19:35
     * @author ☪wl
     */
    @PostMapping(value = "/yggdrasil/api/profiles/minecraft")
    public List<MCProfile> listProfile(@RequestBody List<String> mcProfileNames) {
        List<MCProfile> resultList = new ArrayList<>();
        for (String playerName : mcProfileNames) {
            YggUser yggUser = this.yggUserService.getUserByPlayerName(playerName);
            if (yggUser != null) {
                resultList.add(MCProfile.builder()
                        .id(yggUser.getUUID())
                        .name(yggUser.getPlayerName())
                        .properties(new ArrayList<>())
                        .build());
            }
        }
        return resultList;
    }
}
