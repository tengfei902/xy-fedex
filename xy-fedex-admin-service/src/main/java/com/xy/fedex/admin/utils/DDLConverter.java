package com.xy.fedex.admin.utils;

import com.alibaba.druid.DbType;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlCreateTableStatement;
import com.xy.fedex.admin.api.vo.response.TableDetailVO;
import com.xy.fedex.admin.exception.DbTypeNotSupportException;
import com.xy.fedex.dsl.utility.SQLExprUtils;

public class DDLConverter {

    public static TableDetailVO convertDdlToTableDetail(String ddl,DbType dbType) {
        SQLStatement sqlStatement = SQLUtils.parseSingleStatement(ddl, dbType);
        return getTableConverter(dbType).getTableDetail(sqlStatement);
    }

    private static <T extends SQLStatement> TableConverter<T> getTableConverter(DbType dbType) {
        switch (dbType) {
            case mysql:
                break;
            case hive:
                break;
        }
        throw new DbTypeNotSupportException("dbType not support:"+dbType);
    }

    public interface TableConverter<T extends SQLStatement> {
        TableDetailVO getTableDetail(T statement);
    }

    public static class MySqlTableConverter implements TableConverter<MySqlCreateTableStatement> {
        @Override
        public TableDetailVO getTableDetail(MySqlCreateTableStatement statement) {
            return null;
        }
    }
}
