package com.xy.fedex.facade.service.plan;

import com.alibaba.druid.sql.ast.statement.SQLSelect;
import com.xy.fedex.facade.service.plan.dto.LogicalPlan;
import com.xy.fedex.facade.service.plan.dto.PhysicalPlan;

public interface QueryPlanService {
    LogicalPlan getLogicalPlan(SQLSelect logicalSelect);

    PhysicalPlan getPhysicalPlan(SQLSelect logicalSelect);
}
