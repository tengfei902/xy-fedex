package com.xy.fedex.facade.service.plan;

import com.alibaba.druid.sql.ast.statement.SQLSelect;
import com.xy.fedex.facade.service.plan.dto.QueryPlan;

public interface QueryPlanService {
    QueryPlan getQueryPlan(SQLSelect logicalSelect);
}
