上一个版本我们在ClientHandler处理请求的环节添加了一个Map，用于根据用户请求
的资源的后缀找到对应的Content-Type的值然后进行响应。
但是这个Map内容是固定的，并且没请求都会创建一遍这个Map(因为每个请求都会创建
一个ClientHandler处理请求)这是没有必要的(毕竟后面这个Map有1000多个元素)，
因此我们将这个Map进行重用，将它定义为全局唯一(静态的)。

实现：
1.在com.webserver.http包下新建一个类：HttpContext
  这个类用于定义所有有关HTTP协议定死的内容，并且会被我们程序引用到的信息。
  例如Content-Type对应的值
  扩展:实际上像CR、LF的编码定义也应当放在这里。

2.在HttpContent中定义一个静态属性:static Map mimeMapping
  并将其初始化
3.提供一个静态方法getMimeType，可以根据资源后缀获取到Content-Type对应的值
4.ClientHandler在获取到资源后缀后通过HttpContent获取类型设置响应头即可。


由于响应中只要有响应正文，就应当包含响应头Content-Type和Content-Length
因此我们可以将Content-Type和Content-Length两个属性的设置过程置于setEntity
方法中，这样将来我们只需要设置HttpResponse的entity，内部自动就会根据该文件
添加对应的两个响应头。
