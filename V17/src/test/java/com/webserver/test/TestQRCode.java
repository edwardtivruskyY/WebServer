package com.webserver.test;

import qrcode.QRCodeUtil;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Scanner;

/**
 * @author 名字
 * @company
 * @create 2020-10-24 15:24
 */
public class TestQRCode {
    public static void main(String[] args) throws FileNotFoundException {
        String line = "http://doc.canglaoshi.org";
        try {
            QRCodeUtil.encode(line,new FileOutputStream("qr.jpg"));
            System.out.println("二维码已生成！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
