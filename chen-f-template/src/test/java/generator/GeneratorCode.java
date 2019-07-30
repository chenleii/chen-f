package generator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.chen.f.common.mybatisplus.SupperMapper;
import org.junit.Test;

/**
 * <p>
 * 生成代码
 * </p>
 *
 * @author chen
 * @date 2017/12/18
 */
public class GeneratorCode {
    private static final String parentPackageName = "com.chen.f.core";
    private static final String[] tableNames = new String[]{"sys_api","sys_role_api","sys_permission_api"};

    @Test
    public void generateCode() {
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL)
                .setUrl("jdbc:mysql://127.0.0.1:3306/chen?characterEncoding=utf8&useSSL=false")
                .setUsername("root")
                .setPassword("123456")
                .setDriverName("com.mysql.jdbc.Driver");

        StrategyConfig strategyConfig = new StrategyConfig()
                .setCapitalMode(true)
                .setSkipView(true)
                .setNaming(NamingStrategy.underline_to_camel)
                .setTablePrefix("")
                .setFieldPrefix("")
                .setSuperEntityColumns("", "")
                //.setSuperEntityClass(Object.class.getName())
                .setSuperMapperClass(SupperMapper.class.getName())
                .setEntityLombokModel(true)
                .setRestControllerStyle(true)
                .setEntityTableFieldAnnotationEnable(true)
                .setVersionFieldName("version")
                .setLogicDeleteFieldName("deleted")
                //修改替换成你需要的表名，多个表名传数组
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
                .setOutputDir("G:\\codeGen")
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
                .setIdType(IdType.ID_WORKER_STR);

        new AutoGenerator()
                .setGlobalConfig(globalConfig)
                .setDataSource(dataSourceConfig)
                .setStrategy(strategyConfig)
                .setPackageInfo(packageConfig)
                .execute();
    }
}
