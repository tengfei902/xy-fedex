package com.xy.fedex.catalog.service.meta;

import com.xy.fedex.catalog.common.definition.column.TableField;
import com.xy.fedex.catalog.dto.DimDTO;
import com.xy.fedex.catalog.dto.MetricDTO;
import com.xy.fedex.catalog.dto.TableAliasDTO;

import java.util.List;

public interface MetaService {
    MetricDTO getMetric(Long metricId);

    MetricDTO getMetric(Long bizLineId,String metricCode);

    List<MetricDTO> getMetrics(Long bizLineId);

    DimDTO getDim(Long dimId);

    List<DimDTO> getDims(Long bizLineId);

    Long saveMetric(MetricDTO metric);

    Long saveDim(DimDTO dim);

    void matchMetricAndDim(List<TableAliasDTO> tableAliasList);

    List<TableField> matchMeta(Long bizLineId,List<TableField> tableFields);
}
