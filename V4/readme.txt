此版本开始完成相应客户端一个页面的操作
通过这个版本我们要掌握两个知识点：
1.HTML的基本语法；
2.HTTP相应的格式

HTML:超文本标记语言，是“网页”的语言。浏览器就是通过解释HTML语言内容将其转化为
图形化界面展示给用户看的。实际上我们上网看到的网页都是由服务端发送过来该页面的
HTML代码后由浏览器处理展示的。

实现：
1.在当前项目目录下新建一个目录webapps
    使用这个目录保存我们WebServer维护的多个网络应用。每个网络应用作为一个子
    目录保存在这里，并且子目录的名字就是这个网络应用的名字。
2.在webapps下新建第一个网络应用(新建一个子目录)并取名为myweb
3.在myweb目录下新建第一个页面:index.html
    1)发送一个响应，让浏览器上可以显示我们创的页面的信息。
     在ClientHandler实例化HttpRequest后(解析请求后),先通过socket获取输出流，
     按照HTTP协议要求的相应格式固定发送webapps/myweb/index.html页面给客户端，
     测试浏览器请求服务端后是否可以收到并正确显示该页面，从而理解清楚响应的格式。

