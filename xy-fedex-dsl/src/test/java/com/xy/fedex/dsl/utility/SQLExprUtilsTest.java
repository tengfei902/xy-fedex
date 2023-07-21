package com.xy.fedex.dsl.utility;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.expr.*;
import com.alibaba.druid.sql.ast.statement.SQLSelect;
import com.xy.fedex.dsl.exceptions.SQLExprNotSupportException;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class SQLExprUtilsTest {

    @Test
    public void testRemoveDims() {
        String sql = "select sum(a) from t where t.d1 = 1 and t.d2 = 's' and t3 not in (1,3,4) and d4 between 20230401 and 20230430 and (d5 = 'g' or d6 = 10 and (d7 != 1 and d8 = 9)) or (d9 = 10) and not (d10 = 11 and d11 = 20)";
        SQLSelect select = SQLExprUtils.parse(sql);

        SQLExpr sqlExpr = select.getQueryBlock().getWhere();
        SQLExpr refactorExpr = getConditionExpr(sqlExpr, Arrays.asList("d1","d2","d5","d6","d8"));
        System.out.println(refactorExpr.toString());
    }

    private SQLExpr getConditionExpr(SQLExpr expr, List<String> allowDims) {
        if(expr instanceof SQLBinaryOpExpr && ((SQLBinaryOpExpr) expr).getOperator().isLogical()) {
            SQLBinaryOpExpr binaryOpExpr = (SQLBinaryOpExpr) expr;
            SQLExpr left = binaryOpExpr.getLeft();
            left = getConditionExpr(left,allowDims);
            binaryOpExpr.setLeft(left);

            SQLExpr right = binaryOpExpr.getRight();
            right = getConditionExpr(right,allowDims);
            binaryOpExpr.setRight(right);

            if(binaryOpExpr.getLeft() == null && binaryOpExpr.getRight() == null) {
                return null;
            }
            if(binaryOpExpr.getLeft() == null) {
                return binaryOpExpr.getRight();
            }
            if(binaryOpExpr.getRight() == null) {
                return binaryOpExpr.getLeft();
            }
            return binaryOpExpr;
        }
        if(expr instanceof SQLBinaryOpExpr) {
            SQLBinaryOpExpr binaryOpExpr = (SQLBinaryOpExpr) expr;
            SQLExpr left = binaryOpExpr.getLeft();
            left = getConditionExpr(left,allowDims);
            if(left == null) {
                return null;
            }
            return expr;
        }
        if(expr instanceof SQLBetweenExpr) {
            SQLBetweenExpr sqlBetweenExpr = (SQLBetweenExpr) expr;
            SQLExpr left = getConditionExpr(sqlBetweenExpr.getTestExpr(),allowDims);
            if(left == null) {
                return null;
            }
            return expr;
        }
        if(expr instanceof SQLCaseExpr) {
            SQLCaseExpr sqlCaseExpr = (SQLCaseExpr) expr;
            SQLExpr left = getConditionExpr(sqlCaseExpr,allowDims);
            if(left == null) {
                return null;
            }
            return expr;
        }
        if(expr instanceof SQLInListExpr) {
            SQLInListExpr sqlInListExpr = (SQLInListExpr) expr;
            SQLExpr leftExpr = getConditionExpr(sqlInListExpr.getExpr(),allowDims);
            if(leftExpr == null) {
                return null;
            }
            return expr;
        }
        if(expr instanceof SQLNotExpr) {
            SQLNotExpr sqlNotExpr = (SQLNotExpr) expr;
            SQLExpr leftExpr = getConditionExpr(sqlNotExpr.getExpr(),allowDims);
            if(leftExpr == null) {
                return null;
            }
            return expr;
        }
        if(expr instanceof SQLCastExpr) {
            SQLCastExpr sqlCastExpr = (SQLCastExpr) expr;
            SQLExpr left = getConditionExpr(sqlCastExpr.getExpr(),allowDims);
            if(left == null) {
                return null;
            }
            return expr;
        }
        if(expr instanceof SQLIdentifierExpr) {
            SQLIdentifierExpr sqlIdentifierExpr = (SQLIdentifierExpr) expr;
            String name = sqlIdentifierExpr.getName();
            if(allowDims.contains(name)) {
                return expr;
            }
            return null;
        }
        if(expr instanceof SQLPropertyExpr) {
            SQLPropertyExpr sqlPropertyExpr = (SQLPropertyExpr) expr;
            String name = sqlPropertyExpr.getName();
            if(allowDims.contains(name)) {
                return expr;
            }
            return null;
        }
        throw new SQLExprNotSupportException(expr.getClass().getName());
    }
}
