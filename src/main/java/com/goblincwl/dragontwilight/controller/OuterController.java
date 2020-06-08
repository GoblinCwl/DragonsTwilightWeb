package com.goblincwl.dragontwilight.controller;

import com.goblincwl.dragontwilight.common.CommonUtils;
import com.goblincwl.dragontwilight.common.result.ResultGenerator;
import com.goblincwl.dragontwilight.entity.WebOptions;
import com.goblincwl.dragontwilight.service.MinecraftQqPlayerService;
import com.goblincwl.dragontwilight.service.WebOptionsService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author ☪wl
 * @program dragons-twilight-web
 * @description 外部接口访问 Controller
 * @create 2020-05-28 9:25
 */
@RestController
@RequestMapping("/outer")
public class OuterController {

    private final MinecraftQqPlayerService minecraftQqPlayerService;
    private final WebOptionsService webOptionsService;

    public OuterController(MinecraftQqPlayerService minecraftQqPlayerService, WebOptionsService webOptionsService) {
        this.minecraftQqPlayerService = minecraftQqPlayerService;
        this.webOptionsService = webOptionsService;
    }

    /**
     * 获取玩家QQ号
     *
     * @param playerName 玩家名称
     * @return java.lang.String
     * @create 2020/5/28 15:02
     * @author ☪wl
     */
    @GetMapping("/getPlayerQq")
    public String getPlayerQq(@RequestParam String playerName) {
        CommonUtils.webSocketSend("ws://web.goblincwl.cn:35078", "{\"action\": \"executeCmd\", \"params\": {\"command\": \"say webSocket连接测试\"}}");


        String qq = this.minecraftQqPlayerService.findQqByPlayerName(playerName);
        if (StringUtils.isEmpty(qq)) {
            return ResultGenerator.genFailResult("未绑定QQ").toString();
        } else {
            return ResultGenerator.genSuccessResult(qq).toString();
        }
    }

    /**
     * 酷Q Http接口插件 调用地址
     *
     * @param param:{ fg: 来源群号
     *                fq: 来源QQ号
     *                key: 包括关键字的内容
     *                }
     * @return java.lang.String
     * @create 2020/5/28 15:03
     * @author ☪wl
     * @document
     */
    @GetMapping("/coolQHttp")
    public String coolQHttp(@RequestParam Map<String, Object> param) {
        String key = ((String) param.get("key"));
        if (key.startsWith("#功能 ")) {
            //符号后的内容
            String value = key.substring(4);
            //发送人QQ
            String sendQq = (String) param.get("fq");
            //返回内容
            String resultMsg;
            if (value.startsWith("执行指令 ")) {
                if ("2395025802".equals(sendQq)) {
                    String commond = value.substring(5);
                    WebOptions webOptions = this.webOptionsService.findByKey("lodeWebSocketApiUri");
                    CommonUtils.webSocketSend(webOptions.getOptValue(), "{\"action\": \"executeCmd\", \"params\": {\"command\": \"" + commond + "\"}}");
                    resultMsg = "执行指令 /" + commond + " 成功！";
                } else {
                    resultMsg = "你没有权限！";
                }
            } else {
                resultMsg = "发送人：" + sendQq + ",发送内容：" + value;
            }

            return " {\"return_code\":0,\"return_message\":\"" + resultMsg + "\",\"appver\":0,\"update_url\":\"\",\"return_type\":104}";
        }
        return null;
    }
}
