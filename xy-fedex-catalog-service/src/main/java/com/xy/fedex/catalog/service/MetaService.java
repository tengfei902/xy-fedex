package com.xy.fedex.catalog.service;

import com.xy.fedex.catalog.dto.DimDTO;
import com.xy.fedex.catalog.dto.MetricDTO;
import com.xy.fedex.catalog.dto.TableAliasDTO;

import java.util.List;

public interface MetaService {
    MetricDTO getMetric(Long metricId);

    DimDTO getDim(Long dimId);

    Long saveMetric(MetricDTO metric);

    Long saveDim(DimDTO dim);

    void matchMetricAndDim(List<TableAliasDTO> tableAliasList);
}
