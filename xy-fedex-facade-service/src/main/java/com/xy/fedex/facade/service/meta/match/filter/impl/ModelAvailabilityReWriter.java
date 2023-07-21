package com.xy.fedex.facade.service.meta.match.filter.impl;

import com.xy.fedex.catalog.common.definition.field.impl.MetricModel;
import com.xy.fedex.facade.service.meta.dto.QueryMatchedModelDTO;
import com.xy.fedex.facade.service.meta.match.filter.AbstractMetricModelReWriter;
import com.xy.fedex.facade.service.meta.match.filter.MetricModelReWriter;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 模型可用性验证
 */
@Component
public class ModelAvailabilityReWriter extends AbstractMetricModelReWriter {
    @Override
    public List<QueryMatchedModelDTO.MetricModel> getMatchedMetricModels(QueryMatchedModelDTO.SelectFields fields, List<QueryMatchedModelDTO.MetricModel> matchedMetricModels) {
        return null;
    }
}
