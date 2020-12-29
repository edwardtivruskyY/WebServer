package com.webserver.core;

import java.io.IOException;
import java.io.InputStream;
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
            HttpRequest httpRequest = new HttpRequest(socket);
            //2.处理请求

            //3.响应客户端
        } catch (Exception e) {
            e.getStackTrace();
        }finally {
//            System.out.println("一个客户端下线了。");
//            try {
//                socket.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }
    }
}
