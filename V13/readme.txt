3.服务端如何处理业务请求(Servlet的理解)
4.数据如何保存到服务端(RAF操作，以前课堂案例实现过，后期学习数据库后直接保存在数据库)
接上一个版本完成3，4两步。上个版本知道了如何让用户提交数据(页面上的form表单)，并且也做到
了在HttpRequest中解析用户提交的数据。

本版本完成对注册业务的处理工作。
要在ClientHandler处理请求的环节做一些改变，首先我们不能再使用HttpRequest的uri属性作为
请求路径使用了(如果是页面表单提交，uri中除了请求部分好会包含参数部分)。因此我们改为使用
requestURI这个属性。
其次，我们要在处理环节添加对专门的请求业务的地址做处理，例如:reg.html页面中form表单提交
用户的注册信息时，action="./regUser"。那么HttpRequest在解析该请求后，得到的requestURI
的值就是"/myweb/regUser"。
注：
因为在浏览器地址栏上请求注册页面的路径为：
http://localhist:8088/myweb/reg.html
因此，该页面上form中action="./regUser"实际的提交路径就是:
http://localhost:8088/myweb.regUser?参数部分...
HttpRequest解析完uri后得到的requestURI部分就是/myweb/regUser了。

所以ClientHandler在处理请求的环节添加一个新的分支，判断请求路径是否为"/myweb/regUser"，
如果是，就说明这次请求是reg.html页面form表单提交的用户信息并申请注册的请求，应开始进行注册
用户的业务操作