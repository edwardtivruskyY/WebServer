使用thymeleaf实现动态页面
将所有注册用户信息展现在页面上。由于user.data文件内容随着用户的操作在不断变化，因此
我们不可能实现准备好一个页面将数据完整包含。因此，我们在需要时从user.data文件中读取
所有的用户信息并生成一个页面发送给用户。

thymeleaf模板引擎
thymeleaf可以将数据与模板页面结合，生成一个动态页面。

实现：
1.准备模板页面/webapps/myweb/userList.html
  在页面中定义好格式，并模拟写几条固定的数据。
2.在首页上添加一个超链接，请求路径为<a href="./userList">
3.在com.webserver.servlet下新建一个类：UserListServlet并定义service方法
4.在ClientHandler上添加一个新的分支，将该超链接的请求交给UserListServlet处理
5.新加一个包com.webserver.vo,并在里面准备一个类User，用它的每一个实例表示一个注册用户
6.在UserListServlet中我们首先从user.data文件中读取所有用户，并以User对象形式保存
  到一个List集合中。然后利用模板引擎将其与userList.html结合，然后交给response响应。
7.在userList.html中添加thymeleaf敏感的属性，使得其中导如何将数据展现在页面中
8.在HttpResponse中新建一种响应正文的方式：
  首先添加一个byte[]类型的属性data，然后对其添加get，set方法。
  之后修改sendContent方法，如果data不是null就将这组字节作为正文发送给客户端。
  这样一来第六步将生成好的html页面代码转换为字节后就可以设置到response中最后直
  接响应给客户端了。