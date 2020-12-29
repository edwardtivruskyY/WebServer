package com.webserver.servlet;

import com.webserver.http.HttpRequest;
import com.webserver.http.HttpResponse;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;

/**
 * @author 名字
 * @company
 * @create 2020-10-23 16:16
 */
public class ChangeServlet {
    public void service(HttpRequest request, HttpResponse response){
        String username = request.getParameter("username");
        String pre = request.getParameter("pre");
        String cur = request.getParameter("cur");
        File file = new File("./webapps/myweb/change_fail.html");
        if(username==null && pre==null && cur==null){
            response.setEntity(file);
            return;
        }
        try(RandomAccessFile raf = new RandomAccessFile("user.data","rw")){
            byte[] data = new byte[32];
            long pos = 0;
            raf.seek(pos);
            while(raf.read(data) != -1){
                String s = new String(data,"UTF-8").trim();
                if(s.equals(username)){
                    raf.read(data);
                    s = new String(data,"utf-8").trim();
                    if(s.equals(pre)){
                        long point = raf.getFilePointer() - 32;
                        raf.seek(point);
                        byte[] psw = cur.getBytes();
                        psw = Arrays.copyOf(psw,32);
                        raf.write(psw);
                        file = new File("./webapps/myweb/change_success.html");
                        break;
                    }
                }
                pos += 100;
                raf.seek(pos);
            }
            response.setEntity(file);
        }catch (IOException e){
            e.getStackTrace();
        }
    }
}
