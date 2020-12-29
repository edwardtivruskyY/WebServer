本版本开始完成对业务的处理
以用户注册为例，实现对业务的支持。

这里分为三步实现：
1.理解用户在页面上输入的信息如何提交给服务端(了解html中表单的使用)
2.服务端如何解析浏览器提交上来的数据(重构HttpRequest的解析操作)
3.服务端如何处理业务请求(Servlet的理解)
4.数据如何保存到服务端(RAF操作，以前课堂案例实现过，后期学习数据库后直接保存在数据库)


实现：
1.在webapps/myweb/下新建一个用户注册页面: reg.html
  通过这个页面理解html中表单元素如何提交用户输入的数据

2.当提交表单后，请求的请求行里中间的抽象路径部分我们发现不再是纯粹得请求到了，它还包含传
递过来的参数。因此我们要对请求行的抽象路径部分(HttpRequest中uri属性保存的值)进行进一步
的解析。
在HttpRequest中再定义三个新属性:
private String requestURI                用于保存uri中"?"左侧的请求部分
private String queryString               用于保存uri中"?"右侧的参数部分
private Map<String, String> parameters   用于保存每一组参数 key:参数名，value:参数值

并添加一个新的方法parseUri，用于在解析请求行得到uri后对其进一步解析。