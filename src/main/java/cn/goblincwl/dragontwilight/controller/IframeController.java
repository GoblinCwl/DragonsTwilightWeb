package cn.goblincwl.dragontwilight.controller;

import cn.goblincwl.dragontwilight.entity.primary.WebOptions;
import cn.goblincwl.dragontwilight.service.WebOptionsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


/**
 * @author ☪wl
 * @program dragons-twilight-web
 * @description 基础 Controller
 * @create 2020-05-20 9:39
 */
@Controller
@RequestMapping("/iframe")
public class IframeController {

    private final Logger LOG = LoggerFactory.getLogger(IframeController.class);
    private final WebOptionsService webOptionsService;

    public IframeController(WebOptionsService webOptionsService) {
        this.webOptionsService = webOptionsService;
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
        model.addAttribute("activeSlot", "mapPick");
        model.addAttribute("title", "地皮地图");
        WebOptions dynMapUrl = this.webOptionsService.findByKey("dynMapUrl");
        model.addAttribute("goUrl", dynMapUrl.getOptValue());
        return "iframe";
    }

}
