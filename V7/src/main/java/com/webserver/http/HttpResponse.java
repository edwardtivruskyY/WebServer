package com.webserver.http;
/**
 * V4、5、6中暂未使用
 * 响应对象
 * 该类的每一个实例用于表示一个HTTP响应内容
 * 每个响应有三部分构成：状态行，响应头，响应正文
 */

import java.io.*;
import java.net.Socket;

public class HttpResponse {
    private Socket socket;
    private String path;
    private OutputStream out;
    /*
        状态行的属性
        协议版本 状态代码 状态描述
        HTTP/1.1 200 OK(CRLF)
     */
    private int statusCode = 200; //状态代码默认值为200
    private String statusReason = "OK"; //对应状态描述默认值为OK

    /*
        响应头的属性
        内容格式Content-Type: text/html(CRLF)
        内容长度Content-Length: file.length()(CRLF)(CRLF)
     */
    private String responseHeader;
    private File entity;
    private String line;

//    public HttpResponse(Socket s, String p){
    public HttpResponse(Socket s){
        this.socket = s;
        try {
            out = socket.getOutputStream();
//            entity = new File("./webapps" + path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将当前响应对象内容以标准HTTP响应格式发送给客户端
     */
    public void flush() throws IOException {
        sendStatusLine();
        sendHeaders();
        sendContent();
    }
    private void sendStatusLine() throws IOException {
        //响应的状态行
        line = "HTTP/1.1 " + statusCode +" " + statusReason;
        if(!entity.exists()){
            line = "HTTP/1.1 404 NotFound";
        }
        out.write(line.getBytes("ISO8859-1"));
        out.write(13);out.write(10);
    }
    private void sendHeaders() throws IOException {
        //响应的响应头输出给客户端
        line = "Content-Type: text/html";
        out.write(line.getBytes("ISO8859-1"));
        out.write(13);
        out.write(10);

        line = "Content-Length: " + entity.length();
        out.write(line.getBytes("ISO8859-1"));
        out.write(13);
        out.write(10);
        out.write(13);
        out.write(10);
    }
    private void sendContent() throws IOException {
        //响应的响应正文输出给客户端
        FileInputStream fis = new FileInputStream(entity);
        byte[] data = new byte[1024*10];
        int len;
        /*
            1011101010101010101......
        */
        while ((len=fis.read(data)) != -1){
            out.write(data,0,len);
        }
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusReason() {
        return statusReason;
    }

    public void setStatusReason(String statusReason) {
        this.statusReason = statusReason;
    }

    public File getEntity() {
        return entity;
    }

    public void setFile(File file) {
        this.entity = file;
    }
}
