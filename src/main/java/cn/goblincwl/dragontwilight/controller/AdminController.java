package cn.goblincwl.dragontwilight.controller;

import cn.goblincwl.dragontwilight.common.entity.TableHead;
import cn.goblincwl.dragontwilight.common.exception.DtWebException;
import cn.goblincwl.dragontwilight.common.result.ResultGenerator;
import cn.goblincwl.dragontwilight.entity.primary.WebOptions;
import cn.goblincwl.dragontwilight.service.WebOptionsService;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.*;

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

    /**
     * 跳转管理面板首页（仪表盘）
     *
     * @return java.lang.String
     * @create 2020/6/17 22:13
     * @author ☪wl
     */
    @GetMapping("/webOption")
    public String webOption(Model model) {
        model.addAttribute("activeSlot", "adminPick");
        model.addAttribute("activeNav", "webOption");
        List<WebOptions> webOptionsList = this.webOptionsService.findList(new WebOptions());
        model.addAttribute("webOptionsList", webOptionsList);
        return "admin/webOption";
    }

    /**
     * 根据选项ID获取选项
     *
     * @param optionId 选项ID
     * @return java.lang.String
     * @create 2020/7/15 9:53
     * @author ☪wl
     */
    @ResponseBody
    @GetMapping("/getWebOption")
    public String getWebOption(Integer optionId) {
        WebOptions resultWeb;
        try {
            WebOptions webOptions = new WebOptions();
            webOptions.setId(optionId);
            resultWeb = this.webOptionsService.findOne(webOptions);
        } catch (Exception e) {
            return ResultGenerator.autoReturnFailResult("查询异常", LOG, e);
        }
        return ResultGenerator.genSuccessResult(resultWeb).toString();
    }

    /**
     * 获取网站设置表格数据
     *
     * @return java.lang.String
     * @create 2020/7/15 11:34
     * @author ☪wl
     */
    @PostMapping("/getWebOptionList")
    public String getWebOptionList(WebOptions webOptions, Model model, String queryKey, String queryValue) {
        //表头集合
        List<TableHead> arrayList = new ArrayList<>();
        arrayList.add(new TableHead("ID", 5));
        arrayList.add(new TableHead("键", 15));
        arrayList.add(new TableHead("值", 35));
        arrayList.add(new TableHead("备注", 35));
        model.addAttribute("heads", arrayList);

        //数据集合
        List<List<Object>> valueList = new ArrayList<>();
        List<WebOptions> list = this.webOptionsService.findList(webOptions);
        for (WebOptions nowWebOptions : list) {
            List<Object> dataList = new ArrayList<>();
            dataList.add(nowWebOptions.getId());
            dataList.add(nowWebOptions.getOptKey());
            dataList.add(nowWebOptions.getOptValue());
            dataList.add(nowWebOptions.getRemarks());
            valueList.add(dataList);
        }
        model.addAttribute("datas", valueList);

        //可查询集合
        Map<String, String> selects = new LinkedHashMap<>();
        selects.put("optKey", "键");
        selects.put("optValue", "值");
        selects.put("remarks", "备注");
        model.addAttribute("selects", selects);

        //查询参数回显
        model.addAttribute("queryKey", queryKey);
        model.addAttribute("queryValue", queryValue);
        model.addAttribute("queryText", selects.get(queryKey));

        model.addAttribute("tableTitle", "WebOptions设置");
        return "common/tableSrc::hoverTable";
    }

    /**
     * 保存/修改 WebOptions
     *
     * @param webOptions 数据对象
     * @return java.lang.String
     * @create 2020/7/16 23:06
     * @author ☪wl
     */
    @ResponseBody
    @PostMapping("/saveWebOption")
    public String saveWebOption(@Valid WebOptions webOptions, BindingResult bindingResult) {
        String resultMsg;
        try {
            DtWebException.ValidException(bindingResult);
            resultMsg = this.webOptionsService.save(webOptions);
        } catch (Exception e) {
            return ResultGenerator.autoReturnFailResult("保存失败!", LOG, e);
        }
        return ResultGenerator.genSuccessResult(resultMsg).toString();
    }

    /**
     * 删除 WebOptions
     *
     * @param id 唯一主键
     * @return java.lang.String
     * @create 2020/7/16 23:13
     * @author ☪wl
     */
    @ResponseBody
    @PostMapping("/removeWebOptions")
    public String removeWebOptions(@RequestParam Integer id) {
        String resultMsg;
        try {
            WebOptions webOptions = new WebOptions();
            webOptions.setId(id);
            resultMsg = this.webOptionsService.delete(webOptions);
        } catch (Exception e) {
            return ResultGenerator.autoReturnFailResult("删除失败", LOG, e);
        }
        return ResultGenerator.genSuccessResult(resultMsg).toString();
    }

}
