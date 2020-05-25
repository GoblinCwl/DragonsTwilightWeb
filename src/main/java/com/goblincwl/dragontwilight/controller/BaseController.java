package com.goblincwl.dragontwilight.controller;

import com.alibaba.fastjson.JSONObject;
import com.goblincwl.dragontwilight.common.CommonConfig;
import com.goblincwl.dragontwilight.common.CommonUtils;
import com.goblincwl.dragontwilight.common.ResultGenerator;
import com.goblincwl.dragontwilight.entity.WebNavIframe;
import com.goblincwl.dragontwilight.service.WebNavIframeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.UnknownHostException;
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

    private final CommonConfig commonConfig;
    private final WebNavIframeService webNavIframeService;

    public BaseController(WebNavIframeService webNavIframeService, CommonConfig commonConfig) {
        this.webNavIframeService = webNavIframeService;
        this.commonConfig = commonConfig;
    }

    @GetMapping("/")
    public String index(@RequestParam Map<String, Object> param, Model model) {
        //如果为空跳转到首页
        try {
            String goUrl = (String) param.get("goUrl");
            String actionId = (String) param.get("actionId");
            String isIframe = (String) param.get("isIframe");
            if (!"1".equals(isIframe)) {
                List<WebNavIframe> webNavIframeList = this.webNavIframeService.findNavList(new WebNavIframe());
                model.addAttribute("webNavIframeList", webNavIframeList);
            }
            if (StringUtils.isEmpty(goUrl)) {
                goUrl = "/indexPage";
            } else {
                goUrl = goUrl.replace("${webPath}", CommonUtils.commonUtils.getSpringBootUrl());
            }
            model.addAttribute("actionId", StringUtils.isEmpty(actionId) ? "none" : actionId);
            model.addAttribute("goUrl", goUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "index";
    }

    @GetMapping("/indexPage")
    public String indexPage(Model model) {
        model.addAttribute("clientDownloadUrl", commonConfig.clientDownloadUrl);
        return "index/indexPage";
    }

    @ResponseBody
    @GetMapping("/serverStatus")
    public String serverStatus() {
        //获取MCSManager接口的在线人数和服务器状态
        JSONObject jsonObject = CommonUtils.httpGet(commonConfig.mcsManagerApi, "");
        return ResultGenerator.genSuccessResult(jsonObject).toString();
    }

}
