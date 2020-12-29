上一版本中我们定义了一个类HttpContext，并在其中定义了静态属性mimeMapping
用于重用Content-Type的值，但是这个Map的初始化中我们只是临时存放了6种不同的
类型。实际上资源类型有1000多种，并且TOMCAT提供的配置文件中已经整理完毕了。
因此我们直接采用它来初始化这个Map。

实现：
1.在项目目录下新建目录:config，并将TOMCAT提供的配置文件web.xml拷贝进来。
2.在HttpContext的initMimeMapping中通过解析web.xml文件来初始化。
  解析文件的大致步骤：
  使用dom4j读取config/web.xml文件，将跟标签下所有名为<mime-mapping>的
  子标签获取到，并将其中的子标签：
  <extension>中间的文本作为key
  <mime-type>中间的文本作为value
  保存到mimeMapping这个Map中完成初始化。