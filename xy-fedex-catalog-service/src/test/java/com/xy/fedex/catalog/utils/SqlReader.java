package com.xy.fedex.catalog.utils;

import com.alibaba.druid.DbType;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.expr.SQLCharExpr;
import com.alibaba.druid.sql.ast.statement.SQLAssignItem;
import com.alibaba.druid.sql.ast.statement.SQLSelect;
import com.alibaba.druid.sql.dialect.hive.stmt.HiveCreateTableStatement;
import com.facebook.presto.sql.parser.ParsingOptions;
import com.facebook.presto.sql.parser.SqlParser;
import com.facebook.presto.sql.tree.Statement;
import com.google.common.base.Joiner;
import com.xy.fedex.def.Response;
import com.xy.fedex.dsl.utility.SQLExprUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.spark.sql.catalyst.parser.CatalystSqlParser;
import org.apache.spark.sql.catalyst.plans.logical.LogicalPlan;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SqlReader {

    public static String read(String file) {
        try {
            ClassPathResource classPathResource = new ClassPathResource(file);
            InputStream in = classPathResource.getInputStream();
            StringBuilder sb = new StringBuilder();
            byte[] buff = new byte[1024];
            int byteRead = 0;
            while ((byteRead = in.read(buff)) != -1) {
                sb.append(new String(buff,0,byteRead,"utf-8"));
            }
            in.close();
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testGetSelectItem() {
        String sql = "select sum(${1})/sum(${2})";
        SQLSelect sqlSelect = SQLExprUtils.parse(sql);
        System.out.println("-----");

        String pattern = "\\$\\{(.+?)\\}";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(sql);
        while (m.find()) {
            System.out.println(m.group());
        }
    }

    @Test
    public void testReadDDL() {
        List<Long> modelIds = new ArrayList<>();
        List<String> models = Arrays.asList("model_flow_shop_sku_dt.sql");
        for(String model:models) {
            String sql = SqlReader.read("ddl/"+model);
//            CatalystSqlParser parser = new CatalystSqlParser();
//            LogicalPlan parsedPlan = parser.parsePlan(sql);

//            SqlParser parser = new SqlParser();
//            Statement statement = parser.createStatement(sql, ParsingOptions.builder().build());
            System.out.println("----");
            SQLStatement sqlStatement = SQLUtils.parseSingleStatement(sql, DbType.hive);
            HiveCreateTableStatement hiveCreateTableStatement = (HiveCreateTableStatement) sqlStatement;
            SQLSelect select = hiveCreateTableStatement.getSelect();
            String modelComment = ((SQLCharExpr)hiveCreateTableStatement.getComment()).getText();
            List<SQLAssignItem> tblProperties = hiveCreateTableStatement.getTblProperties();
        }
        System.out.println(Joiner.on(",").join(modelIds));
    }
}
