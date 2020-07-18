package cn.goblincwl.dragontwilight.controller.admin;

import cn.goblincwl.dragontwilight.common.entity.TableHead;
import cn.goblincwl.dragontwilight.common.result.ResultGenerator;
import cn.goblincwl.dragontwilight.yggdrasil.entity.YggUser;
import cn.goblincwl.dragontwilight.yggdrasil.service.YggUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ☪wl
 * @program MinecrafProject
 * @description 用户管理 Controller
 * @create 2020-07-18 10:46
 */
@Controller
@RequestMapping("/admin/userManager")
public class UserManagerController {

    private final Logger LOG = LoggerFactory.getLogger(UserManagerController.class);
    private final YggUserService yggUserService;

    public UserManagerController(YggUserService yggUserService) {
        this.yggUserService = yggUserService;
    }

    @GetMapping
    public String redirect(Model model) {
        model.addAttribute("activeSlot", "adminPick");
        model.addAttribute("activeNav", "userManager");
        return "userManager";
    }

    @ResponseBody
    @RequestMapping("/findList")
    public String findList(YggUser yggUser) {
        List<YggUser> resultList;
        try {
            resultList = this.yggUserService.findList(yggUser);
        } catch (Exception e) {
            return ResultGenerator.autoReturnFailResult("查询异常", LOG, e);
        }
        return ResultGenerator.genSuccessResult(resultList).toString();
    }

    @ResponseBody
    @RequestMapping("/findPage")
    public String findPage(YggUser yggUser) {
        Page<YggUser> resultList;
        try {
            resultList = this.yggUserService.findPage(yggUser, PageRequest.of(0, 10));
        } catch (Exception e) {
            return ResultGenerator.autoReturnFailResult("查询异常", LOG, e);
        }
        return ResultGenerator.genSuccessResult(resultList).toString();
    }

    @PostMapping("/findListHtml")
    public String findListHtml(Model model, YggUser yggUser, String queryKey, String queryValue) {
        //表头集合
        List<TableHead> arrayList = new ArrayList<>();
        arrayList.add(new TableHead("UUID", 4));
        arrayList.add(new TableHead("角色名称", 2));
        arrayList.add(new TableHead("邮箱", 3));
        arrayList.add(new TableHead("密码", 2));
        model.addAttribute("heads", arrayList);

        //数据集合
        List<List<Object>> valueList = new ArrayList<>();
        Page<YggUser> page = this.yggUserService.findPage(yggUser, PageRequest.of(0, 32, Sort.by(Sort.Direction.ASC, "playerName")));
        List<YggUser> list = page.getContent();
        for (YggUser nowYggUser : list) {
            List<Object> dataList = new ArrayList<>();
            dataList.add(nowYggUser.getUUID());
            dataList.add(nowYggUser.getPlayerName());
            dataList.add(nowYggUser.getUsername());
            dataList.add(nowYggUser.getPassword());
            valueList.add(dataList);
        }
        model.addAttribute("datas", valueList);

        //可查询集合
        Map<String, String> selects = new LinkedHashMap<>();
        selects.put("UUID", "UUID");
        selects.put("playerName", "角色名称");
        selects.put("username", "邮箱");
        model.addAttribute("selects", selects);

        //查询参数回显
        model.addAttribute("queryKey", queryKey);
        model.addAttribute("queryValue", queryValue);
        model.addAttribute("queryText", selects.get(queryKey));

        model.addAttribute("tableTitle", "用户管理");

        return "common/tableSrc::hoverTable";
    }

    public String save(@Valid YggUser yggUser, BindingResult bindingResult) {
        //TODO
        return null;
    }

    public String remove(Integer id) {
        //TODO
        return null;
    }
}
