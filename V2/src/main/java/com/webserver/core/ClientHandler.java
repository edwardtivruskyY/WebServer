package com.webserver.core;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * 线程任务，负责与指定的客户端进行HTTP交互。HTTP协议要求与客户端的交互必须采取一问
 * 一答的模式。这里暂时先维持HTTP1.0的模式，一次TCP链接后一问一答编译客户端断开链接。
 */
public class ClientHandler implements Runnable{
    private Socket socket;

    public ClientHandler(Socket socket){
        this.socket = socket;
    }
    public void run(){
        try {
            InputStream in = socket.getInputStream();
            int d;
            char pre = 'a', cur = 'a';
            StringBuilder builder = new StringBuilder();
            while((d=in.read()) != -1){
                cur = (char)d;
                if(pre==13 && cur==10){
                    break;
                }
                pre = cur;
                builder.append(cur);
            }
            String line = builder.toString().trim();
            System.out.print(line);
//                InputStreamReader isr = new InputStreamReader(in,"UTF-8");
//                BufferedReader br = new BufferedReader(isr); //只能读取文字,不适用
//                String str;
//                while((str = br.readLine()) != null){
//                    System.out.println(str);
//                }
        } catch (IOException e) {
            e.getStackTrace();
        }finally {
            System.out.println("一个客户端下线了。");
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
