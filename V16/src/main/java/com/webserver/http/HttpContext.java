package com.webserver.http;

/**
 * 所有有关HTTP协议规定的内容都定义在这里进行复用
 */

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.HashMap;
import java.util.List;
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
        Document document = null;
        try{
            SAXReader reader = new SAXReader();
            document = reader.read(new File("./config/web.xml"));
            Element root = document.getRootElement();
            List<Element> list = root.elements("mime-mapping");
            System.out.println("Content-Type数量:" + list.size());
            for(Element mapElem : list){
//                Element extElem = mapElem.element("extension");
//                Element typeElem = mapElem.element("mime-type");
//
//                String key = extElem.getTextTrim();
//                String value = typeElem.getTextTrim();
                String key = mapElem.elementText("extension");
                String value = mapElem.elementText("mime-type");
                mimeMap.put(key, value);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static String getMimeType(String ext) {
        return mimeMap.get(ext);
    }
}
