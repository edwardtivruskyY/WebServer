package com.webserver.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HttpRequest {
    private Socket socket;
    //请求行相关属性
    private String method;//请求方式
    private String uri;//抽象路径
    private String protocol;//协议版本

    private String requestURI;
    private String queryString;
    private Map<String, String> parameters = new HashMap<>();
    //消息头相关属性
    private Map<String,String> headers = new HashMap<>();
    //消息正文相关属性

    public HttpRequest(Socket socket) throws EmptyRequestException {
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
    private void parseRequestLine() throws EmptyRequestException {
        System.out.println("HttpRequest开始解析请求行...");
        /*
            通过socket获取输入流，读取客户端发送过来的第一行字符串，这一行应当是请求行
            内容。然后按照空格拆分成三部分，分别赋值给method、uri、protocol三个属性
         */
        try {
            String line = readLine();
            if(line.isEmpty()){
                //为了让ClientHandler忽略空请求异常，需要抛出该异常
                throw new EmptyRequestException();
            }
            System.out.println("请求行:" + line);
            String[] strArray = line.split("\\s");//正则切分
            method = strArray[0];
            uri = strArray[1];//无if()这里可能出现数组下标越界，浏览器发送空请求导致，后期解决
            protocol = strArray[2];
            parseUri();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("method:"+method);
        System.out.println("uir:"+uri);
        System.out.println("requestURI:"+requestURI);
        System.out.println("queryString:"+queryString);
        System.out.println("parameters:"+parameters);
        System.out.println("protocol:"+protocol);
        System.out.println("HttpRequest解析请求行完毕!");
    }
    //进一步解析uri
    private void parseUri(){
        System.out.println("HttpRequest进一步解析uri中...");
        /*
            进一步拆分uri。分两种情况：
            1.带参数
              即含"?"，左为requestUri，右为queryString，
              将
              左当key，右当value，保存在parameters中。
            2.不带参数
              即不含"?"，直接赋值给requestUri。
         */
        if(uri.contains("?")) {
            String[] strArr = uri.split("\\?");
            requestURI = strArr[0];
            //防止空提交的情况,?后无信息
            if(strArr.length > 1){
                queryString = strArr[1];
                String[] data = queryString.split("&");
                for(String s : data){
                    String[] arr = s.split("=");
                    //防止value无值情况
                    if(arr.length > 1){
                        parameters.put(arr[0], arr[1]);
                    }else{
                        parameters.put(arr[0],null);
                    }
                }
            }
        }else{
            requestURI = uri;
        }
        System.out.println("HttpRequest进一步解析完毕!");
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

    public String getRequestURI() {
        return requestURI;
    }

    public String getQueryString() {
        return queryString;
    }

    public String getParameter(String name) {
        return parameters.get(name);
    }

    public String getProtocol() {
        return protocol;
    }

    public String getHeaders(String name) {
        return headers.get(name);
    }
}
