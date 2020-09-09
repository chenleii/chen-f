package com.chen.f.core.configuration.db;

import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.util.Assert;

import javax.sql.DataSource;

/**
 * Embedded DataBase Configuration.
 *
 * @author chene
 * @since 2020/9/3 0:37.
 */
@Configuration
public class EnableChenFEmbeddedDataBaseConfiguration implements ImportAware {

    private EmbeddedDatabaseConnection connection = EmbeddedDatabaseConnection.NONE;

    @Override
    public void setImportMetadata(AnnotationMetadata importMetadata) {
        AnnotationAttributes annotationAttributes = AnnotationAttributes
                .fromMap(importMetadata.getAnnotationAttributes(EnableChenFEmbeddedDataBase.class.getName()));
        Assert.notNull(annotationAttributes,
                "@EnableChenFEmbeddedDataBase is not present on importing class " + importMetadata.getClassName());

        this.connection = annotationAttributes.getEnum("connection");
    }

    @Bean
    public DataSource embeddedDataSource() {
        if (EmbeddedDatabaseConnection.NONE.equals(connection)) {
            connection = EmbeddedDatabaseConnection.get(getClass().getClassLoader());
        }
        Assert.state(connection != EmbeddedDatabaseConnection.NONE,
                "Failed to replace DataSource with an embedded database for tests. If "
                        + "you want an embedded database please put a supported one "
                        + "on the classpath or tune the replace attribute of @AutoConfigureTestDatabase.");
        return new EmbeddedDatabaseBuilder().generateUniqueName(true).setType(connection.getType()).build();

    }

}
