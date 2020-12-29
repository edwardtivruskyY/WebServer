package com.webserver.core;

import com.webserver.http.HttpRequest;

import java.net.Socket;

public class ClientHandler implements Runnable{
    private Socket socket;

    public ClientHandler(Socket s){
        this.socket = s;
    }

    public void run() {
        //解析请求
        HttpRequest request = new HttpRequest(socket);

        //处理请求

        //响应客户端
    }
}
