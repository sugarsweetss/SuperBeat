两个文件夹：
ssm是一个小模板
Ui-GUI是之前做过的项目，可以参考里面的代码

开发工具：eclipse
jdk1.8
我的是tomcat9，tomcat8应该也是可以的
数据库用的是mysql
导入项目，打开config目录，下有个db.properties文件，里面的url最后面ssmtest是数据库名，username是用户名，password是用户密码，改成你们的
在entity包下，把对应的实体类创建好
在controller等包下创建需要的类和方法，按照已有的例子，套模板，该有的注解不能少
启动项目：项目右键，run as --> run on server