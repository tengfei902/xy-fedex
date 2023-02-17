package com.xy.fedex.facade.utils;

import com.alibaba.druid.DbType;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.SQLSelect;
import com.alibaba.druid.sql.ast.statement.SQLTableSource;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlSelectQueryBlock;
import com.xy.fedex.dsl.utility.SQLExprUtils;
import org.junit.jupiter.api.Test;

import java.util.List;

public class SQLUtilsTest {

    @Test
    public void testParseSql() {
        String sql = "select a,b,c from t where d = 10 and e = 100";
        List<SQLStatement> statements =  SQLUtils.parseStatements(sql, DbType.mysql);
        System.out.println("-------");

        SQLSelect sqlSelect = SQLExprUtils.parse(sql);
        MySqlSelectQueryBlock mySqlSelectQueryBlock = (MySqlSelectQueryBlock) sqlSelect.getQueryBlock();
        SQLTableSource sqlTableSource = mySqlSelectQueryBlock.getFrom();
        SQLExpr sqlExpr = mySqlSelectQueryBlock.getWhere();
    }
}
