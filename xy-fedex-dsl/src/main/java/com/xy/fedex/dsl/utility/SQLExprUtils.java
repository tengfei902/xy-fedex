package com.xy.fedex.dsl.utility;

import com.alibaba.druid.DbType;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.expr.*;
import com.alibaba.druid.sql.ast.statement.*;
import com.alibaba.druid.sql.dialect.hive.stmt.HiveCreateTableStatement;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlCreateTableStatement;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlSelectQueryBlock;
import com.alibaba.druid.sql.parser.SQLParserUtils;
import com.xy.fedex.dsl.exceptions.SQLExprNotSupportException;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

public class SQLExprUtils {
    public static void testParse() {
        SQLUtils.toSQLExpr("s",DbType.mysql);
    }

    public static String getTableSource(SQLSelect logicalSelect) {
        MySqlSelectQueryBlock mySqlSelectQueryBlock = (MySqlSelectQueryBlock) logicalSelect.getQueryBlock();
        return mySqlSelectQueryBlock.getFrom().toString();
    }

    public static SQLTableSource getTableSource(String tableSource) {
        SQLExpr sqlExpr = SQLUtils.toMySqlExpr("select * from "+tableSource);
        SQLQueryExpr sqlQueryExpr = (SQLQueryExpr) sqlExpr;
        SQLSelect sqlSelect = sqlQueryExpr.getSubQuery();
        MySqlSelectQueryBlock mySqlSelectQueryBlock = (MySqlSelectQueryBlock) sqlSelect.getQueryBlock();
        return mySqlSelectQueryBlock.getFrom();
    }

    public static SQLExpr getSqlCondition(String condition) {
        if(StringUtils.isEmpty(condition)) {
            return null;
        }
        SQLSelect select = parse("select * from t where "+condition);
        return select.getQueryBlock().getWhere();
    }

    public static SQLSelectItem getSqlSelectItem(String selectItem) {
        if(StringUtils.isEmpty(selectItem)) {
            return null;
        }
        SQLSelect select = parse("select %s from t");
        return select.getQueryBlock().getSelectList().get(0);
    }

    public static SQLSelect parse(String sql) {
        SQLStatement sqlStatement = SQLUtils.parseSingleStatement(sql,DbType.mysql);
        SQLSelectStatement sqlSelectStatement = (SQLSelectStatement) sqlStatement;
        return sqlSelectStatement.getSelect();
        //MySqlSelectQueryBlock mySqlSelectQueryBlock = (MySqlSelectQueryBlock) sqlSelectStatement.getSelect().getQuery();
    }

    public static List<String> getAllFields(SQLSelectQueryBlock selectQueryBlock) {
        List<String> allFields = new ArrayList<>();
        SQLExprFunction sqlExprFunction = new SQLExprFunction() {
            @Override
            public void doCallBack(SQLExpr expr) {
                SQLIdentifierExpr sqlIdentifierExpr = (SQLIdentifierExpr) expr;
                allFields.add(sqlIdentifierExpr.getName());
            }
        };
        //select items
        for(SQLSelectItem selectItem : selectQueryBlock.getSelectList()) {
            getSelectItemExpr(selectItem.getExpr(),sqlExprFunction);
        }
        //condition
        SQLExpr condition = selectQueryBlock.getWhere();
        getSqlConditionFieldExpr(condition, sqlExprFunction);
        return allFields.stream().distinct().collect(Collectors.toList());
    }

    public static HiveCreateTableStatement parseCreateTable(String sql) {
        SQLStatement sqlStatement = SQLUtils.parseSingleStatement(sql,DbType.hive);
        return (HiveCreateTableStatement) sqlStatement;
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
        if(Objects.isNull(expr)) {
            return;
        }
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

            SQLExpr right = binaryOpExpr.getRight();
            getSqlConditionFieldExpr(right,callBackFunc);
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
        if(expr instanceof SQLIntegerExpr) {
            return;
        }
        if(expr instanceof SQLDecimalExpr) {
            return;
        }
        if(expr instanceof SQLDoubleExpr) {
            return;
        }
        if(expr instanceof SQLFloatExpr) {
            return;
        }
        if(expr instanceof SQLSmallIntExpr) {
            return;
        }
        if(expr instanceof SQLBigIntExpr) {
            return;
        }
        if(expr instanceof SQLBooleanExpr) {
            return;
        }
        if(expr instanceof SQLCharExpr) {
            return;
        }
        throw new SQLExprNotSupportException("SQL expr type not support:"+expr.getClass().getName());
    }

