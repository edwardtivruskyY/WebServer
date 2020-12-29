package com.webserver.core;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HttpRequest {
    private Socket socket;
    //请求行相关属性
    private String method;//请求方式
    private String uri;//抽象路径
    private String protocol;//协议版本
    //消息头相关属性
    private Map<String,String> headers = new HashMap<>();
    //消息正文相关属性

    public HttpRequest(Socket socket){
        this.socket = socket;
        /*
            实例化过程就是解析过程，按照请求的格式顺序进行解析。
         */
        System.out.println("HttpRequest开始解析请求...");
        parseRequestLine();
        parseHeaders();
        parseContent();
        System.out.println("HttpRequest解析请求完成!");
    }
    private void parseRequestLine(){
        System.out.println("HttpRequest开始解析请求行...");
        /*
            通过socket获取输入流，读取客户端发送过来的第一行字符串，这一行应当是请求行
            内容。然后按照空格拆分成三部分，分别赋值给method、uri、protocol三个属性
         */
        try {
            String line = readLine();
            System.out.println("请求行:" + line);
            String[] strArray = line.split("\\s");//正则切分
            method = strArray[0];
            uri = strArray[1];//这里可能出现数组下标越界，浏览器发送空请求导致，后期解决
            protocol = strArray[2];
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("method:"+method);
        System.out.println("uir:"+uri);
        System.out.println("protocol"+protocol);
        System.out.println("HttpRequest解析请求行完毕!");
    }
    private void parseHeaders(){
        System.out.println("HttpRequest开始解析消息头...");
        try {
            while(true){
                String line = readLine();
                if(line.isEmpty()){
                    break;
                }
//                System.out.println("消息头"+line); //重复输出
                /*
                    将每个消息头按照": "(冒号空格)拆分，将消息头名字作为key，消息头
                    的内容作为value，保存到headers里
                 */
                String[] head = line.split(":\\s");
                headers.put(head[0],head[1]);
            }
            Set<Map.Entry<String,String>> entrySet = headers.entrySet();
            for(Map.Entry<String,String> head : entrySet){
                String key = head.getKey();
                String value = head.getValue();
                System.out.println("消息头：" + key + ": " + value);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("HttpRequest解析消息头完毕!");
    }
    private void parseContent(){
        System.out.println("HttpRequest开始解析消息正文...");

        System.out.println("HttpRequest解析消息正文完毕!");
    }

    private String readLine() throws IOException {
        InputStream in = socket.getInputStream();
        StringBuilder builder = new StringBuilder();
        int d;
        //pre上次读到的字符，cur为本次
        char pre = 'a', cur = 'a';
        while ((d = in.read()) != -1){
            cur = (char)d;
            if(pre == 13 && cur == 10){
                break;
            }
            builder.append(cur);
            pre = cur;
        }
        return builder.toString().trim();
    }

    public String getMethod() {
        return method;
    }

    public String getUri() {
        return uri;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getHeaders(String name) {
        return headers.get(name);
    }
}
