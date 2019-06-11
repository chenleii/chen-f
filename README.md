# chen-f
基于SpringBoot2的简单后台管理系统框架

## 功能列表
* 基于 `Spring Security` 的用户角色权限菜单管理。
* 基于 `Spring Seesion` 的统一会话管理，重启会话不丢失。
* 基于 `数据库 + Quartz` 的定时任务配置,支持在线删除 修改 启动 停止 立即执行。
* 基于 `Mybatis Plus` 的持久层减少SQL开发量，支持多数据源可动态切换数据源。
* 基于 `Mybatis Plus` 的代码生成，使编码时间都用在核心业务上。
* 基于 `REST` 规范，通过 `@ControllerAdvice` 处理异常及返回错误信息。
* 基于 `Druid` 数据库连接池，监控数据库访问性能，统计SQL的执行性能。
* 基于 `log4j2` 打印日志，提高日志性能。并根据时间和文件大小分割日志文件。
* 基于 `Swagger2` 的统一API文档管理。

## 快速开始
* 克隆项目到本地 将项目安装到`maven`本地仓库
```
mvnw clean install
```

* 新建一个SpringBoot项目 添加依赖
```
<dependency>
    <groupId>com.chen</groupId>
    <artifactId>chen-f-spring-boot-starter</artifactId>
    <version>0.0.1</version>
 </dependency>
```

* 项目启动类添加注解 `@EnableChenFAdmin`
```java
@EnableChenFAdmin
@SpringBootApplication
public class ChenFTemplateApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(ChenFTemplateApplication.class, args);
    }
}
```
* 参考 `chen-f-template` 项目 配置数据库和Redis并启动项目 
