package cn.goblincwl.dragontwilight.controller;

import cn.goblincwl.dragontwilight.common.exception.DtWebException;
import cn.goblincwl.dragontwilight.common.result.ResultGenerator;
import cn.goblincwl.dragontwilight.common.utils.CommonUtils;
import cn.goblincwl.dragontwilight.entity.primary.MinecraftQqPlayer;
import cn.goblincwl.dragontwilight.service.MinecraftQqPlayerService;
import cn.goblincwl.dragontwilight.yggdrasil.service.YggUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
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
    private final YggUserService yggUserService;

    public OuterController(MinecraftQqPlayerService minecraftQqPlayerService, YggUserService yggUserService) {
        this.minecraftQqPlayerService = minecraftQqPlayerService;
        this.yggUserService = yggUserService;
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
    @ResponseBody
    @GetMapping(value = "/getImage")
    public void GetImage(@RequestParam String userName, HttpServletResponse response) throws IOException {
        InputStream inStream = null;
        OutputStream os = null;

        URL url;
        HttpURLConnection conn;
        try {
            try {
                //通过用户名获取游戏ID
                String playerName;
                if (userName.contains("@")) {
                    playerName = this.yggUserService.getUserByUsername(userName).getPlayerName();
                } else {
                    playerName = userName;
                }
                url = new URL("https://littleskin.cn/skin/" + playerName + ".png");
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(5 * 1000);
                //通过输入流获取图片数据
                inStream = conn.getInputStream();
                if (inStream == null) {
                    throw new DtWebException("无匹配角色");
                }
            } catch (Exception e) {
                //请求littleSkin失败，返回默认史蒂夫皮肤
                String root = ResourceUtils.getFile("classpath:static").getPath();
                response.setContentType("image/png");
                BufferedImage bufferedImage = ImageIO.read(new FileInputStream(new File(root + "/images/steve.png")));
                if (bufferedImage != null) {
                    os = response.getOutputStream();
                    ImageIO.write(bufferedImage, "png", os);
                }
                return;
            }
            //读取输入流数据
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[2048];
            int len;
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
            if (inStream != null) {
                inStream.close();
            }
            if (os != null) {
                os.close();
            }
        }
    }


}