    public static List<String> getSelectItemAliases(SQLSelect select) {
        MySqlSelectQueryBlock sqlSelectQuery = (MySqlSelectQueryBlock) select.getQueryBlock();
        return getSelectItemAliases(sqlSelectQuery);
    }

    public static List<String> getSelectItemAliases(MySqlSelectQueryBlock block) {
        List<String> selectItemAliases = new ArrayList<>();
        SQLExprFunction sqlExprFunction = new SQLExprFunction() {
            @Override
            public void doCallBack(SQLExpr expr) {
                if(expr instanceof SQLIdentifierExpr) {
                    selectItemAliases.add(((SQLIdentifierExpr) expr).getName());
                }
            }
        };
        for(SQLSelectItem item:block.getSelectList()) {
            if(!StringUtils.isEmpty(item.getAlias())) {
                selectItemAliases.add(item.getAlias());
            } else {
                getSelectItemExpr(item.getExpr(),sqlExprFunction);
            }
        }
        return selectItemAliases.stream().filter(Objects::nonNull).distinct().collect(Collectors.toList());
    }

    public static String getSelectItemAlias(SQLSelectItem selectItem) {
        if(!StringUtils.isEmpty(selectItem.getAlias())) {
            return selectItem.getAlias();
        }
        List<String> selectItemAliases = new ArrayList<>();
        SQLExprFunction sqlExprFunction = new SQLExprFunction() {
            @Override
            public void doCallBack(SQLExpr expr) {
                if(expr instanceof SQLIdentifierExpr) {
                    selectItemAliases.add(((SQLIdentifierExpr) expr).getName());
                }
            }
        };
        getSelectItemExpr(selectItem.getExpr(),sqlExprFunction);
        return selectItemAliases.get(0);
    }

    public static void getSelectItemExpr(SQLExpr sqlExpr,SQLExprFunction callBackFunc) {
        if(sqlExpr instanceof SQLBinaryOpExpr) {
            SQLBinaryOpExpr sqlBinaryOpExpr = (SQLBinaryOpExpr) sqlExpr;
            getSelectItemExpr(sqlBinaryOpExpr.getLeft(),callBackFunc);
            getSelectItemExpr(sqlBinaryOpExpr.getRight(),callBackFunc);
            return;
        }
        if(sqlExpr instanceof SQLAggregateExpr) {
            SQLAggregateExpr sqlAggregateExpr = (SQLAggregateExpr) sqlExpr;
            List<SQLExpr> arguments = sqlAggregateExpr.getArguments();
            for(SQLExpr argument:arguments) {
                getSelectItemExpr(argument,callBackFunc);
            }
            return;
        }
        if(sqlExpr instanceof SQLCaseExpr) {
            SQLCaseExpr sqlCaseExpr = (SQLCaseExpr) sqlExpr;
            List<SQLCaseExpr.Item> items = sqlCaseExpr.getItems();
            for(SQLCaseExpr.Item item:items) {
                getSelectItemExpr(item.getConditionExpr(),callBackFunc);
            }
            return;
        }
        if(sqlExpr instanceof SQLCastExpr) {
            SQLCastExpr sqlCastExpr = (SQLCastExpr) sqlExpr;
            SQLExpr castExpr = sqlCastExpr.getExpr();
            getSelectItemExpr(castExpr,callBackFunc);
            return;
        }
        if(sqlExpr instanceof SQLIntegerExpr) {
            return;
        }
        if(sqlExpr instanceof SQLCharExpr) {
            return;
        }
        if(sqlExpr instanceof SQLBigIntExpr) {
            return;
        }
        if(sqlExpr instanceof SQLBooleanExpr) {
            return;
        }
        if(sqlExpr instanceof SQLDateExpr) {
            return;
        }
        if(sqlExpr instanceof SQLDateTimeExpr) {
            return;
        }
        if(sqlExpr instanceof SQLDecimalExpr) {
            return;
        }
        if(sqlExpr instanceof SQLDoubleExpr) {
            return;
        }
        if(sqlExpr instanceof SQLFloatExpr) {
            return;
        }
        if(sqlExpr instanceof SQLJSONExpr) {
            return;
        }
        if(sqlExpr instanceof SQLNumberExpr) {
            return;
        }
        if(sqlExpr instanceof SQLSmallIntExpr) {
            return;
        }
        if(sqlExpr instanceof SQLTimeExpr) {
            return;
        }
        if(sqlExpr instanceof SQLTimestampExpr) {
            return;
        }
        if(sqlExpr instanceof SQLTinyIntExpr) {
            return;
        }
        if(sqlExpr instanceof SQLValuesExpr) {
            return;
        }
        if(sqlExpr instanceof SQLIdentifierExpr) {
            callBackFunc.doCallBack(sqlExpr);
            return;
        }
        throw new SQLExprNotSupportException("SQL expr type not support:"+sqlExpr.getClass().getName());
    }

