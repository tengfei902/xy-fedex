package com.xy.fedex.facade.service.meta.query;

import com.xy.fedex.facade.service.meta.dto.PhysicalQueryPlan;
import com.xy.fedex.facade.service.meta.dto.QueryMatchedModelDTO;

/**
 * @author tengfei
 */
public interface ModelQueryService {
    PhysicalQueryPlan getPhysicalQuery(QueryMatchedModelDTO metricModelMatchDTO);
}
