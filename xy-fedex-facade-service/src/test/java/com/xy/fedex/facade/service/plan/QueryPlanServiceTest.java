package com.xy.fedex.facade.service.plan;

import com.xy.fedex.facade.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class QueryPlanServiceTest extends BaseTest {
    @Autowired
    private QueryPlanService queryPlanService;

    @Test
    public void testSQLExprTableSource() {
        queryPlanService.getQueryPlan(null);
    }

    @Test
    public void testSQLJoinTableSource() {
        queryPlanService.getQueryPlan(null);
    }

    @Test
    public void testSubQueryTableSource() {
        queryPlanService.getQueryPlan(null);
    }
}
