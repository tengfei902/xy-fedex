package com.xy.fedex.admin.service.datasource;

import com.xy.fedex.admin.constants.Constants;
import com.xy.fedex.admin.exception.DataSourceConnectFailedException;
import com.xy.fedex.admin.exception.DataSourceNotSupportException;
import com.xy.infrustruction.definition.DbType;
import com.xy.infrustruction.definition.connection.ConnectInfo;
import com.xy.infrustruction.definition.connection.impl.RdbmsConnectInfo;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;

@Slf4j
public class ConnectPreCheck {

    public static void preCheck(DbType dbType, ConnectInfo connectInfo) {
        switch (dbType) {
            case MYSQL:
                preCheckForMySQL(connectInfo);
                return;
            case ORACLE:
                preCheckForOracle(connectInfo);
                return;
            case SQLSERVER:
                preCheckForSqlServer(connectInfo);
                return;
            case ES:
                preCheckForEs(connectInfo);
                return;
            case TIDB:
                preCheckForTiDB(connectInfo);
                return;
            case DORIS:
                preCheckForDoris(connectInfo);
                return;
            case HIVE:
                preCheckForHive(connectInfo);
                return;
        }
        throw new DataSourceNotSupportException("DataSource not support:"+dbType.name());
    }

    private static void preCheckForMySQL(ConnectInfo connectInfo) {
        RdbmsConnectInfo mySqlConnectInfo = (RdbmsConnectInfo) connectInfo;
        Connection connection;
        String url = Constants.MYSQL_URL.replace("{HOST}",mySqlConnectInfo.getHost())
                .replace("{PORT}",String.valueOf(mySqlConnectInfo.getPort()))
                .replace("{SCHEMA}",mySqlConnectInfo.getSchema());
        try {
            Class.forName(Constants.MYSQL_DRIVER_CLASS);
            connection = DriverManager.getConnection(url,mySqlConnectInfo.getUsername(),mySqlConnectInfo.getPassword());
            if(!connection.isClosed()) {
                log.info("dataSource connect success");
            }
            connection.close();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new DataSourceConnectFailedException(String.format("dataSource test failed,%s,cause by:%s",connectInfo.toString(),e.getMessage()));
        }
    }

    private static boolean preCheckForOracle(ConnectInfo connectInfo) {
        return false;
    }

    private static boolean preCheckForSqlServer(ConnectInfo connectInfo) {
        return false;
    }

    private static boolean preCheckForEs(ConnectInfo connectInfo) {
        return false;
    }

    private static boolean preCheckForTiDB(ConnectInfo connectInfo) {
        return false;
    }

    private static boolean preCheckForDoris(ConnectInfo connectInfo) {
        return false;
    }

    private static boolean preCheckForHive(ConnectInfo connectInfo) {
        return false;
    }
}
