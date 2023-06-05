package com.xy.fedex.admin.service.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import com.xy.fedex.admin.exception.DbTypeNotSupportException;
import com.xy.fedex.admin.service.dsn.model.bo.DsnDTO;
import com.xy.inf.acl.ACL;
import com.xy.inf.acl.impl.MySqlAcl;
import com.xy.infrustruction.definition.connection.impl.RdbmsConnectInfo;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class AclFactory {
    private static ConcurrentHashMap<String, ACL> ACLS = new ConcurrentHashMap<>();

    private static AclFactory aclFactory;

    private AclFactory() {}

    public static AclFactory getInstance() {
        if(Objects.isNull(aclFactory)) {
            synchronized (AclFactory.class) {
                if(!Objects.isNull(aclFactory)) {
                    return aclFactory;
                }
            }
            aclFactory = new AclFactory();
        }
        return aclFactory;
    }

    public ACL getAcl(DsnDTO dsn) {
        return ACLS.putIfAbsent(dsn.getDsnName(),buildAcl(dsn));
    }

    private ACL buildAcl(DsnDTO dsn) {
//        switch (dsn.getDbType()) {
//            case MYSQL:
//            case ORACLE:
//            case DB2:
//            case SQLSERVER:
//            case TIDB:
//                RdbmsConnectInfo rdbmsConnectInfo = (RdbmsConnectInfo) dsn.getConnectInfo();
//                DruidDataSource druidDataSource = new DruidDataSource();
//                druidDataSource.setUsername(rdbmsConnectInfo.getUsername());
//                druidDataSource.setPassword(rdbmsConnectInfo.getPassword());
//                druidDataSource.setUrl(rdbmsConnectInfo.getHost());
//                druidDataSource.setMaxActive(10);
//                druidDataSource.setDefaultAutoCommit(true);
//
//                JdbcTemplate jdbcTemplate = new JdbcTemplate();
//                jdbcTemplate.setDataSource(druidDataSource);
//
//                MySqlAcl mySqlAcl = new MySqlAcl();
//                mySqlAcl.setJdbcTemplate(jdbcTemplate);
//
//                return mySqlAcl;
//            case ES:
//                break;
//            case HIVE:
//                break;
//            case HBASE:
//                break;
//            case TAIR:
//                break;
//            case REDIS:
//                break;
//            case DORIS:
//                break;
//            case DRUID:
//                break;
//            case KYLIN:
//                break;
//            case IGNITE:
//                break;
//            case CLICKHOUSE:
//                break;
//        }

        throw new DbTypeNotSupportException(String.format("db type:%s not support",dsn.getDbType().name()));
    }
}
