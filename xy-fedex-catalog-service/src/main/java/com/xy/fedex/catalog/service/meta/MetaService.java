package com.xy.fedex.catalog.service.meta;

import com.xy.fedex.catalog.api.dto.request.list.ListMetricModelRequest;
import com.xy.fedex.catalog.common.definition.column.TableField;
import com.xy.fedex.catalog.common.definition.field.impl.DimModel;
import com.xy.fedex.catalog.common.definition.field.impl.MetricModel;
import com.xy.fedex.catalog.dto.*;

import java.util.List;

public interface MetaService {
    MetricDTO getMetric(Long metricId);

    MetricDTO getMetric(Long bizLineId,String metricCode);

    List<MetricDTO> getMetrics(Long bizLineId);

    DimDTO getDim(Long dimId);

    DimDTO getDim(Long bizLineId,String dimCode);
    List<DimDTO> getDims(Long bizLineId);

    Long saveMetric(MetricDTO metric);

    Long saveDim(DimDTO dim);

    void matchMetricAndDim(List<TableAliasDTO> tableAliasList);

    List<TableField> matchMeta(Long bizLineId,List<TableField> tableFields);

    List<MetricModel> getMetricModels(ListMetricModelRequest listMetricModelRequest);

    MetricModel getMetricModel(MetricModelRequest metricModelRequest);

    List<DimModel> getDimModels(DimModelRequest dimModelRequest);
}
