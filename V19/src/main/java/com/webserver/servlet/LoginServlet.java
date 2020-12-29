package com.webserver.servlet;

import com.webserver.http.HttpRequest;
import com.webserver.http.HttpResponse;
import org.apache.log4j.Logger;
import sun.rmi.runtime.Log;

import javax.sound.midi.Soundbank;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * @author 名字
 * @company
 * @create 2020-10-23 11:05
 */
public class LoginServlet extends HttpServlet{
    private static Logger log = Logger.getLogger(LoginServlet.class);
    public void service(HttpRequest request, HttpResponse response){
        log.info("LoginServlet开始处理登录业务...");
        String userName = request.getParameter("username");
        String passWord = request.getParameter("password");
//        System.out.println("username:"+userName);
//        System.out.println("password:"+passWord);
        if(userName==null || passWord==null){
            log.info("空输入！！！！！！");
            response.setEntity(new File("./webapps/myweb/login_fail.html"));
            return;
        }
        try(RandomAccessFile raf = new RandomAccessFile("user.dat", "r")){
            byte[] data = new byte[32];
            byte[] data1 = new byte[32];
            long point = 0;
            String s;
            String s1;
            raf.seek(0);
            while(raf.read(data) != -1){
                raf.read(data1);
                s = new String(data,"UTF-8").trim();
                s1 = new String(data1,"UTF-8").trim();
                if(s.equals(userName) && s1.equals(passWord)) {
                    log.info("验证通过！！！！！！！！！！");
                    response.setEntity(new File("./webapps/myweb/login_success.html"));
                    return;
                }
                point += 100;
                raf.seek(point);
            }
            response.setEntity(new File("./webapps/myweb/login_fail.html"));
            log.info("LoginServlet处理登录业务完毕!");
        }catch (IOException e){
            log.error(e.getMessage(), e);
        }

    }
}
