package com.webserver.core;

import com.webserver.servlet.HttpServlet;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 名字
 * @company
 * @create 2020-10-26 10:06
 */
public class ServerContext {
    private static Map<String, HttpServlet> servletMapping = new HashMap<>();

    static{
        //创建请求与业务映射表
        initServletMapping();
        System.out.println(servletMapping);
    }
    private static void initServletMapping(){
        Document document = null;
        SAXReader reader = new SAXReader();//创建扫描器
        try {
            //扫描的信息存储在文件中
            document = reader.read(new File("./config/servlets.xml"));
            Element root = document.getRootElement();
            List<Element> list = root.elements("servlet");
            for(Element sub : list){
                String path = sub.attributeValue("path");
                Class cls = Class.forName(sub.attributeValue("className"));
                HttpServlet servlet = (HttpServlet) cls.getConstructor().newInstance();
                servletMapping.put(path, servlet);
            }
        } catch (DocumentException | ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param path
     * @return
     */
    public static HttpServlet getServlet(String path){
        return servletMapping.get(path);
    }
}
