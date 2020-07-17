package cn.goblincwl.dragontwilight.controller.admin;

import cn.goblincwl.dragontwilight.common.exception.DtWebException;
import cn.goblincwl.dragontwilight.common.result.ResultGenerator;
import cn.goblincwl.dragontwilight.entity.primary.WebOptions;
import cn.goblincwl.dragontwilight.service.WebOptionsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @author ☪wl
 * @program dragons-twilight-web
 * @description WEB管理 Controller
 * @create 2020-05-25 23:11
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    private final Logger LOG = LoggerFactory.getLogger(AdminController.class);
    private final WebOptionsService webOptionsService;

    public AdminController(WebOptionsService webOptionsService) {
        this.webOptionsService = webOptionsService;
    }

    /**
     * 跳转登陆页面
     *
     * @return java.lang.String
     * @create 2020/6/17 22:12
     * @author ☪wl
     */
    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("activeSlot", "adminPick");
        return "admin/login";
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
    @PostMapping("/doLogin")
    public String doLogin(@RequestParam String password, HttpSession session) {
        String resultStr;
        try {
            WebOptions webOptions = this.webOptionsService.findByKey("adminPassword");
            if (password.equals(webOptions.getOptValue())) {
                resultStr = "登陆成功";
                session.setAttribute("isLogin", "true");
            } else {
                throw new DtWebException("密码错误,password");
            }
        } catch (Exception e) {
            return ResultGenerator.autoReturnFailResult("登陆异常，请联系管理员", LOG, e);
        }
        return ResultGenerator.genSuccessResult(resultStr).toString();
    }

    /**
     * 跳转管理面板首页（仪表盘）
     *
     * @return java.lang.String
     * @create 2020/6/17 22:13
     * @author ☪wl
     */
    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("activeSlot", "adminPick");
        model.addAttribute("activeNav", "dashboard");
        return "admin/index";
    }
}
