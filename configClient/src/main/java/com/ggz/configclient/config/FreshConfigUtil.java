package com.ggz.configclient.config;

import cn.hutool.http.HttpUtil;

import java.util.HashMap;

/**
 * @author ggz on 2025/4/3
 */
public class FreshConfigUtil{
    public static void main(String[] args) {
        HashMap<String,String> headers =new HashMap<>();
        headers.put("Content-Type", "application/json; charset=utf-8");
        System.out.println("因为要去git获取，还要刷新config-server, 会比较卡，所以一般会要好几秒才能完成，请耐心等待");

        /**
         * 调用config-client服务的actuator/bus-refresh请求，即可从config-server把新的配置刷新到config-client中
         */
        String result = HttpUtil.createPost("http://localhost:8031/actuator/bus-refresh").addHeaders(headers).execute().body();
        System.out.println("result:"+result);
        System.out.println("refresh 完成");
    }
}