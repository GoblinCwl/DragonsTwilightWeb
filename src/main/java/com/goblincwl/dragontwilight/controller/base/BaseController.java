package com.goblincwl.dragontwilight.controller.base;

import com.alibaba.fastjson.JSONObject;
import com.goblincwl.dragontwilight.common.CommonUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author ☪wl
 * @program dragontwilight
 * @description 基础 Controller
 * @create 2020-05-20 9:39
 */
@Controller
@RequestMapping("/")
public class BaseController {

    @GetMapping("/")
    public String index(String goUrl, Model model) {
        if (StringUtils.isEmpty(goUrl)) {
            goUrl = "/indexPage";
        }
        model.addAttribute("goUrl", goUrl);
        return "index";
    }

    @GetMapping("/indexPage")
    public String indexPage(Model model) {
        JSONObject jsonObject = CommonUtils.httpGet("http://web.goblincwl.cn:35074/api/status/MinecraftServer", "");
        if (jsonObject != null) {
            model.addAttribute("status", jsonObject.getBoolean("status"));
            model.addAttribute("online", jsonObject.getString("current_players"));
        }
        return "index/indexPage";
    }

}
