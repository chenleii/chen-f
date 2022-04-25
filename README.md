# chen-f
基于SpringBoot2的简单后台管理系统框架。

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
* git克隆项目到本地，并使用`maven`正常加载项目。
* 打开项目中 `chen-f-template` 模块，运行类`com.chen.f.template.ChenFTemplateApplication`启动模板项目（默认使用嵌入式数据库）。
* 访问链接: `http://localhost:8080/doc.html`。

## 将本项目作为三方依赖引入自己的项目
* git克隆项目到本地，将项目安装到`maven`本地仓库。
```
mvnw clean install
```

* 新建一个`SpringBoot`项目，并添加该依赖。
```
<dependency>
    <groupId>com.chen</groupId>
    <artifactId>chen-f-spring-boot-starter</artifactId>
    <version>0.0.2</version>
 </dependency>
```

* 项目启动类添加注解`@EnableChenFAdmin`，即可拥有该项目提供的功能。
```java
@EnableChenFAdmin
@SpringBootApplication
public class ChenFTemplateApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(ChenFTemplateApplication.class, args);
    }
}
```

