package com.webserver.servlet;

import com.webserver.http.HttpRequest;
import com.webserver.http.HttpResponse;
import org.apache.log4j.Logger;
import qrcode.QRCodeUtil;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;

/**
 * @author 名字
 * @company
 * @create 2020-10-24 15:50
 */
public class CreateQRServlet extends HttpServlet{
    private static Logger log = Logger.getLogger(CreateQRServlet.class);
    public void service(HttpRequest request, HttpResponse response){
        log.info("CreateQRServlet开始处理业务...");
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
            log.error(e.getMessage(), e);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        log.info("CreateQRServlet处理业务完毕！");
    }
}
