package com.webserver.core;
/**
 * V4中暂未使用
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class HttpResponse {
    private Socket socket;

    public HttpResponse(Socket s){
        this.socket = s;
        responseHeaders();
        responseBody();
    }
    private void responseHeaders(){

    }
    private void responseBody() {
        try {
            /*
                HTTP/1.1 200 OK(CRLF)
                Content-Type: text/html(CRLF)
                Content-Length: 2546(CRLF)(CRLF)
                1011101010101010101......
             */
            OutputStream out = socket.getOutputStream();
            File file = new File("./webapps/myweb/text/html");
            FileInputStream fis = new FileInputStream(file);
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

            byte[] data = new byte[1024*10];
            int len;
            while ((len=fis.read(data)) != -1){
                out.write(data,0,len);
            }
            System.out.println("响应完成!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
