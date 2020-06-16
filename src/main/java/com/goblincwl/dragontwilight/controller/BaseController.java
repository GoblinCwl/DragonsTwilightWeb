package com.goblincwl.dragontwilight.controller;

import com.alibaba.fastjson.JSONObject;
import com.goblincwl.dragontwilight.common.utils.CommonUtils;
import com.goblincwl.dragontwilight.common.result.ResultGenerator;
import com.goblincwl.dragontwilight.entity.WebNavIframe;
import com.goblincwl.dragontwilight.entity.WebOptions;
import com.goblincwl.dragontwilight.service.WebNavIframeService;
import com.goblincwl.dragontwilight.service.WebOptionsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

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

    public BaseController(WebNavIframeService webNavIframeService, WebOptionsService webOptionsService) {
        this.webNavIframeService = webNavIframeService;
        this.webOptionsService = webOptionsService;
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
