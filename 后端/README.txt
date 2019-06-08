后端总体框架基于SSM，服务器采用tomcat9，数据库采用mySql。
开发工具：eclipse
开发环境：jdk1.8

启动项目：右键ssm项目，run as-->run on server

ssm是后端项目文件。下面具体介绍ssm中的配置：
config目录下，db.properties是对数据库的配置。
src目录下weixin_mp包是业务逻辑的具体实现：
controller包是后端业务的控制层，通过调用serviceImpl中的方法实现具体功能；entity包是后端实体，供其他模块调用；mapper包是后端数据库操作，.java文件为数据库操作的java代码实现，.xml为数据库操作的mybatis实现；methods包中包括核心算法的代码；service包中包含三个接口，每个接口中定义了若干方法；serviceImpl中的类分别对应继承service中的接口，并具体实现各方法的功能。
