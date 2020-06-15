package com.goblincwl.dragontwilight.controller;

import com.goblincwl.dragontwilight.common.CommonUtils;
import com.goblincwl.dragontwilight.common.exception.DtWebException;
import com.goblincwl.dragontwilight.common.result.ResultGenerator;
import com.goblincwl.dragontwilight.common.systemInfo.Linux;
import com.goblincwl.dragontwilight.entity.WebOptions;
import com.goblincwl.dragontwilight.service.BlessingUsersService;
import com.goblincwl.dragontwilight.service.MinecraftQqPlayerService;
import com.goblincwl.dragontwilight.service.WebOptionsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author ☪wl
 * @program dragons-twilight-web
 * @description 外部接口访问 Controller
 * @create 2020-05-28 9:25
 */
@Controller
@RequestMapping("/outer")
public class OuterController {

    private final static Logger LOG = LoggerFactory.getLogger(OuterController.class);
    private final MinecraftQqPlayerService minecraftQqPlayerService;
    private final WebOptionsService webOptionsService;
    private final BlessingUsersService blessingUsersService;

    public OuterController(MinecraftQqPlayerService minecraftQqPlayerService, WebOptionsService webOptionsService, BlessingUsersService blessingUsersService) {
        this.minecraftQqPlayerService = minecraftQqPlayerService;
        this.webOptionsService = webOptionsService;
        this.blessingUsersService = blessingUsersService;
    }

    /**
     * 获取玩家QQ号
     *
     * @param playerName 玩家名称
     * @return java.lang.String
     * @create 2020/5/28 15:02
     * @author ☪wl
     */
    @ResponseBody
    @GetMapping("/getPlayerQq")
    public String getPlayerQq(@RequestParam String playerName) {
        String qq = this.minecraftQqPlayerService.findQqByPlayerName(playerName);
        if (StringUtils.isEmpty(qq)) {
            return ResultGenerator.genFailResult("未绑定QQ").toString();
        } else {
            return ResultGenerator.genSuccessResult(qq).toString();
        }
    }


    @GetMapping("/systemInfo")
    public String systemInfo() {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("cpuUse", Linux.cpuUsage());
        resultMap.put("diskUse", Linux.disk());
        resultMap.put("memoryUse", Linux.memoryUsage());
        return ResultGenerator.genSuccessResult(resultMap).toString();
    }


    /**
     * 根据用户邮箱huoqu用户皮肤
     *
     * @param userName 用户名（邮箱）
     * @return void
     * @create 2020/6/15 22:16
     * @author ☪wl
     */
    @GetMapping("/getImage")
    public void GetImage(@RequestParam String userName, HttpServletRequest request, HttpServletResponse response) throws IOException {
        InputStream inStream = null;
        OutputStream os = null;

        URL url;
        HttpURLConnection conn;
        try {
            try {
                //通过用户名获取游戏ID
                String playerName = this.blessingUsersService.getPlayerNameByUserName(userName);
                url = new URL("https://skin.goblincwl.cn/skin/" + playerName + ".png");
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(5 * 1000);
                //通过输入流获取图片数据
                inStream = conn.getInputStream();
            } catch (Exception e) {
                url = new URL(CommonUtils.getServerUrl(request) + "/images/steve.png");
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(5 * 1000);
                //通过输入流获取图片数据
                inStream = conn.getInputStream();
            }
            //读取输入流数据
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[2048];
            int len = 0;
            while ((len = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, len);
            }
            byte[] data = outStream.toByteArray();
            inStream.read(data);  //读数据
            response.setContentType("image/png"); //设置返回的文件类型
            response.setHeader("Cache-Control", "private, must-revalidate");
            response.setHeader("pragma", "no-cache");
            os = response.getOutputStream();
            os.write(data);
            os.flush();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        } finally {
            Objects.requireNonNull(inStream).close();
            Objects.requireNonNull(os).close();
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
    @ResponseBody
    @GetMapping("/coolQHttp")
    public String coolQHttp(@RequestParam Map<String, Object> param) {
        //发送人QQ
        String sendQq = (String) param.get("fq");
        //返回内容
        String resultMsg;
        //关键字
        String key = ((String) param.get("key"));
        if (key.startsWith("#功能 ")) {
            resultMsg = "发送人：" + sendQq + ",发送内容：" + key;
        } else if (key.startsWith("#指令 ")) {
            if ("2395025802".equals(sendQq)) {
                //符号后的内容
                String commond = key.substring(4);
                WebOptions webOptions = this.webOptionsService.findByKey("lodeWebSocketApiUri");
                CommonUtils.webSocketSend(webOptions.getOptValue(), "{\"action\": \"executeCmd\", \"params\": {\"command\": \"" + commond + "\"}}");
                resultMsg = "执行指令 /" + commond + " 成功！";
            } else {
                resultMsg = "没有权限";
            }
        } else {
            resultMsg = "none";
        }
        return " {\"return_code\":0,\"return_message\":\"" + resultMsg + "\",\"appver\":0,\"update_url\":\"\",\"return_type\":104}";
    }
}
