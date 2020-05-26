package com.goblincwl.dragontwilight.controller;

import com.goblincwl.dragontwilight.common.result.Result;
import com.goblincwl.dragontwilight.common.result.ResultGenerator;
import com.goblincwl.dragontwilight.entity.WebNavIframe;
import com.goblincwl.dragontwilight.entity.WebOptions;
import com.goblincwl.dragontwilight.service.WebNavIframeService;
import com.goblincwl.dragontwilight.service.WebOptionsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
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
    public String index(Model model) {
//        String webManagerIndexUri = "${webPath}/webManager/index";
//        WebNavIframe webNavIframe = this.webNavIframeService.findNavByUri(webManagerIndexUri);
//        model.addAttribute("webNavId", webNavIframe.getId());
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

    @ResponseBody
    @PostMapping("/loginCheck")
    public String loginCheck(@RequestParam Map<String, Object> param) {
        System.out.println(param.toString());
        return "webManager/login";
    }

}
