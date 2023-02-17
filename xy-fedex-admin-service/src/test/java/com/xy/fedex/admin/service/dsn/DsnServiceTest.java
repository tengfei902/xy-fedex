package com.xy.fedex.admin.service.dsn;

import com.google.gson.Gson;
import com.xy.fedex.admin.BaseTest;
import com.xy.fedex.admin.service.dsn.model.bo.DsnDTO;
import com.xy.infrustruction.definition.DbType;
import com.xy.infrustruction.definition.connection.impl.RdbmsConnectInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class DsnServiceTest extends BaseTest {
    @Autowired
    private DsnService dsnService;

    @Test
    public void testCreateDsn() {
        RdbmsConnectInfo rdbmsConnectInfo = RdbmsConnectInfo.builder().host("127.0.0.1").port(3306).username("root").password("123456").schema("test_mysql_1").build();
        DsnDTO dsnDTO = DsnDTO.builder().dsnName("test").dbType(DbType.MYSQL).creator("tengfei").connectInfo(rdbmsConnectInfo).build();
        DsnDTO dsn = dsnService.createDsn(dsnDTO);
        System.out.println(new Gson().toJson(dsn));

        dsn = dsnService.getDsn("test");
        System.out.println(new Gson().toJson(dsn));
    }
}
