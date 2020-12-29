package com.webserver.core;

import com.webserver.http.EmptyRequestException;
import com.webserver.http.HttpRequest;
import com.webserver.http.HttpResponse;
import com.webserver.servlet.*;

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
            //通过request获取浏览器地址栏内请求的资源进一步解析后的抽象路径requestUri
            String path = request.getRequestURI();
            System.out.println("抽象路径:" + path);

            /*
                测试固定发送webapps/myweb/index.html页面给客户端
             */
            if("/myweb/regUser".equals(path)){ //注册页面为自己写的，标识符regUser
                RegServlet regServlet = new RegServlet();
                regServlet.service(request, response);
            }else if("/myweb/userLogin".equals(path)){
                /*
                    登录
                */
                LoginServlet sign = new LoginServlet();
                sign.service(request,response);
            }else if("/myweb/changePsw".equals(path)){
                /*
                    改密
                */
                ChangeServlet change = new ChangeServlet();
                change.service(request,response);
            }else if("/myweb/UserList".equals(path)){
                /*
                    用户列表
                */
                UserListServlet userList = new UserListServlet();
                userList.service(request,response);
            }else if("/myweb/QRCreate".equals(path)){
                /*
                    二维码生成
                */
                CreateQRServlet createQRServlet = new CreateQRServlet();
                createQRServlet.service(request,response);
            }else{
                File file = new File("./webapps" + path);
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
            //捕获空请求，不做空请求之后的操作，直接进入finally关闭socket
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
