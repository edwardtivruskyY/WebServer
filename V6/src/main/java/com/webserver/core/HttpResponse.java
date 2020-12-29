package com.webserver.core;
/**
 * V4中暂未使用
 */

import java.io.*;
import java.net.Socket;

public class HttpResponse {
    private Socket socket;
    private String path;
    private OutputStream out;
    private File file;
    /*
        状态行的属性
        协议版本 状态代码 状态描述
        HTTP/1.1 200 OK(CRLF)
     */

    /*
        响应头的属性
        内容格式Content-Type: text/html(CRLF)
        内容长度Content-Length: file.length()(CRLF)(CRLF)
     */

    private String line;

    public HttpResponse(Socket s, String p){
        this.socket = s;
        this.path = p;
        try {
            out = socket.getOutputStream();
            file = new File("./webapps" + path);

            responseStatue();
            responseHeaders();
            responseBody();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void responseStatue(){
        //响应的状态行
        try{
            line = "HTTP/1.1 200 OK";
            if(!file.exists()){
                line = "HTTP/1.1 404 NotFound";
            }
            out.write(line.getBytes("ISO8859-1"));
            out.write(13);
            out.write(10);
        }catch(IOException e){
            e.getStackTrace();
        }
    }
    private void responseHeaders(){
        //响应的响应头输出给客户端
        try{
            line = "Content-Type: text/html";
            out.write(line.getBytes("ISO8859-1"));
            out.write(13);
            out.write(10);

            line = "Content-Length: " + file.length();
            if(!file.exists()){
                file = new File("./webapps/root/404.html");
                line = "Content-Length: " + file.length();
            }
            out.write(line.getBytes("ISO8859-1"));
            out.write(13);
            out.write(10);
            out.write(13);
            out.write(10);
        }catch(IOException e){
            e.getStackTrace();
        }
    }
    private void responseBody() {
        //响应的响应正文输出给客户端
        try {
            FileInputStream fis = new FileInputStream(file);
            byte[] data = new byte[1024*10];
            int len;
            /*
                1011101010101010101......
             */
            if(file.exists()){
                line = "资源已存在，响应中...";
            }else{
                line = "资源不存在！";
                file = new File("./webapps/root/404.html");
                fis = new FileInputStream(file);
            }
            System.out.println(line);
            while ((len=fis.read(data)) != -1){
                out.write(data,0,len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
