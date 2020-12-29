package com.webserver.core;

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
            HttpRequest httpRequest = new HttpRequest(socket);
            //2.处理请求
            String path = httpRequest.getUri();
            System.out.println("抽象路径:" + path);

            //3.响应客户端
            OutputStream out = socket.getOutputStream();
            //测试固定发送webapps/myweb/index.html页面给客户端
            File file = new File("./webapps" + path);
            /*
                HTTP/1.1 200 OK(CRLF)
                Content-Type: text/html(CRLF)
                Content-Length: 2546(CRLF)(CRLF)
                1011101010101010101......
             */
            String line = "HTTP/1.1 200 OK";
            out.write(line.getBytes("ISO8859-1"));
            out.write(13);
            out.write(10);

            line = "Content-Type: text/html";
            out.write(line.getBytes("ISO8859-1"));
            out.write(13);
            out.write(10);

            line = "Content-Length: " + file.length();
            out.write(line.getBytes("ISO8859-1"));
            out.write(13);
            out.write(10);
            out.write(13);
            out.write(10);

            FileInputStream fis = new FileInputStream(file);
            byte[] data = new byte[1024*10];
            int len;
            while ((len = fis.read(data)) != -1){
                out.write(data,0,len);
            }
//            System.out.println("响应完成!");
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
