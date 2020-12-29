package com.webserver.servlet;

import com.webserver.http.HttpRequest;
import com.webserver.http.HttpResponse;
import org.apache.log4j.Logger;

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
    private static Logger log = Logger.getLogger(ChangeServlet.class);
    public void service(HttpRequest request, HttpResponse response){
        log.info("ChangeServlet开始处理改密...");
        String username = request.getParameter("username");
        String pre = request.getParameter("pre");
        String cur = request.getParameter("cur");
        File file = new File("./webapps/myweb/change_fail.html");
        if(username==null || pre==null || cur==null){
            response.setEntity(file);
            return;
        }
        try(RandomAccessFile raf = new RandomAccessFile("./user.dat","rw")){
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
                pos += 100;
            }
            response.setEntity(file);
            log.info("ChangeServlet处理改密完毕!");
        }catch (IOException e){
            log.error(e.getMessage(), e);
        }
    }
}
