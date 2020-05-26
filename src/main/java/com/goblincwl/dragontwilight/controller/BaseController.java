package com.goblincwl.dragontwilight.controller;

import com.alibaba.fastjson.JSONObject;
import com.goblincwl.dragontwilight.common.CommonConfig;
import com.goblincwl.dragontwilight.common.CommonUtils;
import com.goblincwl.dragontwilight.common.result.ResultGenerator;
import com.goblincwl.dragontwilight.entity.WebNavIframe;
import com.goblincwl.dragontwilight.entity.WebOptions;
import com.goblincwl.dragontwilight.service.WebNavIframeService;
import com.goblincwl.dragontwilight.service.WebOptionsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @GetMapping("/")
    public String index(@RequestParam Map<String, Object> param, Model model, HttpServletRequest request) {
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
                goUrl = goUrl.replace("${webPath}", CommonUtils.getServerUrl(request));
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

}
