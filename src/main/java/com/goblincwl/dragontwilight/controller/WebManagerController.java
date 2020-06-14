package com.goblincwl.dragontwilight.controller;

import com.goblincwl.dragontwilight.common.EntityMap;
import com.goblincwl.dragontwilight.common.result.Result;
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
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ☪wl
 * @program dragons-twilight-web
 * @description WEB管理 Controller
 * @create 2020-05-25 23:11
 */
@Controller
@RequestMapping("/webManager")
public class WebManagerController {

    private final WebOptionsService webOptionsService;
    private final WebNavIframeService webNavIframeService;

    public WebManagerController(WebOptionsService webOptionsService, WebNavIframeService webNavIframeService) {
        this.webOptionsService = webOptionsService;
        this.webNavIframeService = webNavIframeService;
    }

    @GetMapping("/index")
    public String index(@RequestParam Map<String, Object> param, Model model, HttpServletRequest request) {
        //如果为空跳转到首页
        try {
            String actionId = (String) param.get("actionId");
            //供页面上updateCss()进行样式渲染为本次选择的侧边栏
            model.addAttribute("actionId", StringUtils.isEmpty(actionId) ? "none" : actionId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "webManager/index";
    }

    @GetMapping("/loginPage")
    public String loginPage() {
        return "webManager/login";
    }

    @ResponseBody
    @PostMapping("/login")
    public String login(@RequestParam String password, HttpSession session) {
        Result result;
        try {
            WebOptions webOptions = this.webOptionsService.findByKey("password");
            if (password.equals(webOptions.getOptValue())) {
                result = ResultGenerator.genSuccessResult("登陆成功");
                session.setAttribute("isLogin", "true");
            } else {
                result = ResultGenerator.genFailResult("密码错误");
            }
        } catch (Exception e) {
            result = ResultGenerator.genFailResult(e.getMessage());
            e.printStackTrace();
        }
        return result.toString();
    }

    @RequestMapping("/getSideNavList")
    public String getSideNavList(Model model) {
        //查询侧边栏列表
        List<WebNavIframe> webNavIframeList = this.webNavIframeService.findNavList(new WebNavIframe());
        model.addAttribute("webNavIframeList", webNavIframeList);
        return "fragment/sideNav :: sideNav";
    }

    @ResponseBody
    @PostMapping("/getDashboardData")
    public String getDashboardData(@RequestParam Map<String, Object> param, Model model) throws ParseException {
        EntityMap<String, Object> entityMap = new EntityMap<>(param);
        Map<String, Object> dashboardDate = this.webNavIframeService.getDashboardDate(entityMap);
        return ResultGenerator.genSuccessResult(dashboardDate).toString();
    }

}
