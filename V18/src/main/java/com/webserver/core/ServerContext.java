package com.webserver.core;

import com.webserver.servlet.HttpServlet;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServerContext {
    private static Map<String, HttpServlet> map = new HashMap<>();
    static{
        initServerMapping();
    }
    private static void initServerMapping(){
        try {
            SAXReader reader = new SAXReader();
            Document doc = reader.read("servlets");
            Element root = doc.getRootElement();
            List<Element> list = root.elements("servlet");

            for(Element e : list){
                String path = e.attributeValue("path");
                Class cls = Class.forName(e.attributeValue("className"));
                HttpServlet servlet = (HttpServlet) cls.getConstructor().newInstance();
                map.put(path, servlet);
            }
        }catch (DocumentException | ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static HttpServlet getMap(String path) {
        return map.get(path);
    }
}
