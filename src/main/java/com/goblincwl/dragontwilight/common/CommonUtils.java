package com.goblincwl.dragontwilight.common;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class CommonUtils {

    TomcatServletWebServerFactory tomcatServletWebServerFactory;

    public CommonUtils(TomcatServletWebServerFactory tomcatServletWebServerFactory) {
        this.tomcatServletWebServerFactory = tomcatServletWebServerFactory;
    }

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

    public static String getServerUrl(HttpServletRequest request) {
        return "https://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
}
