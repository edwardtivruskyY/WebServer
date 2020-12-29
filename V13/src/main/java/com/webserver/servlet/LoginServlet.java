package com.webserver.servlet;

import com.webserver.http.HttpRequest;
import com.webserver.http.HttpResponse;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * @author 名字
 * @company
 * @create 2020-10-23 11:05
 */
public class LoginServlet {
    public void service(HttpRequest request, HttpResponse response){
        String userName = request.getParameter("username");
        String passWord = request.getParameter("password");
        if(userName==null || passWord==null){
            response.setEntity(new File("./webapps/myweb/sign_failed.html"));
            return;
        }
        try(RandomAccessFile raf = new RandomAccessFile("", "r");){
            byte[] data = new byte[32];
            long point = 0;
            String s;
            while(raf.read(data) != -1){
                s = new String(data,"UTF-8").trim();
                if(s.equals(userName)) {
                    raf.read(data);
                    s = new String(data,"utf-8").trim();
                    if(s.equals(passWord)){
                        response.setEntity(new File("./webapps/myweb/sign_success.html"));
                    }else{
                        response.setEntity(new File("./webapps/myweb/sign_failed.html"));
                    }
                    return;
                }
                point += 100;
                raf.seek(point);
            }
        }catch (IOException e){
            e.getStackTrace();
        }

    }
}
