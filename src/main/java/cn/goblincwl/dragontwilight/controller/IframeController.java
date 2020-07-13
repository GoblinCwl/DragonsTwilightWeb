package cn.goblincwl.dragontwilight.controller;

import cn.goblincwl.dragontwilight.common.exception.DtWebException;
import cn.goblincwl.dragontwilight.common.result.ResultGenerator;
import cn.goblincwl.dragontwilight.common.utils.CommonUtils;
import cn.goblincwl.dragontwilight.yggdrasil.entity.YggPasswordLink;
import cn.goblincwl.dragontwilight.yggdrasil.entity.YggUser;
import cn.goblincwl.dragontwilight.yggdrasil.service.YggPasswordLinkService;
import cn.goblincwl.dragontwilight.yggdrasil.service.YggUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;


/**
 * @author ☪wl
 * @program dragons-twilight-web
 * @description 基础 Controller
 * @create 2020-05-20 9:39
 */
@Controller
@RequestMapping("/iframe")
public class IframeController {

    private final YggUserService yggUserService;
    private final YggPasswordLinkService yggPasswordLinkService;
    private final Logger LOG = LoggerFactory.getLogger(IframeController.class);

    public IframeController(YggUserService yggUserService, YggPasswordLinkService yggPasswordLinkService) {
        this.yggUserService = yggUserService;
        this.yggPasswordLinkService = yggPasswordLinkService;
    }

    /**
     * 自定义跳转
     *
     * @param url      跳转地址
     * @param activeLi 高亮li
     * @return java.lang.String
     * @create 2020/7/13 22:08
     * @author ☪wl
     */
    @GetMapping("/")
    public String blank(Model model, String url, String activeLi) {
        model.addAttribute("activeLi", activeLi);
        model.addAttribute("goUrl", url);
        return "iframe";
    }

    /**
     * 跳转 卫星地图
     *
     * @return java.lang.String
     * @create 2020/7/13 22:08
     * @author ☪wl
     */
    @GetMapping("/map")
    public String map(Model model) {
        model.addAttribute("activeLi", "liRegisterPage");
        model.addAttribute("activeLi", "liMap");
        model.addAttribute("goUrl", "https://map.goblincwl.cn/");
        return "iframe";
    }

}
