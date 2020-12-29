1.登录功能
1.1:登录页面，login.html
    该页面的form表单中action="./loginUser"
    需要username和password
1.2:登录成功提示页面，login_success.html
    居中显示一行字：
1.3:登录失败提示页面，login_fail.html
    居中显示

2.在com.webserver.servlet包下新建处理登录业务的类:LoginServlet，并完成service方法
  登录业务要求将用户输入的用户名和密码去user。dat文件比对，全部正确则登录成功。用户名不存
  在或密码错误都响应登录失败

3.在ClientHandler处理请求环节添加新的分支，判断请求如果是登录操作就进行登录处理。