package com.xy.fedex.dsl.utility;

import com.alibaba.druid.DbType;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.expr.*;
import com.alibaba.druid.sql.ast.statement.*;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlCreateTableStatement;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlSelectQueryBlock;
import com.alibaba.druid.sql.parser.SQLParserUtils;
import com.xy.fedex.dsl.exceptions.SQLExprNotSupportException;

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

    public static String getTableName(SQLExprTableSource tableSource) {
        SQLExpr sqlExpr = tableSource.getExpr();
        if(sqlExpr instanceof SQLIntegerExpr) {
            SQLIntegerExpr sqlIntegerExpr = (SQLIntegerExpr) sqlExpr;
            return String.valueOf(sqlIntegerExpr.getNumber());
        }
        if(sqlExpr instanceof SQLLiteralExpr) {
            SQLLiteralExpr sqlLiteralExpr = (SQLLiteralExpr) sqlExpr;
            return null;
        }
        if(sqlExpr instanceof SQLIdentifierExpr) {
            SQLIdentifierExpr sqlIdentifierExpr = (SQLIdentifierExpr) sqlExpr;
            return sqlIdentifierExpr.getName();
        }
        throw new SQLExprNotSupportException("sqlExpr type not support:"+sqlExpr.getClass().getName());
    }

    public static void getSqlConditionFieldExpr(SQLExpr expr,SQLExprFunction callBackFunc) {
        if(expr instanceof SQLBinaryOpExpr && ((SQLBinaryOpExpr) expr).getOperator().isLogical()) {
            SQLBinaryOpExpr binaryOpExpr = (SQLBinaryOpExpr) expr;
            SQLExpr left = binaryOpExpr.getLeft();
            getSqlConditionFieldExpr(left,callBackFunc);

            SQLExpr right = binaryOpExpr.getRight();
            getSqlConditionFieldExpr(right,callBackFunc);
            return;
        }
        if(expr instanceof SQLBinaryOpExpr) {
            SQLBinaryOpExpr binaryOpExpr = (SQLBinaryOpExpr) expr;
            SQLExpr left = binaryOpExpr.getLeft();
            getSqlConditionFieldExpr(left,callBackFunc);
            return;
        }
        if(expr instanceof SQLBetweenExpr) {
            SQLBetweenExpr sqlBetweenExpr = (SQLBetweenExpr) expr;
            getSqlConditionFieldExpr(sqlBetweenExpr.getTestExpr(),callBackFunc);
            return;
        }
        if(expr instanceof SQLCaseExpr) {
            SQLCaseExpr sqlCaseExpr = (SQLCaseExpr) expr;
            getSqlConditionFieldExpr(sqlCaseExpr,callBackFunc);
            return;
        }
        if(expr instanceof SQLInListExpr) {
            SQLInListExpr sqlInListExpr = (SQLInListExpr) expr;
            getSqlConditionFieldExpr(sqlInListExpr.getExpr(),callBackFunc);
            return;
        }
        if(expr instanceof SQLNotExpr) {
            SQLNotExpr sqlNotExpr = (SQLNotExpr) expr;
            getSqlConditionFieldExpr(sqlNotExpr.getExpr(),callBackFunc);
            return;
        }
        if(expr instanceof SQLCastExpr) {
            SQLCastExpr sqlCastExpr = (SQLCastExpr) expr;
            getSqlConditionFieldExpr(sqlCastExpr.getExpr(),callBackFunc);
            return;
        }
        if(expr instanceof SQLIdentifierExpr) {
            SQLIdentifierExpr sqlIdentifierExpr = (SQLIdentifierExpr) expr;
            callBackFunc.doCallBack(sqlIdentifierExpr);
            return;
        }
        throw new SQLExprNotSupportException("SQL expr type not support:"+expr.getClass().getName());
    }

    public interface SQLExprFunction {
        void doCallBack(SQLExpr expr);
    }
}
