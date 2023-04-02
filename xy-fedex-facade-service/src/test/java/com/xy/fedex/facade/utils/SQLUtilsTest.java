package com.xy.fedex.facade.utils;

import com.alibaba.druid.DbType;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.expr.*;
import com.alibaba.druid.sql.ast.statement.*;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlSelectQueryBlock;
import com.xy.fedex.dsl.utility.SQLExprUtils;
import com.xy.fedex.facade.exceptions.SQLExprTypeNotSupportException;
import org.junit.jupiter.api.Test;

import java.util.List;

public class SQLUtilsTest {

    @Test
    public void testParseSql() {
        String sql = "select a,b,c from t where d = 10 and e = 100 and (c = 100 or d > 1000)";
        List<SQLStatement> statements =  SQLUtils.parseStatements(sql, DbType.mysql);
        System.out.println("-------");

        SQLSelect sqlSelect = SQLExprUtils.parse(sql);
        MySqlSelectQueryBlock mySqlSelectQueryBlock = (MySqlSelectQueryBlock) sqlSelect.getQueryBlock();
        SQLTableSource sqlTableSource = mySqlSelectQueryBlock.getFrom();
        SQLExpr sqlExpr = mySqlSelectQueryBlock.getWhere();
        String s = "";
        SQLUtils.toSQLExpr("d = 10 and e = 100 and (c = 100 or d > 1000)");
        SQLUtils.parseStatements("d = 10 and e = 100 and (c = 100 or d > 1000)",DbType.mysql);
//        new SQLBinaryOpExpr(s);
        SQLBinaryOpExpr sqlBinaryOpExpr = new SQLBinaryOpExpr();
    }

    @Test
    public void testGetMySQLBlock() {
        MySqlSelectQueryBlock mySqlSelectQueryBlock = new MySqlSelectQueryBlock();
        //select item
        mySqlSelectQueryBlock.addSelectItem("sum(t1.paid_amount)","paid_amount");
        mySqlSelectQueryBlock.addSelectItem("sum(t1.amount)","amount");
        mySqlSelectQueryBlock.addSelectItem("t1.dt","dt");
        //from
        mySqlSelectQueryBlock.setFrom("pay_request","t");
        //where
        mySqlSelectQueryBlock.addCondition("t1.dt between 20230101 and 20230401");
        mySqlSelectQueryBlock.addCondition("t1.trade_type = 1");

        SQLSelectGroupByClause sqlSelectGroupByClause = new SQLSelectGroupByClause();
        sqlSelectGroupByClause.addItem(mySqlSelectQueryBlock.getSelectItem(2).getExpr());
        mySqlSelectQueryBlock.setGroupBy(sqlSelectGroupByClause);
        System.out.println( mySqlSelectQueryBlock.toString());
    }

    @Test
    public void testGetLocalizedCondition() {
        String condition = "dt between 20230101 and 20230301 and trade_type = 1 and not channel_code = 'hft' and opr_status in (1,2,3,4,5) and opr_status not in (10) and cast(buyer_id as char) = 'aaa' ";
        SQLExpr conditionExpr = SQLUtils.toSQLExpr(condition);
        getSqlConditionExpr(conditionExpr);
        System.out.println("-------");
    }

    private void getSqlConditionExpr(SQLExpr expr) {
        if(expr instanceof SQLBinaryOpExpr && ((SQLBinaryOpExpr) expr).getOperator().isLogical()) {
            SQLBinaryOpExpr binaryOpExpr = (SQLBinaryOpExpr) expr;
            SQLExpr left = binaryOpExpr.getLeft();
            getSqlConditionExpr(left);

            SQLExpr right = binaryOpExpr.getRight();
            getSqlConditionExpr(right);
            return;
        }
        if(expr instanceof SQLBinaryOpExpr) {
            SQLBinaryOpExpr binaryOpExpr = (SQLBinaryOpExpr) expr;
            SQLExpr left = binaryOpExpr.getLeft();
            getSqlConditionExpr(left);
            return;
        }
        if(expr instanceof SQLBetweenExpr) {
            SQLBetweenExpr sqlBetweenExpr = (SQLBetweenExpr) expr;
            getSqlConditionExpr(sqlBetweenExpr.getTestExpr());
            return;
        }
        if(expr instanceof SQLCaseExpr) {
            SQLCaseExpr sqlCaseExpr = (SQLCaseExpr) expr;
            getSqlConditionExpr(sqlCaseExpr);
            return;
        }
        if(expr instanceof SQLInListExpr) {
            SQLInListExpr sqlInListExpr = (SQLInListExpr) expr;
            getSqlConditionExpr(sqlInListExpr.getExpr());
            return;
        }
        if(expr instanceof SQLNotExpr) {
            SQLNotExpr sqlNotExpr = (SQLNotExpr) expr;
            getSqlConditionExpr(sqlNotExpr.getExpr());
            return;
        }
        if(expr instanceof SQLCastExpr) {
            SQLCastExpr sqlCastExpr = (SQLCastExpr) expr;
            getSqlConditionExpr(sqlCastExpr.getExpr());
            return;
        }
        if(expr instanceof SQLIdentifierExpr) {
            SQLIdentifierExpr sqlIdentifierExpr = (SQLIdentifierExpr) expr;
            System.out.println(sqlIdentifierExpr.getName());
            return;
        }
        throw new SQLExprTypeNotSupportException("SQL expr type not support:"+expr.getClass().getName());
    }

    @Test
    public void testGetTableSource() {
        SQLJoinTableSource sqlJoinTableSource = new SQLJoinTableSource();
        SQLSubqueryTableSource sqlSubqueryTableSource = new SQLSubqueryTableSource();
//        sqlJoinTableSource.setLeft();
    }
}
