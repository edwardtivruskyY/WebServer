package com.webserver.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * 请求对象，该类的每一个实例用于表示客户端发送过来的一个HTTP请求内容。
 * 每个请求由三部分构成:请求行，消息头，消息正文
 */
public class HttpRequest {
    //请求行相关的属性
    private String method;//请求方式
    private String uri;//抽象路径
    private String protocol;//协议版本

    //消息头相关的属性
    private Map<String,String> headers = new HashMap<>();

    //消息正文相关的属性

    private Socket socket;

    public HttpRequest(Socket socket){
        this.socket = socket;
        /*
            实例化过程就是解析过程，按照请求的格式顺序进行解析
         */
        System.out.println("HttpRequest:开始解析请求...");
        parseRequestLine();//解析请求行
        parseHeaders();//解析消息头
        parseContent();//解析消息正文
        System.out.println("HttpRequest:请求解析完毕!");
    }

    private void parseRequestLine(){
        System.out.println("HttpRequest:开始解析请求行...");
        /*
            通过socket获取输入流，读取客户端发送过来的第一行字符串，这一行应当时请求行
            内容。然后按照空格拆分为三部分，分别赋值给method,uri,protocol三个属性
         */
        try {
            String line = readLine();
            System.out.println("请求行:"+line);
            String[] data = line.split("\\s");
            method = data[0];
            uri = data[1];//这里可能出现数组下标越界，浏览器发送空请求导致，后期解决
            protocol = data[2];
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("method:"+method);
        System.out.println("uri:"+uri);
        System.out.println("protocol:"+protocol);
        System.out.println("HttpRequest:请求行解析完毕!");
    }
    private void parseHeaders(){
        System.out.println("HttpRequest:开始解析消息头...");
        try {
            while(true) {
                String line = readLine();
                if(line.isEmpty()){//如果readLine返回的是空字符串说明单独读取到了CRLF
                    break;
                }
                System.out.println("消息头:" + line);
                /*
                    将每个消息头按照": "(冒号空格)拆分，将消息头名字做为key，消息头
                    的值作为value保存到headers中即可
                 */
                String[] data = line.split(":\\s");
                headers.put(data[0],data[1]);
            }

            System.out.println("headers:"+headers);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("HttpRequest:消息头解析完毕!");
    }
    private void parseContent(){
        System.out.println("HttpRequest:开始解析消息正文...");

        System.out.println("HttpRequest:消息正文解析完毕!");
    }

    private String readLine() throws IOException {
        InputStream in = socket.getInputStream();
        int d;
        StringBuilder builder = new StringBuilder();
        //pre为上次读取到的字符，cur为本次读取到的字符
        char pre='a',cur='a';
        while((d = in.read())!=-1){
            cur = (char)d;
            if(pre==13&&cur==10){//如果连续读取到回车符和换行符就停止读取
                break;
            }
            builder.append(cur);
            pre = cur;
        }
        return builder.toString().trim();
    }

}







