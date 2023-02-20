package com.xy.fedex.catalog.service;

import com.xy.fedex.catalog.dto.DimDTO;
import com.xy.fedex.catalog.dto.MetricDTO;

public interface MetaService {
    MetricDTO getMetric(Long metricId);

    DimDTO getDim(Long dimId);

    Long saveMetric(MetricDTO metric);

    Long saveDim(DimDTO dim);
}
