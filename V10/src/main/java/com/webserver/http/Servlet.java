package com.webserver.http;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;

public class Servlet {
    public Servlet(HttpRequest request, HttpResponse response){
        //获取传递的参数
        request.getQueryRequest();
        //保存传递的参数
        String username = request.getParameter("name");
        String password = request.getParameter("password");
        String nickname = request.getParameter("nickname");
        int age = Integer.parseInt(request.getParameter("age"));
        try(RandomAccessFile raf = new RandomAccessFile("register.data","rw");){
            byte[] data = username.getBytes();
            data = Arrays.copyOf(data,32);
            raf.write(data);
            data = password.getBytes();
            data = Arrays.copyOf(data,32);
            raf.write(data);
            data = nickname.getBytes();
            data = Arrays.copyOf(data,32);
            raf.write(data);
            raf.writeInt(age);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //响应注册成功的页面
    }
}