    public interface SQLExprFunction {
        void doCallBack(SQLExpr expr);
    }

    public static Map<String,String> getTblPropertyValue(List<SQLAssignItem> items) {
        Map<String,String> map = new HashMap<>();
        for(SQLAssignItem item:items) {
            String key = ((SQLIdentifierExpr)item.getTarget()).getName();
            String value = ((SQLCharExpr)item.getValue()).getText();
            map.put(key,value);
        }
        return map;
    }

    public static SQLExpr getMatchedCondition(SQLExpr expr, List<String> allowDims) {
        if(expr instanceof SQLBinaryOpExpr && ((SQLBinaryOpExpr) expr).getOperator().isLogical()) {
            SQLBinaryOpExpr binaryOpExpr = (SQLBinaryOpExpr) expr;
            SQLExpr left = binaryOpExpr.getLeft();
            left = getMatchedCondition(left,allowDims);
            binaryOpExpr.setLeft(left);

            SQLExpr right = binaryOpExpr.getRight();
            right = getMatchedCondition(right,allowDims);
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
            left = getMatchedCondition(left,allowDims);
            if(left == null) {
                return null;
            }
            return expr;
        }
        if(expr instanceof SQLBetweenExpr) {
            SQLBetweenExpr sqlBetweenExpr = (SQLBetweenExpr) expr;
            SQLExpr left = getMatchedCondition(sqlBetweenExpr.getTestExpr(),allowDims);
            if(left == null) {
                return null;
            }
            return expr;
        }
        if(expr instanceof SQLCaseExpr) {
            SQLCaseExpr sqlCaseExpr = (SQLCaseExpr) expr;
            SQLExpr left = getMatchedCondition(sqlCaseExpr,allowDims);
            if(left == null) {
                return null;
            }
            return expr;
        }
        if(expr instanceof SQLInListExpr) {
            SQLInListExpr sqlInListExpr = (SQLInListExpr) expr;
            SQLExpr leftExpr = getMatchedCondition(sqlInListExpr.getExpr(),allowDims);
            if(leftExpr == null) {
                return null;
            }
            return expr;
        }
        if(expr instanceof SQLNotExpr) {
            SQLNotExpr sqlNotExpr = (SQLNotExpr) expr;
            SQLExpr leftExpr = getMatchedCondition(sqlNotExpr.getExpr(),allowDims);
            if(leftExpr == null) {
                return null;
            }
            return expr;
        }
        if(expr instanceof SQLCastExpr) {
            SQLCastExpr sqlCastExpr = (SQLCastExpr) expr;
            SQLExpr left = getMatchedCondition(sqlCastExpr.getExpr(),allowDims);
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
