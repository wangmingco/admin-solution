## 管理后台-服务器部分

### 后台技术架构
* springboot 整个后台应用的容器管理
* shiro 权限管理, 
* mybatis 作为orm框架
* springboot 自带HikariCP数据源
* maven 依赖管理, 项目发布
* junit4 测试框架
* JMockit 测试mock框架

### 后台目录说明
* docs目录里存放创建库的SQL文件以及项目说明文档

maven 项目结构介绍
* src/main/java 项目工程代码
* src/main/resources 项目工程配置(application.properties springboot配置文件, logback.xml logback日志配置文件)
* src/test/java 项目测试代码
* src/test/resources 项目测试配置

#### Java工程说明
* config 配置文件路径
* controller http请求入口
* enums 常量枚举
* logger 用代理模式对日志框架的封装(logback/slf4j) 
* mapper 对数据库的操作
* model 操作数据库的实例bean
* service 逻辑处理
* shiro 权限管理
* util 工具类