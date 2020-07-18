package cn.goblincwl.dragontwilight.controller.admin;

import cn.goblincwl.dragontwilight.common.entity.TableHead;
import cn.goblincwl.dragontwilight.common.exception.DtWebException;
import cn.goblincwl.dragontwilight.common.result.ResultGenerator;
import cn.goblincwl.dragontwilight.entity.primary.WebOptions;
import cn.goblincwl.dragontwilight.service.WebOptionsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ☪wl
 * @program dragons-twilight-web
 * @description WebOption Controller
 * @create 2020-05-25 23:11
 */
@Controller
@RequestMapping("/admin/webOption")
public class WebOptionController {

    private final Logger LOG = LoggerFactory.getLogger(WebOptionController.class);
    private final WebOptionsService webOptionsService;

    public WebOptionController(WebOptionsService webOptionsService) {
        this.webOptionsService = webOptionsService;
    }

    /**
     * 跳转管理面板首页（仪表盘）
     *
     * @return java.lang.String
     * @create 2020/6/17 22:13
     * @author ☪wl
     */
    @GetMapping
    public String webOption(Model model) {
        model.addAttribute("activeSlot", "adminPick");
        model.addAttribute("activeNav", "webOption");
        return "admin/webOption";
    }

    /**
     * 根据选项ID获取选项
     *
     * @param webOptions 数据对象
     * @return java.lang.String
     * @create 2020/7/15 9:53
     * @author ☪wl
     */
    @ResponseBody
    @RequestMapping("/findList")
    public String findList(WebOptions webOptions) {
        List<WebOptions> resultList;
        try {
            resultList = this.webOptionsService.findList(webOptions);
        } catch (Exception e) {
            return ResultGenerator.autoReturnFailResult("查询异常", LOG, e);
        }
        return ResultGenerator.genSuccessResult(resultList).toString();
    }

    @ResponseBody
    @RequestMapping("/findOne")
    public String findOne(WebOptions webOptions) {
        WebOptions resultWebOptions;
        try {
            resultWebOptions = this.webOptionsService.findOne(webOptions);
        } catch (Exception e) {
            return ResultGenerator.autoReturnFailResult("查询失败", LOG, e);
        }
        return ResultGenerator.genSuccessResult(resultWebOptions).toString();
    }

    /**
     * 获取网站设置表格数据
     *
     * @return java.lang.String
     * @create 2020/7/15 11:34
     * @author ☪wl
     */
    @PostMapping("/findListHtml")
    public String findListHtml(WebOptions webOptions, Model model, String queryKey, String queryValue) {
        //表头集合
        List<TableHead> arrayList = new ArrayList<>();
        arrayList.add(new TableHead("ID", 1));
        arrayList.add(new TableHead("键", 3));
        arrayList.add(new TableHead("值", 6));
        arrayList.add(new TableHead("备注", 6));
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
    @PostMapping("/save")
    public String save(@Valid WebOptions webOptions, BindingResult bindingResult) {
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
    @PostMapping("/remove")
    public String remove(@RequestParam Integer id) {
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
