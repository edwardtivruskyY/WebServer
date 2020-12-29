package com.webserver.servlet;

import com.webserver.http.HttpRequest;
import com.webserver.http.HttpResponse;

/**
 * @author 名字
 * @company
 * @create 2020-10-26 9:22
 */
public abstract class HttpServlet {
    public abstract void service(HttpRequest request, HttpResponse response);
}
