package com.webserver.servlet;

import com.webserver.http.HttpRequest;
import com.webserver.http.HttpResponse;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;

/**
 * 当前类用于处理注册业务
 */
public class RegServlet {
    public void service(HttpRequest request, HttpResponse response) {
        System.out.println("RegService开始处理注册...");
        //获取注册表单信息
        String username = request.getParameter("name");
        String password = request.getParameter("password");
        String nickname = request.getParameter("nickname");
        String ageStr = request.getParameter("age");
        /*
            获取注册页面提交的表单数据后再注册前要做必要的验证工作：
            以上四项不能是空，并且年龄必须是数字(1-2位)，
            如果不符合要求，则响应用户一个页面:reg_info_err.html
            这个页面居中显示一行字:注册信息有误，请重新注册。
         */
        if(username==null || password==null || nickname==null || ageStr==null || !ageStr.matches("[0-9]{1,2}")){
            System.out.println("注册失败，原因:信息有误！");
            File file = new File("./webapps/myweb/reg_info_err.html");
            response.setEntity(file);
        }else {
            int age = Integer.parseInt(ageStr);
            System.out.println("username:" + username);
            System.out.println("psw:" + password);
            System.out.println("nike:" + nickname);
            System.out.println("age:" + age);
            //保存用户信息
        /*
            每个用户信息固定占100字节，其中用户名，密码，昵称和年龄，其中仅年龄占4字节。
         */
            try (RandomAccessFile raf = new RandomAccessFile("user.data", "rw");) {
                byte[] data = new byte[32];
                long pos = 0;
                while (raf.read(data) != -1){
                    pos += 100;
                    String s = new String(data,"UTF-8");
                    if(s.trim().equals(username)){
                        response.setEntity(new File("./webapps/myweb/hav_user.html"));
                        return;
                    }
                    raf.seek(pos);
                }
                //保存用户名
                data = username.getBytes("UTF-8");
                data = Arrays.copyOf(data, 32);
                raf.write(data);
                //保存密码
                data = password.getBytes("UTF-8");
                data = Arrays.copyOf(data, 32);
                raf.write(data);
                //保存昵称
                data = nickname.getBytes("UTF-8");
                data = Arrays.copyOf(data, 32);
                raf.write(data);
                //保存年龄
                raf.writeInt(age);
                //设置response正文文件为注册完毕页面
                File file = new File("./webapps/myweb/reg_success.html");
                response.setEntity(file);
            } catch (IOException e) {
                e.getStackTrace();
            }
        }
        System.out.println("RegService注册处理完毕!");
    }
}
