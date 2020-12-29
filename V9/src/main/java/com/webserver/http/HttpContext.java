package com.webserver.http;

/**
 * 所有有关HTTP协议规定的内容都定义在这里进行复用
 */

import java.util.HashMap;
import java.util.Map;

public class HttpContext {
    private static Map<String, String> mimeMap = new HashMap<>();

    //初始化mimeMap
    static{
        //创建Content-Type字典
        initMimeMapping();
    }
    //初始化mimeMap的方法
    private static void initMimeMapping(){
        mimeMap.put("html","text/html");
        mimeMap.put("css","text/css");
        mimeMap.put("js","application/javascript");
        mimeMap.put("png","image/png");
        mimeMap.put("gif","image/gif");
        mimeMap.put("jpg","image/jpeg");
    }
    public static String getMimeType(String ext) {
        return mimeMap.get(ext);
    }
}
