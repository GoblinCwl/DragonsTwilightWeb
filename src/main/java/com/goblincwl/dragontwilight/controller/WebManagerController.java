package com.goblincwl.dragontwilight.controller;

import com.goblincwl.dragontwilight.common.entity.EntityMap;
import com.goblincwl.dragontwilight.common.exception.DtWebException;
import com.goblincwl.dragontwilight.common.result.Result;
import com.goblincwl.dragontwilight.common.result.ResultGenerator;
import com.goblincwl.dragontwilight.entity.primary.WebNavIframe;
import com.goblincwl.dragontwilight.entity.primary.WebOptions;
import com.goblincwl.dragontwilight.service.WebNavIframeService;
import com.goblincwl.dragontwilight.service.WebOptionsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
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

    private final Logger LOG = LoggerFactory.getLogger(WebManagerController.class);
    private final WebOptionsService webOptionsService;
    private final WebNavIframeService webNavIframeService;

    public WebManagerController(WebOptionsService webOptionsService, WebNavIframeService webNavIframeService) {
        this.webOptionsService = webOptionsService;
        this.webNavIframeService = webNavIframeService;
    }

    /**
     * 跳转管理面板首页（仪表盘）
     *
     * @param param 数据参数
     * @return java.lang.String
     * @create 2020/6/17 22:13
     * @author ☪wl
     * @document ShowDoc:
     */
    @GetMapping("/index")
    public String index(@RequestParam Map<String, Object> param, Model model) {
        String actionId = (String) param.get("actionId");
        //供页面上updateCss()进行样式渲染为本次选择的侧边栏
        model.addAttribute("actionId", StringUtils.isEmpty(actionId) ? "none" : actionId);
        return "webManager/index";
    }

    /**
     * 跳转登陆页面
     *
     * @return java.lang.String
     * @create 2020/6/17 22:12
     * @author ☪wl
     */
    @GetMapping("/loginPage")
    public String loginPage() {
        return "webManager/login";
    }

    /**
     * 用户登录
     *
     * @param password 密码
     * @return java.lang.String
     * @create 2020/6/17 22:11
     * @author ☪wl
     */
    @ResponseBody
    @PostMapping("/login")
    public String login(@RequestParam String password, HttpSession session) {
        String resultStr;
        try {
            WebOptions webOptions = this.webOptionsService.findByKey("password");
            if (password.equals(webOptions.getOptValue())) {
                resultStr = "登陆成功";
                session.setAttribute("isLogin", "true");
            } else {
                throw new DtWebException("密码错误");
            }
        } catch (Exception e) {
            return ResultGenerator.autoReturnFailResult("登陆异常，请联系管理员", LOG, e);
        }
        return ResultGenerator.genSuccessResult(resultStr).toString();
    }

    /**
     * 获取侧边栏菜单（管理员版）
     *
     * @return java.lang.String
     * @create 2020/6/17 22:10
     * @author ☪wl
     */
    @RequestMapping("/getSideNavList")
    public String getSideNavList(Model model) {
        //查询侧边栏列表
        List<WebNavIframe> webNavIframeList = this.webNavIframeService.findNavList(new WebNavIframe());
        model.addAttribute("webNavIframeList", webNavIframeList);
        //返回thymeleaf模块
        return "fragment/sideNav :: sideNav";
    }

    /**
     * 获取仪表盘信息
     *
     * @param param 数据参数
     * @return java.lang.String
     * @create 2020/6/17 22:09
     * @author ☪wl
     */
    @ResponseBody
    @PostMapping("/getDashboardData")
    public String getDashboardData(@RequestParam Map<String, Object> param) {
        Map<String, Object> dashboardDate;
        try {
            EntityMap<String, Object> entityMap = new EntityMap<>(param);
            dashboardDate = this.webNavIframeService.getDashboardDate(entityMap);
        } catch (Exception e) {
            return ResultGenerator.autoReturnFailResult("仪表盘信息获取失败！", LOG, e);
        }
        return ResultGenerator.genSuccessResult(dashboardDate).toString();
    }

}
