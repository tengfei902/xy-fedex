package com.xy.fedex.facade.service.meta.plan;

import com.xy.fedex.facade.service.meta.dto.PhysicalQueryPlan;
import com.xy.fedex.facade.service.meta.dto.QueryMatchedModelDTO;

public interface ModelPlanService {

    PhysicalQueryPlan getPhysicalQueryPlan(QueryMatchedModelDTO queryMatchedModel);
}
