package com.xy.fedex.facade.service;

import com.alibaba.druid.sql.ast.statement.SQLSelect;
import com.xy.fedex.dsl.utility.SQLExprUtils;
import com.xy.fedex.facade.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class FedexQueryServiceTest extends BaseTest {
    @Autowired
    private FedexQueryService fedexQueryService;

    @Test
    public void testQuery() {
        String sql = "select a,b,c from 10000";
        SQLSelect sqlSelect = SQLExprUtils.parse(sql);
        fedexQueryService.query(sqlSelect);
    }
}
