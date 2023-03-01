package com.xy.fedex.dsl.utility;

import com.alibaba.druid.DbType;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.SQLSelect;
import com.alibaba.druid.sql.ast.statement.SQLSelectItem;
import com.alibaba.druid.sql.ast.statement.SQLSelectQuery;
import com.alibaba.druid.sql.ast.statement.SQLSelectStatement;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlCreateTableStatement;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlSelectQueryBlock;
import com.alibaba.druid.sql.parser.SQLParserUtils;

import java.util.List;

public class SQLExprUtils {
    public static void testParse() {
        SQLUtils.toSQLExpr("s",DbType.mysql);
    }

    public static String getTableSource(SQLSelect logicalSelect) {
        MySqlSelectQueryBlock mySqlSelectQueryBlock = (MySqlSelectQueryBlock) logicalSelect.getQueryBlock();
        return mySqlSelectQueryBlock.getFrom().toString();
    }

    public static SQLSelect parse(String sql) {
        SQLStatement sqlStatement = SQLUtils.parseSingleStatement(sql,DbType.mysql);
        SQLSelectStatement sqlSelectStatement = (SQLSelectStatement) sqlStatement;
        return sqlSelectStatement.getSelect();
        //MySqlSelectQueryBlock mySqlSelectQueryBlock = (MySqlSelectQueryBlock) sqlSelectStatement.getSelect().getQuery();
    }

    public static void parseCreateTable(String sql) {
        SQLStatement sqlStatement = SQLUtils.parseSingleStatement(sql,DbType.mysql);
        MySqlCreateTableStatement createTableStatement = (MySqlCreateTableStatement) sqlStatement;
    }

    public static List<String> getAllFields(SQLSelect select) {
        SQLSelectQuery sqlSelectQuery = select.getQuery();
        if(sqlSelectQuery instanceof MySqlSelectQueryBlock) {
            MySqlSelectQueryBlock mySqlSelectQueryBlock = (MySqlSelectQueryBlock) sqlSelectQuery;
            List<SQLSelectItem> selectItems = mySqlSelectQueryBlock.getSelectList();
//            selectItems.stream().map(sqlSelectItem -> sqlSelectItem.get)
        }
        return null;
    }

    public static List<String> getMetrics(SQLSelect select) {
        return null;
    }

    public static List<String> getDims(SQLSelect select) {
        return null;
    }

}
