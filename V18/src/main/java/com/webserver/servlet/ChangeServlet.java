package com.webserver.servlet;

import com.webserver.http.HttpRequest;
import com.webserver.http.HttpResponse;

import javax.sound.midi.Soundbank;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;

/**
 * @author 名字
 * @company
 * @create 2020-10-23 16:16
 */
public class ChangeServlet extends HttpServlet{
    public void service(HttpRequest request, HttpResponse response){
        System.out.println("ChangeServlet开始处理改密...");
        String username = request.getParameter("username");
        String pre = request.getParameter("pre");
        String cur = request.getParameter("cur");
        File file = new File("./webapps/myweb/change_fail.html");
        if(username==null || pre==null || cur==null){
            response.setEntity(file);
            return;
        }
        try(RandomAccessFile raf = new RandomAccessFile("./user.data","rw")){
            byte[] data = new byte[32];
            long pos = 0;
            while(raf.read(data) != -1){
                raf.seek(pos);
                String s = new String(data,"utf-8").trim();
                if(s.equals(username)){
                    raf.read(data);
                    s = new String(data,"utf-8").trim();
                    if(s.equals(pre)){
                        file = new File("./webapps/myweb/change_success.html");
                        long point = raf.getFilePointer() - 32;
                        raf.seek(point);
                        data = cur.getBytes();
                        data = Arrays.copyOf(data,32);
                        raf.write(data);
                    }
                    break;
                }
            }
            response.setEntity(file);
            System.out.println("ChangeServlet处理改密完毕!");
        }catch (IOException e){
            e.getStackTrace();
        }
    }
}
