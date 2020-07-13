package cn.goblincwl.dragontwilight.controller;

import cn.goblincwl.dragontwilight.common.exception.DtWebException;
import cn.goblincwl.dragontwilight.common.result.ResultGenerator;
import cn.goblincwl.dragontwilight.common.utils.CommonUtils;
import cn.goblincwl.dragontwilight.service.WebOptionsService;
import cn.goblincwl.dragontwilight.yggdrasil.entity.YggPasswordLink;
import cn.goblincwl.dragontwilight.yggdrasil.entity.YggUser;
import cn.goblincwl.dragontwilight.yggdrasil.service.YggPasswordLinkService;
import cn.goblincwl.dragontwilight.yggdrasil.service.YggUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;


/**
 * @author ☪wl
 * @program dragons-twilight-web
 * @description 基础 Controller
 * @create 2020-05-20 9:39
 */
@Controller
@RequestMapping("/")
public class WebController {

    private final Logger LOG = LoggerFactory.getLogger(WebController.class);
    private final YggUserService yggUserService;
    private final YggPasswordLinkService yggPasswordLinkService;
    private final WebOptionsService webOptionsService;

    public WebController(YggUserService yggUserService, YggPasswordLinkService yggPasswordLinkService, WebOptionsService webOptionsService) {
        this.yggUserService = yggUserService;
        this.yggPasswordLinkService = yggPasswordLinkService;
        this.webOptionsService = webOptionsService;
    }

    /**
     * 首页
     *
     * @return java.lang.String
     * @create 2020/6/14 1:24
     * @author ☪wl
     */
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("activeLi", "liIndex");
        return "index";
    }

    /**
     * 跳转：注册页面
     *
     * @return java.lang.String
     * @create 2020/7/11 15:41
     * @author ☪wl
     */
    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("activeLi", "liRegisterPage");
        return "register";
    }

    /**
     * 下载客户端
     *
     * @return java.lang.String
     * @create 2020/7/13 22:10
     * @author ☪wl
     */
    @GetMapping("/download")
    public String download() {
        String clientDownloadUrl = this.webOptionsService.findByKey("clientDownloadUrl").getOptValue();
        return "redirect:" + clientDownloadUrl;
    }

    @ResponseBody
    @GetMapping("/mcsMangerApiUrl")
    public String mcsMangerApiUrl() {
        return this.webOptionsService.findByKey("mcsMangerApiUrl").getOptValue();
    }

    /**
     * 注册
     *
     * @param email      用户名(邮箱)
     * @param password   密码
     * @param playerName 玩家名称
     * @return java.lang.String
     * @create 2020/7/11 15:41
     * @author ☪wl
     */
    @ResponseBody
    @PostMapping("/doRegister")
    public String doRegister(HttpServletRequest request, String email, String password, String playerName) {
        String resultMsg = null;
        try {
            YggUser yggUser = this.yggUserService.register(email, password, playerName, CommonUtils.getIP(request));
            if (yggUser != null) {
                resultMsg = "注册成功!";
            }
        } catch (Exception e) {
            String msg = "注册异常，请联系腐竹";
            if (e instanceof DtWebException) {
                msg = e.getMessage();
            }
            LOG.error(msg, e);
            return ResultGenerator.genFailResult(msg).toString();
        }
        return ResultGenerator.genSuccessResult(resultMsg).toString();
    }

    /**
     * 找回密码
     *
     * @param email      用户名(邮箱)
     * @param playerName 玩家名称
     * @return java.lang.String
     * @create 2020/7/11 15:42
     * @author ☪wl
     */
    @ResponseBody
    @PostMapping("/backPassword")
    public String backPassword(HttpServletRequest request, String email, String playerName) {
        try {
            //查询用户是否存在
            YggUser yggUser = new YggUser();
            yggUser.setUsername(email);
            yggUser.setPlayerName(playerName);
            YggUser userByUsername = this.yggUserService.findOne(yggUser);
            if (userByUsername == null) {
                throw new DtWebException("此用户不存在");
            }
            //生成邮件地址
            this.yggPasswordLinkService.createEmailLink(yggUser, CommonUtils.getServerUrl(request));
        } catch (Exception e) {
            String msg = "发送邮件异常，请联系腐竹";
            if (e instanceof DtWebException) {
                msg = e.getMessage();
            }
            LOG.error(msg, e);
            return ResultGenerator.genFailResult(msg).toString();
        }
        return ResultGenerator.genSuccessResult("发送成功").toString();
    }

    /**
     * 跳转 重设密码
     *
     * @param uuid 随机生成的UUID链接
     * @return org.springframework.web.servlet.ModelAndView
     * @create 2020/7/11 15:42
     * @author ☪wl
     */
    @GetMapping("/emailBackPassword/{uuid}")
    public ModelAndView emailBackPassword(@PathVariable("uuid") String uuid, ModelAndView modelAndView) {
        modelAndView.setViewName("newPassword");
        modelAndView.addObject("uuid", uuid);
        modelAndView.addObject("activeLi", "liRegisterPage");
        //查询UUID是否有效
        YggPasswordLink yggPasswordLink = new YggPasswordLink();
        yggPasswordLink.setUUID(uuid);
        YggPasswordLink resultYggPasswordLink = this.yggPasswordLinkService.findOne(yggPasswordLink);
        if (resultYggPasswordLink != null) {
            if (resultYggPasswordLink.getEndTime() > System.currentTimeMillis()) {
                //判断链接是否超时
                modelAndView.addObject("checkOk", true);
                modelAndView.addObject("userName", resultYggPasswordLink.getUsername());
                return modelAndView;
            } else {
                //链接超时，删除链接
                this.yggPasswordLinkService.delete(resultYggPasswordLink);
            }
        }
        modelAndView.addObject("checkOk", false);
        return modelAndView;
    }

    /**
     * 执行修改密码
     *
     * @param userName    用户名(邮箱)
     * @param newPassword 密码
     * @param uuid        随机UUID
     * @return java.lang.String
     * @create 2020/7/11 15:43
     * @author ☪wl
     */
    @ResponseBody
    @PostMapping("/modifyPassword")
    public String modifyPassword(String userName, String newPassword, String uuid) {
        //通过UUID查询链接
        try {
            YggPasswordLink yggPasswordLink = new YggPasswordLink();
            yggPasswordLink.setUUID(uuid);
            YggPasswordLink linkServiceOne = this.yggPasswordLinkService.findOne(yggPasswordLink);
            if (linkServiceOne != null && linkServiceOne.getEndTime() > System.currentTimeMillis()) {
                //修改密码
                YggUser yggUser = new YggUser();
                yggUser.setUsername(userName);
                yggUser.setPassword(newPassword);
                this.yggUserService.changePassword(yggUser, uuid);
            } else {
                throw new DtWebException("链接已过期");
            }
        } catch (Exception e) {
            String msg = "修改密码异常，请联系腐竹";
            if (e instanceof DtWebException) {
                msg = e.getMessage();
            }
            LOG.error(msg, e);
            return ResultGenerator.genFailResult(msg).toString();
        }
        return ResultGenerator.genSuccessResult("修改成功").toString();
    }

}
