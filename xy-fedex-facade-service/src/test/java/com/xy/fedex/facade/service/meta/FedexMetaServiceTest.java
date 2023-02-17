package com.xy.fedex.facade.service.meta;

import com.alibaba.druid.sql.ast.statement.SQLSelect;
import com.xy.fedex.dsl.utility.SQLExprUtils;
import com.xy.fedex.facade.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class FedexMetaServiceTest extends BaseTest {
    @Autowired
    private FedexMetaService fedexMetaService;

    @Test
    public void testGetQueryPlan() {

    }
}
