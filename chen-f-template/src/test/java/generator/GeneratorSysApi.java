package generator;

import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.chen.f.common.mapper.SysApiMapper;
import com.chen.f.common.pojo.SysApi;
import com.chen.f.common.pojo.enums.StatusEnum;
import com.chen.f.common.pojo.enums.SysApiHttpMethodEnum;
import com.chen.f.common.pojo.enums.SysApiTypeEnum;
import com.chen.f.core.util.JacksonUtils;
import com.chen.f.template.ChenFTemplateApplication;
import com.fasterxml.jackson.databind.JsonNode;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.NullValue;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.insert.Insert;
import org.apache.commons.collections4.IteratorUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * <p>
 * 根据swagger api
 * 生成系统接口
 * 插入sql
 * </p>
 *
 * @author chen
 * @date 2017/12/18
 */
@EnableSwagger2
@AutoConfigureMockMvc
@SpringBootTest(classes = ChenFTemplateApplication.class)
public class GeneratorSysApi {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private SysApiMapper sysApiMapper;


    //@Test
    public void generatorSysApi() throws Exception {

        //获取文档json
        final MvcResult mvcResult = mvc.perform(
                get("/v2/api-docs")
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf())
        )
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        final String contentAsString = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);

        //转换为系统接口对象列表
        final JsonNode jsonNode = JacksonUtils.parse(contentAsString);
        final JsonNode paths = jsonNode.get("paths");
        final List<Map.Entry<String, JsonNode>> pathsEntryList = IteratorUtils.toList(paths.fields());
        
        final LongAdder longAdder = new LongAdder();
        final List<SysApi> sysApiList = pathsEntryList.stream()
                .filter((pathsEntry) -> !StringUtils.startsWith(pathsEntry.getKey(), "/error"))
                .flatMap((pathsEntry) -> {
                    final List<Map.Entry<String, JsonNode>> apiEntrySet = IteratorUtils.toList(pathsEntry.getValue().fields());
                    return apiEntrySet.stream()
                            .map((apiEntry) -> {
                                final JsonNode value =apiEntry.getValue();
                                longAdder.increment();

                                final SysApi sysApi = new SysApi();
                                sysApi.setId(longAdder.toString());
                                sysApi.setName(value.get("summary").asText());
                                sysApi.setUrl(pathsEntry.getKey());
                                sysApi.setHttpMethod(SysApiHttpMethodEnum.of(apiEntry.getKey().toUpperCase()));
                                sysApi.setType(SysApiTypeEnum.SYSTEM);
                                sysApi.setRemark("");
                                sysApi.setStatus(StatusEnum.ENABLED);
                                sysApi.setUpdatedSysUserId("");
                                sysApi.setCreatedSysUserId("");
                                sysApi.setUpdatedDateTime(LocalDateTime.now());
                                sysApi.setCreatedDateTime(LocalDateTime.now());
                                return sysApi;
                            });
                })
                .collect(Collectors.toList());

        //转换为sql列表
        final String sqlStatement = SqlHelper.getSqlStatement(SysApiMapper.class, SqlMethod.INSERT_ONE);
        final SqlSession sqlSession = SqlHelper.sqlSession(SysApi.class);
        final Configuration configuration = sqlSession.getConfiguration();
        final MappedStatement mappedStatement = configuration.getMappedStatement(sqlStatement);

        final TableInfo tableInfo = SqlHelper.table(SysApi.class);
        final Map<String, Field> columnFieldMap = tableInfo.getFieldList().stream()
                .peek((tableFieldInfo -> tableFieldInfo.getField().setAccessible(true)))
                .collect(Collectors.toMap(TableFieldInfo::getColumn, TableFieldInfo::getField));
        final Field idField = SysApi.class.getDeclaredField("id");
        idField.setAccessible(true);
        columnFieldMap.put("`ID`", idField);

        final List<String> sqlList = new ArrayList<>();
        for (SysApi sysApi : sysApiList) {
            final BoundSql boundSql = mappedStatement.getBoundSql(sysApi);
            final Insert insert = (Insert) CCJSqlParserUtil.parse(boundSql.getSql());

            final List<Column> columns = insert.getColumns();
            final List<Expression> expressionListList = new ArrayList<>();
            final ExpressionList expressionList = new ExpressionList(expressionListList);
            for (Column column : columns) {
                final String columnName = column.getColumnName();
                final Field field = columnFieldMap.get(columnName);
                final Object o = field.get(sysApi);

                if (Objects.nonNull(o)) {
                    expressionListList.add(new StringValue(Objects.toString(o, null)));
                } else {
                    expressionListList.add(new NullValue());
                }
            }
            insert.setItemsList(expressionList);

            final String sql = insert.toString();
            sqlList.add(sql);
        }

        //打印sql
        System.out.println(sqlList.stream().collect(Collectors.joining(";\n", "", ";")));

        //插入数据库
        sysApiMapper.insertBatch(sysApiList);
    }

}
