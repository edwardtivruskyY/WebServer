package com.webserver.servlet;

import com.webserver.http.HttpRequest;
import com.webserver.http.HttpResponse;
import com.webserver.vo.User;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.FileTemplateResolver;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


/**
 * @author 名字
 * @company
 * @create 2020-10-24 9:19
 * 将user.data文件的所有用户记录生成一个页面(userList.html作为模板)并响应给客户端
 */
public class UserListServlet {
    public void service(HttpRequest request, HttpResponse response){
        //1.将user.data中的用户记录读取出来
        List<User> list = new ArrayList<>();
        try(RandomAccessFile raf = new RandomAccessFile("user.data","r")){
            byte[] data = new byte[32];
            long pos = 0;
            raf.seek(pos);
            while(raf.read(data) != -1){
                String username = new String(data,"utf-8").trim();
                raf.read(data);
                String password = new String(data,"utf-8").trim();
                raf.read(data);
                String nickname = new String(data,"utf-8").trim();
                int age = raf.readInt();
                User user = new User(username, password, nickname, age);
                list.add(user);
            }
        }catch(IOException e){
            e.getStackTrace();
        }
        for(User u : list){
            System.out.println(u.toString());
        }
        //2.将数据绑定到userList.html(用thymeleaf完成)
        /*
            Context类有点类似一个Map，用来保存数据。thymeleaf最终是将Context中保存
            得数据与模板页面结合生成动态页面的。
            因此所有要在页面上显示的数据都要放到Context中。
         */
        Context  context = new Context();
        /*
            向Context保存要在页面上显示的数据，调用方法：
            setVariable(String name, Object value)
            页面上将来要获取这个数据，只需要指定name就可以得到对应的value，类似Map中
            根据key来获取value。
         */
        context.setVariable("用户记录", list);
        FileTemplateResolver tr = new FileTemplateResolver();
        tr.setTemplateMode("html");
        tr.setCharacterEncoding("utf-8");
        //创建模板引擎
        TemplateEngine te = new TemplateEngine();
        te.setTemplateResolver(tr);//设置模板解释器，让thymeleaf了解模板相关信息

        String html = te.process("./webapps/myweb/userList.html", context);
        System.out.println(html);
        //3.将生成的页面设置到response中
        try {
            response.setData(html.getBytes("utf-8"));
            response.putHeader("Content-Type","text/html");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}









