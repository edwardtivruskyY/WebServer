package com.webserver.core;

import com.webserver.http.HttpRequest;
import com.webserver.http.HttpResponse;

import java.io.*;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 线程任务，负责与指定的客户端进行HTTP交互。HTTP协议要求与客户端的交互必须采取一问
 * 一答的模式。这里暂时先维持HTTP1.0的模式，一次TCP链接后一问一答编译客户端断开链接。
 */
public class ClientHandler implements Runnable{
    private Socket socket;

    public ClientHandler(Socket socket){
        this.socket = socket;
    }
    public void run(){
        try {
            //1.解析请求
            HttpRequest request = new HttpRequest(socket);
            HttpResponse response = new HttpResponse(socket);

            //2.处理请求
            String path = request.getUri();
            System.out.println("抽象路径:" + path);
            //初始化Content-Type所有基本属性
            Map<String, String> map = new HashMap<>();
            map.put("html","text/html");
            map.put("css","text/css");
            map.put("js","application/javascript");
            map.put("png","image/png");
            map.put("gif","image/gif");
            map.put("jpg","image/jpeg");
            //测试固定发送webapps/myweb/index.html页面给客户端
            File file = new File("./webapps" + path);
            String line = "资源响应中...";
            if (!file.exists()) {
                line = "资源不存在！";
                file = new File("./webapps/root/404.html");
                response.setStatusCode(404);
                response.setStatusReason("NotFound");
            }
            String fileName = file.getName();
            String[] nameArr = fileName.split("\\.");
            String ext = nameArr[nameArr.length - 1];
            String type = map.get(ext);
            response.putHeader("Content-Type", type);
            response.putHeader("Content-Length", file.length() + "");
            response.setFile(file);
            System.out.println(line);
            response.putHeader("Server","WebServer");
                //3.响应客户端
            response.flush();

        } catch (Exception e) {
            e.getStackTrace();
        }finally {
            System.out.println("一个客户端下线了。");
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
