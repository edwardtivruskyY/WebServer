package com.webserver.core;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * WebServer模仿Tomcat实现的一个轻量级容器
 * 可以维护部署多个不同的网络应用(webapp)，并且可以和客户端(浏览器)以TCP
 * 协议进行链接并基于HTTP协议进行交互，使得浏览器可以访问当前web容器中的各
 * webapp功能。
 *
 * webapp:网络应用，每个网络应用相当于一个"网站"，由自己的网页、素材和处理
 * 业务的java代码组成。
 *
 * 通过这个项目，我们可以理解tomcat底层实现原理，清楚客户端与服务端的交互过
 * 程以及HTTP协议的细节。
 */
public class WebServer {
    private ServerSocket server;
    private ExecutorService threadPool;

    public WebServer(){
        try {
            System.out.println("服务端启动中...");
            /*
                如果实例化时抛出异常:java.net.BindException:address already in use
                说明申请的端口已经被别的程序占用。
                通常情况下是连续启动服务端导致的。
             */
            server = new ServerSocket(8088);
            threadPool = Executors.newFixedThreadPool(50);
            System.out.println("服务端启动完毕!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void start() {
        try {
            while(true){
                System.out.println("等待客户端连接中...");
                Socket socket = server.accept();
                ClientHandler clientHandler = new ClientHandler(socket);
                System.out.println("客户端已连接!");
                threadPool.execute(clientHandler);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        WebServer webServer = new WebServer();
        webServer.start();
    }
}
