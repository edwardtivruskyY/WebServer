package com.webserver.http;

/**
 * V8中未使用
 */

import java.util.HashMap;
import java.util.Map;

public class RequestHandler {
    private Map<String, String> map = new HashMap<>();

    {
        map.put("html", "text/html");
        map.put("css", "text/css");
        map.put("js", "application/javascript");
        map.put("png", "image/png");
        map.put("gif", "image/gif");
        map.put("jpg", "image/jpeg");
    }
}
