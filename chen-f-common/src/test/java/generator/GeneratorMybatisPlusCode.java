package generator;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.chen.f.core.mybatisplus.SupperMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;

import javax.sql.DataSource;
import java.lang.reflect.Field;

/**
 * <p>
 * 生成 mybatis-plus 代码
 * </p>
 *
 * @author chen
 * @date 2017/12/18
 */
@SpringBootTest(classes = DataSource.class)
@AutoConfigureTestDatabase
@ImportAutoConfiguration(classes = DataSourceAutoConfiguration.class)
public class GeneratorMybatisPlusCode {
    private static final String parentPackageName = "com.chen.f.common";
    private static final String generatorMybatisPlusCodeDir = System.getProperty("user.dir") + "/target/mybatis-plus-code";

    private static final String[] tableNames = {};

    @Autowired
    private DataSourceProperties dataSourceProperties;
    @Autowired
    private DataSource dataSource;
    
    
    //@Test
    public void generatorMybatisPlusCode() throws Exception {
        String url = dataSourceProperties.determineUrl();
        String username = dataSourceProperties.determineUsername();
        String password = dataSourceProperties.determinePassword();
        String driverName = dataSourceProperties.determineDriverClassName();
        
        if (dataSource instanceof EmbeddedDatabase){
            final Field dataSourceField = this.dataSource.getClass().getDeclaredField("dataSource");
            dataSourceField.setAccessible(true);
            
            final Object o = dataSourceField.get(dataSource);
            if (o instanceof SimpleDriverDataSource) {
                final SimpleDriverDataSource simpleDriverDataSource = (SimpleDriverDataSource) o;
                url = simpleDriverDataSource.getUrl();
                username = simpleDriverDataSource.getUsername();
                password = simpleDriverDataSource.getPassword();
                driverName = simpleDriverDataSource.getDriver().getClass().getName();
            }
        }

        //driverName = "com.mysql.cj.jdbc.Driver";
        //url = "jdbc:mysql://127.0.0.1:3306/chen?characterEncoding=utf8&useSSL=false";
        //username = "root";
        //password = "123456";

        DataSourceConfig dataSourceConfig = new DataSourceConfig()
                .setDriverName(driverName)
                .setUrl(url)
                .setUsername(username)
                .setPassword(password);

        StrategyConfig strategyConfig = new StrategyConfig()
                .setCapitalMode(true)
                .setSkipView(true)
                .setNaming(NamingStrategy.underline_to_camel)
                .setTablePrefix("")
                .setFieldPrefix("")
                .setSuperEntityColumns("")
                //.setSuperEntityClass(Object.class.getName())
                .setSuperMapperClass(SupperMapper.class.getName())
                .setEntityLombokModel(true)
                .setRestControllerStyle(true)
                .setEntityTableFieldAnnotationEnable(true)
                .setEntityBooleanColumnRemoveIsPrefix(true)
                .setVersionFieldName("version")
                .setLogicDeleteFieldName("is_deleted")
                .setInclude(tableNames);

        PackageConfig packageConfig = new PackageConfig()
                .setParent(parentPackageName)
                .setModuleName("")
                .setEntity("pojo")
                .setService("service")
                .setServiceImpl("service.impl")
                .setMapper("mapper")
                .setXml("mapper.xml")
                .setController("web.controller");


        GlobalConfig globalConfig = new GlobalConfig()
                .setOutputDir(generatorMybatisPlusCodeDir)
                .setOpen(true)
                .setFileOverride(true)
                // XML 二级缓存
                .setEnableCache(false)
                .setAuthor("chen")
                .setSwagger2(true)
                .setActiveRecord(true)
                // XML ResultMap
                .setBaseResultMap(true)
                .setBaseColumnList(true)
                .setDateType(DateType.TIME_PACK)
                .setIdType(IdType.ASSIGN_ID);

        new AutoGenerator()
                .setDataSource(dataSourceConfig)
                .setStrategy(strategyConfig)
                .setPackageInfo(packageConfig)
                .setGlobalConfig(globalConfig)
                .execute();
    }
}
