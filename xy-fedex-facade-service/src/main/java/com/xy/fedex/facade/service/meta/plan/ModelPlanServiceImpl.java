package com.xy.fedex.facade.service.meta.plan;

import com.alibaba.druid.sql.ast.statement.SQLSelect;
import com.xy.fedex.facade.service.meta.dto.PhysicalQueryPlan;
import com.xy.fedex.facade.service.meta.dto.QueryMatchedModelDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModelPlanServiceImpl implements ModelPlanService {
    @Override
    public PhysicalQueryPlan getPhysicalQueryPlan(QueryMatchedModelDTO queryMatchedModel) {
        SQLSelect logicalSelect = queryMatchedModel.getLogicalSelect();
        List<QueryMatchedModelDTO.MetricMatchedModelDTO> metricMatchedModels = queryMatchedModel.getMetricMatchedModelList();

        return null;
    }
}
