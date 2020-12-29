package com.webserver.core;

import com.webserver.http.*;

import java.io.*;
import java.net.Socket;

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
            //通过request获取浏览器地址栏内请求的资源的抽象路径
            if(request.getUri().contains("?")) {
                Servlet servlet = new Servlet(request, response);
            }else {
                String path = request.getRequestURI();
                System.out.println("抽象路径:" + path);
            /*
                测试固定发送webapps/myweb/index.html页面给客户端
             */
                File file = new File("./webapps" + path);
                String line = "资源响应中...";
                if (file.exists()) {
                    System.out.println("资源已存在，响应中...");
                    response.setEntity(file);
                } else {
                    System.out.println("资源不存在！");
                    File notFoundPage = new File("./webapps/root/404.html");
                    response.setStatusCode(404);
                    response.setStatusReason("NotFound");
                    response.setEntity(notFoundPage);
                }
            }
            response.putHeader("Server", "WebServer");
            //3.响应客户端
            response.flush();
        }catch (EmptyRequestException e){
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
