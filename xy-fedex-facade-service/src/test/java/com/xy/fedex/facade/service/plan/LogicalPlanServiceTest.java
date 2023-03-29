package com.xy.fedex.facade.service.plan;

import com.xy.fedex.dsl.utility.SQLExprUtils;
import com.xy.fedex.facade.BaseTest;
import com.xy.fedex.facade.service.plan.dto.LogicalPlan;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class LogicalPlanServiceTest extends BaseTest {
    @Autowired
    private QueryPlanService queryPlanService;

    @Test
    public void testSQLExprTableSource() {
        String sql = "select m1,m2,m3,d1,d2,d3 from t where d4 = 10 and dt between 20230101 and 20230202 group by d1,d2,d3";
        LogicalPlan logicalPlan = queryPlanService.getLogicalPlan(SQLExprUtils.parse(sql));
        List<LogicalPlan.Node> nodes = logicalPlan.lrd();
        System.out.println("------");
    }

    @Test
    public void testSQLJoinTableSource() {
        String sql = "select t2.a - t1.last_a as a_increase,t2.b-t1.b as b_increase,t2.a,t2.b,t1.last_a as last_a,t1.last_b as last_b,t1.d1,t1.d2 from (select a as last_a,b as last_b,d1,d2,dt from t where dt between 20220101 and 20220202 and d1 = 10 and d2 = 100 group by t.d1,t.d2) t1 full join (select a as a,b as b,d1,d2,dt from t where dt between 20220202 and 20220303 and d1 = 10 and d2 = 100 group by t.d1,t.d2) t2 on t1.d1 = t2.d1 and t1.d2 = t2.d2 and t1.dt = t2.dt";
        LogicalPlan logicalPlan = queryPlanService.getLogicalPlan(SQLExprUtils.parse(sql));
        List<LogicalPlan.Node> nodes = logicalPlan.lrd();
        System.out.println("------");
    }

    @Test
    public void testSubQueryTableSource() {
        String sql = "select m1,m2,d1,d2 from (select m1,m2,d1,d2 from t where dt between 20220101 and 20230101 group by d1,d2) t2";
        LogicalPlan logicalPlan = queryPlanService.getLogicalPlan(SQLExprUtils.parse(sql));
        List<LogicalPlan.Node> nodes = logicalPlan.lrd();
        System.out.println("-------");
    }
}
