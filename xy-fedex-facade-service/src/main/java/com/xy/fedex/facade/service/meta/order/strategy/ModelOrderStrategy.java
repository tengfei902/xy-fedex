package com.xy.fedex.facade.service.meta.order.strategy;

import com.xy.fedex.facade.service.meta.dto.QueryMatchedModelDTO;

import java.util.List;

public interface ModelOrderStrategy {
    void sort(List<QueryMatchedModelDTO.MetricModel> metricModels);
}
