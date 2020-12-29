package com.webserver.servlet;

import com.webserver.http.HttpRequest;
import com.webserver.http.HttpResponse;
import qrcode.QRCodeUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * @author 名字
 * @company
 * @create 2020-10-24 15:50
 */
public class CreateQRServlet {
    public void service(HttpRequest request, HttpResponse response){
        String content = request.getParameter("content");
        try {
            System.out.println("QR内容："+content);
//            FileOutputStream fos = new FileOutputStream("qr.jpg");
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            QRCodeUtil.encode(content,out);
            byte[] data = out.toByteArray();
            response.setData(data);
            response.putHeader("Content-Type","image/jpeg");
//            System.out.println("二维码已生成！");
//            response.setEntity(new File("./qr.jpg"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
