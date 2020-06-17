package com.goblincwl.dragontwilight.controller;

import com.alibaba.fastjson.JSONObject;
import com.goblincwl.dragontwilight.common.exception.DtWebException;
import com.goblincwl.dragontwilight.common.utils.CommonUtils;
import com.goblincwl.dragontwilight.common.result.ResultGenerator;
import com.goblincwl.dragontwilight.entity.primary.BlessingUuid;
import com.goblincwl.dragontwilight.entity.primary.MinecraftQqPlayer;
import com.goblincwl.dragontwilight.entity.primary.WebOptions;
import com.goblincwl.dragontwilight.entity.slave.VexSign;
import com.goblincwl.dragontwilight.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
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
    private final VexSignService vexSignService;
    private final BlessingUuidService blessingUuidService;

    public OuterController(MinecraftQqPlayerService minecraftQqPlayerService, WebOptionsService webOptionsService, BlessingUsersService blessingUsersService, VexSignService vexSignService, BlessingUuidService blessingUuidService) {
        this.minecraftQqPlayerService = minecraftQqPlayerService;
        this.webOptionsService = webOptionsService;
        this.blessingUsersService = blessingUsersService;
        this.vexSignService = vexSignService;
        this.blessingUuidService = blessingUuidService;
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
        String qq;
        try {
            MinecraftQqPlayer minecraftQqPlayer = new MinecraftQqPlayer();
            minecraftQqPlayer.setName(playerName);
            MinecraftQqPlayer minecraftQqPlayerResult = this.minecraftQqPlayerService.findOne(minecraftQqPlayer);
            if (minecraftQqPlayerResult == null) {
                throw new DtWebException("未绑定QQ！");
            }
            qq = minecraftQqPlayerResult.getQq();
            if (StringUtils.isEmpty(qq)) {
                throw new DtWebException("QQ号无效！");
            }
        } catch (Exception e) {
            return ResultGenerator.autoReturnFailResult("获取玩家QQ号异常!", LOG, e);
        }
        return ResultGenerator.genSuccessResult(qq).toString();
    }

    /**
     * 根据用户邮箱获取用户皮肤
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
                LOG.info("获取皮肤时名称：" + userName);
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
        //返回内容
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("return_code", 0);
        jsonObject.put("appver", 0);
        jsonObject.put("update_url", "");
        jsonObject.put("return_type", 104);
        //返回消息
        String message;
        try {
            //发送人QQ
            String sendQq = (String) param.get("fq");
            //关键字
            String key = ((String) param.get("key"));
            //执行方法
            message = doCoolQWork(sendQq, key);
        } catch (Exception e) {
            message = (e instanceof DtWebException) ? e.getMessage() : "酷Q接口请求异常！";
            LOG.error(message, e);
        }
        jsonObject.put("return_message", message);
        return " " + jsonObject.toJSONString();
    }

    /**
     * 执行酷Q传过来的方法
     *
     * @param sendQq 发送人QQ
     * @param key    发送内容
     * @return java.lang.String
     * @create 2020/6/16 22:46
     * @author ☪wl
     */
    public String doCoolQWork(String sendQq, String key) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //群主专用
        if ("2395025802".equals(sendQq)) {
            if (key.startsWith("#指令 ")) {
                //符号后的内容
                String commond = key.substring(4);
                WebOptions webOptions = this.webOptionsService.findByKey("lodeWebSocketApiUri");
                //封装webSocket信息发送格式
                JSONObject paramJson = new JSONObject();
                paramJson.put("command", commond);
                CommonUtils.webSocketSend(webOptions.getOptValue(), CommonUtils.lodeApiParam("executeCmd", paramJson).toJSONString());
                return "执行指令 /" + commond + " 成功！";
            }
        }

        if (key.startsWith("#签到")) {
            //获取QQ对应角色名
            MinecraftQqPlayer minecraftQqPlayer = new MinecraftQqPlayer();
            minecraftQqPlayer.setQq(sendQq);
            MinecraftQqPlayer minecraftQqPlayerResult = this.minecraftQqPlayerService.findOne(minecraftQqPlayer);
            if (minecraftQqPlayerResult == null) {
                throw new DtWebException("@" + sendQq + "，签到失败！\n请先绑定游戏ID！");
            }
            //玩家名称
            String playerName = minecraftQqPlayerResult.getName();
            //获取角色名对应UUID
            BlessingUuid blessingUuid = new BlessingUuid();
            blessingUuid.setName(playerName);
            BlessingUuid blessingUuidResult = this.blessingUuidService.findONe(blessingUuid);
            if (blessingUuidResult == null) {
                throw new DtWebException("@" + playerName + "，签到失败！\n数据异常，请联系管理员。");
            }
            //查询玩家签到数据
            VexSign vexSign = new VexSign();
            vexSign.setSignUuid(CommonUtils.convertUUId(blessingUuidResult.getUuid()));
            VexSign vexSignResult = this.vexSignService.findOne(vexSign);
            if (vexSignResult == null) {
                throw new DtWebException("@" + playerName + "，签到失败！\n你至少要登陆过一次客户端才能在群内签到！");
            }
            //通过反射获取当前日期的get方法
            String nowDay = String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
            Method getSignDayMethod = vexSignResult.getClass().getMethod("getSignDay" + nowDay);
            String signDay = String.valueOf(getSignDayMethod.invoke(vexSignResult));
            if ("1".equals(signDay)) {
                throw new DtWebException("@" + playerName + "，签到失败，你今天已经签到过了！！");
            } else {
                //签到
                WebOptions webOptions = this.webOptionsService.findByKey("lodeWebSocketApiUri");
                //发送WebSocket指令
                JSONObject paramJson = new JSONObject();
                //发送MailBox邮箱
                paramJson.put("command", "mb send signQQ player " + playerName);
                CommonUtils.webSocketSend(webOptions.getOptValue(), CommonUtils.lodeApiParam("executeCmd", paramJson).toJSONString());
                //修改数据库为已签到
                Method setSignDayMethod = vexSignResult.getClass().getMethod("setSignDay" + nowDay, Integer.class);
                setSignDayMethod.invoke(vexSignResult, 1);
                this.vexSignService.update(vexSignResult);
            }
            return "@" + playerName + "，签到成功！\n奖励已发放至邮箱！";
        } else {
            return "发送人：" + sendQq + ",发送内容：" + key;
        }
    }
}
