package com.xy.fedex.catalog.api;

import com.xy.fedex.catalog.api.dto.request.GetDimModelRequest;
import com.xy.fedex.catalog.api.dto.request.GetMetricModelRequest;
import com.xy.fedex.catalog.common.definition.field.impl.DimModel;
import com.xy.fedex.catalog.common.definition.field.impl.MetricModel;

import java.util.List;

public interface CatalogMetaFacade {
    List<MetricModel> getMetricModels(GetMetricModelRequest request);

    MetricModel getMetricModel(GetMetricModelRequest request);

    List<DimModel> getDimModels(GetDimModelRequest request);

    DimModel getDimModel(GetDimModelRequest request);
}
