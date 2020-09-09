package com.chen.f.core.configuration.db;

import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 开启chen-f 嵌入式数据库配置
 *
 * @author chen
 * @since 2020/9/9 18:13.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({EnableChenFEmbeddedDataBaseConfiguration.class,})
public @interface EnableChenFEmbeddedDataBase {

    EmbeddedDatabaseConnection connection() default EmbeddedDatabaseConnection.NONE;
}
