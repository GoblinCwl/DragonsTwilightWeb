package com.goblincwl.dragontwilight.controller.base;

import com.alibaba.fastjson.JSONObject;
import com.goblincwl.dragontwilight.common.CommonConfig;
import com.goblincwl.dragontwilight.common.CommonUtils;
import com.goblincwl.dragontwilight.entity.base.WebNavIframe;
import com.goblincwl.dragontwilight.service.base.BaseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


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
    private final BaseService baseService;

    public BaseController(BaseService baseService, CommonConfig commonConfig) {
        this.baseService = baseService;
        this.commonConfig = commonConfig;
    }

    @GetMapping("/")
    public String index(String goUrl, String actionId, Model model) {
        //如果为空跳转到首页
        if (StringUtils.isEmpty(goUrl)) {
            goUrl = "/indexPage";
        }
        List<WebNavIframe> webNavIframeList = this.baseService.findNavList(new WebNavIframe());
        //获取MCSManager接口的在线人数和服务器状态
        JSONObject jsonObject = CommonUtils.httpGet(commonConfig.mcsManagerApi, "");
        if (jsonObject != null) {
            model.addAttribute("status", jsonObject.getBoolean("status"));
            model.addAttribute("online", StringUtils.isEmpty(jsonObject.getString("current_players")) ? "0" : jsonObject.getString("current_players"));
        }
        model.addAttribute("goUrl", goUrl);
        model.addAttribute("clientDownloadUrl", commonConfig.clientDownloadUrl);
        model.addAttribute("webNavIframeList", webNavIframeList);
        model.addAttribute("actionId", StringUtils.isEmpty(actionId) ? "none" : actionId);
        return "index";
    }

    @GetMapping("/indexPage")
    public String indexPage(Model model) {

        return "index/indexPage";
    }

}
