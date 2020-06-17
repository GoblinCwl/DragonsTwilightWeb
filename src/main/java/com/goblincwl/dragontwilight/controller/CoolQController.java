package com.goblincwl.dragontwilight.controller;

import com.alibaba.fastjson.JSONObject;
import com.goblincwl.dragontwilight.common.config.DragonsTwilightWebConfig;
import com.goblincwl.dragontwilight.common.exception.DtWebException;
import com.goblincwl.dragontwilight.common.utils.CommonUtils;
import com.goblincwl.dragontwilight.entity.primary.BlessingUuid;
import com.goblincwl.dragontwilight.entity.primary.MinecraftQqPlayer;
import com.goblincwl.dragontwilight.entity.primary.WebOptions;
import com.goblincwl.dragontwilight.entity.slave.MailboxPlayer;
import com.goblincwl.dragontwilight.entity.slave.VexSign;
import com.goblincwl.dragontwilight.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * @author ☪wl
 * @program dragons-twilight-web
 * @description 酷Q专用API
 * @create 2020-06-17 17:13
 */
@RestController
@RequestMapping("/")
public class CoolQController {

    private final Logger LOG = LoggerFactory.getLogger(CoolQController.class);

    private final MinecraftQqPlayerService minecraftQqPlayerService;
    private final WebOptionsService webOptionsService;
    private final VexSignService vexSignService;
    private final BlessingUuidService blessingUuidService;
    private final DragonsTwilightWebConfig dragonsTwilightWebConfig;
    private final MailboxPlayerService mailboxPlayerService;

    public CoolQController(MinecraftQqPlayerService minecraftQqPlayerService, WebOptionsService webOptionsService, VexSignService vexSignService, BlessingUuidService blessingUuidService, DragonsTwilightWebConfig dragonsTwilightWebConfig, MailboxPlayerService mailboxPlayerService) {
        this.minecraftQqPlayerService = minecraftQqPlayerService;
        this.webOptionsService = webOptionsService;
        this.vexSignService = vexSignService;
        this.blessingUuidService = blessingUuidService;
        this.dragonsTwilightWebConfig = dragonsTwilightWebConfig;
        this.mailboxPlayerService = mailboxPlayerService;
    }

    /**
     * 酷Q指令常量
     */
    private final static String SIGN = "#签到";
    private final static String MAIL = "#邮箱";
    private final static String MANAGER = "#管理";
    private final static String MANAGER_COMMAND = "指令";

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
    @GetMapping("/coolQ")
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
            message = this.doCoolQWork(sendQq, key);
        } catch (Exception e) {
            message = (e instanceof DtWebException) ? e.getMessage() : "酷Q接口请求异常！\n请联系管理员。";
            LOG.error(message, e);
        }
        jsonObject.put("return_message", message);
        //前面加空格 对其DOM请求头
        return " " + jsonObject.toJSONString();
    }

    /**
     * 执行酷Q方法选择器
     *
     * @param sendQq 发送人QQ
     * @param key    发送内容
     * @return java.lang.String
     * @create 2020/6/16 22:46
     * @author ☪wl
     */
    public String doCoolQWork(String sendQq, String key) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if (key.startsWith(MANAGER)) {
            //管理员专用
            if (dragonsTwilightWebConfig.getAdminQq().contains(sendQq)) {
                //二级指令，MANAGER后的内容，+1去空格
                String managerKey = key.substring(MANAGER.length() + 1);
                if (managerKey.startsWith(MANAGER_COMMAND)) {
                    //三级指令
                    String command = key.substring(MANAGER.length() + 1 + MANAGER_COMMAND.length() + 1);
                    //获取WebSocketApi
                    WebOptions webOptions = this.webOptionsService.findByKey("lodeWebSocketApiUri");
                    //封装webSocket信息发送格式
                    JSONObject paramJson = new JSONObject();
                    paramJson.put("command", command);
                    CommonUtils.webSocketSend(webOptions.getOptValue(), CommonUtils.lodeApiParam("executeCmd", paramJson).toJSONString());
                    return "执行指令 /" + command + " 成功！";
                } else {
                    return "小埋很听话哦！";
                }
            } else {
                return "你没有权限！";
            }
        } else if (key.startsWith(SIGN)) {
            //执行签到方法
            return this.sign(sendQq);
        } else if (key.startsWith(MAIL)) {
            //执行查询邮箱方法
            return this.mailBox(sendQq);
        } else {
            return "发送人：" + sendQq + ",发送内容：" + key;
        }
    }

    private String mailBox(String sendQq) {
        StringBuilder stringBuilder = new StringBuilder();
        //玩家名称
        String playerName = getPlayerName(sendQq);
        stringBuilder.append("【").append(playerName).append("的邮箱").append("】").append("\n");
        //查询玩家邮箱
        MailboxPlayer mailboxPlayer = new MailboxPlayer();
        mailboxPlayer.setRecipient(playerName);
        List<MailboxPlayer> mailboxPlayerList = this.mailboxPlayerService.findList(mailboxPlayer);
        if (CollectionUtils.isEmpty(mailboxPlayerList)) {
            //如果没有邮件
            stringBuilder.append("这里面空空如也~");
        } else {
            for (MailboxPlayer mail : mailboxPlayerList) {
                stringBuilder.append("===============").append("\n");
                stringBuilder.append("来自：").append(mail.getSender()).append("\n");
                stringBuilder.append("时间：").append(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(mail.getSendtime())).append("\n");
                stringBuilder.append("标题：").append(mail.getTopic()).append("\n");
                stringBuilder.append("附件：").append(("0".equals(mail.getFilename())) ? "无" : "有").append("\n");
            }
        }
        return stringBuilder.toString();
    }

    /**
     * #签到
     *
     * @param sendQq 发送人QQ
     * @return java.lang.String
     * @create 2020/6/17 17:15
     * @author ☪wl
     */
    public String sign(String sendQq) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //玩家名称
        String playerName = getPlayerName(sendQq);
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
            throw new DtWebException("@" + playerName + "，签到失败！\n你今天已经签到过了！！");
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
    }

    /**
     * 根据QQ号获取玩家ID
     *
     * @param qq QQ号
     * @return java.lang.String
     * @create 2020/6/17 17:51
     * @author ☪wl
     */
    public String getPlayerName(String qq) {
        //获取QQ对应角色名
        MinecraftQqPlayer minecraftQqPlayer = new MinecraftQqPlayer();
        minecraftQqPlayer.setQq(qq);
        MinecraftQqPlayer minecraftQqPlayerResult = this.minecraftQqPlayerService.findOne(minecraftQqPlayer);
        if (minecraftQqPlayerResult == null) {
            throw new DtWebException("@" + qq + "，请先绑定游戏ID！\n(使用\"#绑定 游戏ID\")");
        }
        return minecraftQqPlayerResult.getName();
    }

}
