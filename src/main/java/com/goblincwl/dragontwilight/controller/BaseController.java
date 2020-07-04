package com.goblincwl.dragontwilight.controller;

import com.alibaba.fastjson.JSONObject;
import com.goblincwl.dragontwilight.common.exception.DtWebException;
import com.goblincwl.dragontwilight.common.utils.CommonUtils;
import com.goblincwl.dragontwilight.common.result.ResultGenerator;
import com.goblincwl.dragontwilight.entity.primary.WebNavIframe;
import com.goblincwl.dragontwilight.entity.primary.WebOptions;
import com.goblincwl.dragontwilight.service.WebNavIframeService;
import com.goblincwl.dragontwilight.service.WebOptionsService;
import com.goblincwl.dragontwilight.yggdrasil.entity.YggPasswordLink;
import com.goblincwl.dragontwilight.yggdrasil.entity.YggUser;
import com.goblincwl.dragontwilight.yggdrasil.service.YggPasswordLinkService;
import com.goblincwl.dragontwilight.yggdrasil.service.YggUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


/**
 * @author ☪wl
 * @program dragons-twilight-web
 * @description 基础 Controller
 * @create 2020-05-20 9:39
 */
@Controller
@RequestMapping("/")
public class BaseController {

    private final WebNavIframeService webNavIframeService;
    private final WebOptionsService webOptionsService;
    private final YggUserService yggUserService;
    private final YggPasswordLinkService yggPasswordLinkService;
    private final Logger LOG = LoggerFactory.getLogger(BaseController.class);

    public BaseController(WebNavIframeService webNavIframeService, WebOptionsService webOptionsService, YggUserService yggUserService, YggPasswordLinkService yggPasswordLinkService) {
        this.webNavIframeService = webNavIframeService;
        this.webOptionsService = webOptionsService;
        this.yggUserService = yggUserService;
        this.yggPasswordLinkService = yggPasswordLinkService;
    }

    /**
     * "/" 跳转
     *
     * @return java.lang.String
     * @create 2020/6/14 1:24
     * @author ☪wl
     */
    @GetMapping("/")
    public String index(@RequestParam Map<String, Object> param, Model model, HttpServletRequest request) {
        //如果为空跳转到首页
        try {
            String goUrl = (String) param.get("goUrl");
            String actionId = (String) param.get("actionId");
            //如果有参数goUrl,则跳转至指定URL，否则跳转至首页(index/indexPage)
            if (StringUtils.isEmpty(goUrl)) {
                //供页面判断是否刷新侧边栏
                model.addAttribute("loadSideNav", "1");
                goUrl = "/indexPage";
            } else {
                //将webPath替换为本项目路劲
                goUrl = goUrl.replace("${webPath}", CommonUtils.getServerUrl(request));
            }
            //供页面上updateCss()进行样式渲染为本次选择的侧边栏
            model.addAttribute("actionId", StringUtils.isEmpty(actionId) ? "7" : actionId);
            //供页面上iframe识别目的地
            model.addAttribute("goUrl", goUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "index";
    }

    @GetMapping("/registerPage")
    public String registerPage() {
        return "register";
    }

    @ResponseBody
    @PostMapping("/register")
    public String register(HttpServletRequest request, String email, String password, String playerName) {
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

    @GetMapping("/emailBackPassword/{uuid}")
    public ModelAndView emailBackPassword(@PathVariable("uuid") String uuid, ModelAndView modelAndView) {
        modelAndView.setViewName("backPassword");
        modelAndView.addObject("uuid", uuid);
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

    @GetMapping("/indexPage")
    public String indexPage(Model model) {
        //获取下载地址
        WebOptions webOptions = this.webOptionsService.findByKey("clientDownloadUrl");
        model.addAttribute("clientDownloadUrl", webOptions.getOptValue());
        return "index/indexPage";
    }

    @ResponseBody
    @GetMapping("/serverStatus")
    public String serverStatus() {
        //获取API地址
        WebOptions webOptions = this.webOptionsService.findByKey("mcsMangerApiUrl");
        //获取MCSManager接口的在线人数和服务器状态
        JSONObject jsonObject = CommonUtils.httpGet(webOptions.getOptValue(), "");
        return ResultGenerator.genSuccessResult(jsonObject).toString();
    }

    @RequestMapping("/getSideNavList")
    public String getSideNavList(Model model) {
        //查询侧边栏列表
        WebNavIframe webNavIframe = new WebNavIframe();
        webNavIframe.setIsAdmin(0);
        List<WebNavIframe> webNavIframeList = this.webNavIframeService.findNavList(webNavIframe);
        model.addAttribute("webNavIframeList", webNavIframeList);
        return "fragment/sideNav :: sideNav";
    }

}
