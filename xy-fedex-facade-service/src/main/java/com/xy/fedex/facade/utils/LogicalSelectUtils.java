package com.xy.fedex.facade.utils;

import com.alibaba.druid.sql.ast.statement.SQLSelect;
import com.alibaba.druid.sql.ast.statement.SQLTableSource;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlSelectQueryBlock;

public class LogicalSelectUtils {

    public static SQLTableSource getTableSource(SQLSelect sqlSelect) {
        MySqlSelectQueryBlock mySqlSelectQueryBlock = (MySqlSelectQueryBlock) sqlSelect.getQueryBlock();
        return mySqlSelectQueryBlock.getFrom();
    }
}
