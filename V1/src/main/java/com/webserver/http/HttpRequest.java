package com.webserver.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private Socket socket;
    //请求行属性
    private String method;
    private String uri;
    private String protocol;
    //消息头属性
    private Map<String, String> headers = new HashMap<>();
    //消息正文属性

    public HttpRequest(Socket socket) {
        this.socket = socket;
        System.out.println("HttpRequest开始解析请求...");
        parseLine();
        parseHeaders();
        parseContent();
    }
    private void parseLine(){
        try {
            InputStream in = socket.getInputStream();
            StringBuilder builder = new StringBuilder();
            int d;
            char pre = 'a', cur = 'a';
            while(pre != 13 || cur != 10){
                if((d=in.read()) != -1){
                    cur = (char)d;
                    builder.append(cur);
                    pre = cur;
                }
            }
            System.out.println("消息头:" + builder.toString());
//          InputStreamReader isr = new InputStreamReader(in,"UTF-8");
//          BufferedReader br = new BufferedReader(isr); //只能读取文字
        } catch (IOException e) {
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
    private void parseHeaders(){

    }
    private void parseContent(){

    }
}
