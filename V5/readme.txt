上个版本测试完成了响应固定的页面，本版本基于上个版本完成根据用户在浏览器地址栏中输入的地址
去webapps下找到对应的页面文件，然后将其响应给浏览器。

通过测试发现，当我们在浏览器地址栏中输入地址时：
例如：
http://localhost:8088/myweb/index.html
或
http://localhost:8088/myblog/hello.html

观察服务端解析请求时，请求行中的抽象路径(也就是保存在属性uri中的值)会发生改变。并且体现的
是地址栏中localhost:8088之后的内容。
因此，我们在ClientHandler完成如下工作，在处理请求的环节，我们通过request获取uri的值，
然后在创建File对象时将上个版本固定寻找./webapps/myweb/index.html改为，在./webapps
下根据uri的值定位用户在浏览器地址栏上输入的希望请求的页面的路径，从而做到浏览器上输入什么地
址就去找到该页面并响应。
