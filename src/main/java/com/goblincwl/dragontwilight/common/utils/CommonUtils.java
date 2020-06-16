package com.goblincwl.dragontwilight.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.enums.ReadyState;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.*;
import java.util.Calendar;
import java.util.Date;

public class CommonUtils {

    private static Logger logger = LoggerFactory.getLogger(CommonUtils.class);
    public static WebSocketClient client;

    TomcatServletWebServerFactory tomcatServletWebServerFactory;

    public CommonUtils(TomcatServletWebServerFactory tomcatServletWebServerFactory) {
        this.tomcatServletWebServerFactory = tomcatServletWebServerFactory;
    }

    /**
     * 转换UUID为有-的
     *
     * @param uuId
     * @return java.lang.String
     * @create 2020/6/16 11:04
     * @author ☪wl
     * @document ShowDoc:
     */
    public static String convertUUId(String uuId) {
        String sub1 = uuId.substring(0, 8);
        String sub2 = uuId.substring(8, 12);
        String sub3 = uuId.substring(12, 16);
        String sub4 = uuId.substring(16, 20);
        String sub5 = uuId.substring(20);
        return sub1 + "-" + sub2 + "-" + sub3 + "-" + sub4 + "-" + sub5;
    }

    /**
     * 发送GET请求
     *
     * @param url    请求URL
     * @param params 请求参数
     * @return com.alibaba.fastjson.JSONObject
     * @create 2020/5/21 20:05
     * @author ☪wl
     */
    public static JSONObject httpGet(String url, String params) {
        JSONObject resultJson = null;
        // 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        // 创建Get请求
        HttpGet httpGet = new HttpGet(url + "?" + params);
        // 响应模型
        CloseableHttpResponse response = null;
        try {
            // 配置信息
            RequestConfig requestConfig = RequestConfig.custom()
                    // 设置连接超时时间(单位毫秒)
                    .setConnectTimeout(5000)
                    // 设置请求超时时间(单位毫秒)
                    .setConnectionRequestTimeout(5000)
                    // socket读写超时时间(单位毫秒)
                    .setSocketTimeout(5000)
                    // 设置是否允许重定向(默认为true)
                    .setRedirectsEnabled(true).build();

            // 将上面的配置信息 运用到这个Get请求里
            httpGet.setConfig(requestConfig);

            // 由客户端执行(发送)Get请求
            response = httpClient.execute(httpGet);

            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                resultJson = JSONObject.parseObject(EntityUtils.toString(responseEntity));
            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultJson;
    }


    /**
     * 发送POST请求
     *
     * @param url    请求URL
     * @param params 请求参数
     * @return void
     * @create 2020/5/27 0:39
     * @author ☪wl
     * @document
     */
    public JSONObject httpPost(String url, String params) {
        JSONObject resultJson = null;
        // 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        // 创建Post请求
        HttpPost httpPost = new HttpPost(url + "?" + params);
        // 设置ContentType(注:如果只是传普通参数的话,ContentType不一定非要用application/json)
        httpPost.setHeader("Content-Type", "application/json;charset=utf8");

        // 响应模型
        CloseableHttpResponse response = null;
        try {
            // 由客户端执行(发送)Post请求
            response = httpClient.execute(httpPost);
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();

            if (responseEntity != null) {
                resultJson = JSONObject.parseObject(EntityUtils.toString(responseEntity));
            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultJson;
    }

    /**
     * 发送POST请求 请求体参数
     *
     * @param url    请求URL
     * @param object 请求参数
     * @return com.alibaba.fastjson.JSONObject
     * @create 2020/6/16 11:05
     * @author ☪wl
     * @document ShowDoc:
     */
    public JSONObject httpPost(String url, Object object) {
        JSONObject resultJson = null;
        // 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        // 创建Post请求
        HttpPost httpPost = new HttpPost(url);
        // 我这里利用阿里的fastjson，将Object转换为json字符串;
        // (需要导入com.alibaba.fastjson.JSON包)
        String jsonString = JSON.toJSONString(object);
        StringEntity entity = new StringEntity(jsonString, "UTF-8");
        // post请求是将参数放在请求体里面传过去的;这里将entity放入post请求体中
        httpPost.setEntity(entity);
        httpPost.setHeader("Content-Type", "application/json;charset=utf8");
        // 响应模型
        CloseableHttpResponse response = null;
        try {
            // 由客户端执行(发送)Post请求
            response = httpClient.execute(httpPost);
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();

            if (responseEntity != null) {
                resultJson = JSONObject.parseObject(EntityUtils.toString(responseEntity));
            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultJson;
    }

    /**
     * 获取服务器地址
     *
     * @return java.lang.String
     * @create 2020/5/27 0:35
     * @author ☪wl
     */
    public static String getServerUrl(HttpServletRequest request) {
        return "https://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

    /**
     * 发送websocket消息
     *
     * @param uri     请求地址
     * @param message 发送消息
     * @create 2020/6/8 18:24
     * @author ☪wl
     */
    public static void webSocketSend(String uri, String message) {
        try {
            client = new WebSocketClient(new URI(uri), new Draft_6455()) {
                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                    logger.info("握手成功");
                }

                @Override
                public void onMessage(String msg) {
                    logger.info("收到消息==========" + msg);
                    if (msg.equals("over")) {
                        client.close();
                    }
                }

                @Override
                public void onClose(int i, String s, boolean b) {
                    logger.info("链接已关闭");
                }

                @Override
                public void onError(Exception e) {
                    e.printStackTrace();
                    logger.info("发生错误已关闭");
                }
            };
            client.connect();
            //logger.info(client.getDraft());
            while (!client.getReadyState().equals(ReadyState.OPEN)) {
                logger.info("正在连接...");
            }
            //连接成功,发送信息
            logger.info("发送消息==========" + message);
            client.send(message);
            client.close();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    /**
     * 计算日期相差天数
     *
     * @param startDate 开始日期
     * @param endDate   截止日期
     * @return java.lang.Long
     * @create 2020/6/14 19:48
     * @author ☪wl
     */
    public static Long calDateBetweenDays(Date startDate, Date endDate) {
        // 获取相差的天数
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        long timeInMillis1 = calendar.getTimeInMillis();
        calendar.setTime(endDate);
        long timeInMillis2 = calendar.getTimeInMillis();
        return (timeInMillis2 - timeInMillis1) / (1000L * 3600L * 24L);
    }

    /**
     * 封装lodeApi WebSocket消息格式
     *
     * @param action 执行操作["executeCmd":"执行指令"]
     * @param param  json参数
     * @return com.alibaba.fastjson.JSONObject
     * @create 2020/6/16 11:07
     * @author ☪wl
     * @document ShowDoc:
     */
    public static JSONObject lodeApiParam(String action, JSONObject param) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("action", action);
        jsonObject.put("params", param);
        return jsonObject;
    }
}