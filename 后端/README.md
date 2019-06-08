## 后端简介
* 运用技术
  * 后端总体框架
    * SSM
  * 服务器
    * tomcat9
  * 数据库
    * mySql。
* 开发工具
  * eclipse
* 开发环境
  * jdk1.8
## 启动项目
右键ssm项目，run as-->run on server
## 项目配置
### config目录
db.properties是对数据库的配置。
### src目录
weixin_mp包是业务逻辑的具体实现。
* controller
  * 后端业务的控制层，通过调用serviceImpl中的方法实现具体功能。
* entity
  * 后端实体，供其他模块调用。
* mapper
  * 后端数据库操作，.java文件为数据库操作的java代码实现，.xml为数据库操作的mybatis实现。
* methods
  * 包括核心算法的代码。
* service
  * 包含三个接口，每个接口中定义了若干方法。
* serviceImpl
  * 其中的类分别对应继承service中的接口，并具体实现各方法的功能。
