package com.xy.fedex.catalog.api;

import com.xy.fedex.catalog.api.dto.request.GetDimModelRequest;
import com.xy.fedex.catalog.api.dto.request.GetMetricModelRequest;
import com.xy.fedex.catalog.common.definition.field.impl.DimModel;
import com.xy.fedex.catalog.common.definition.field.impl.MetricModel;
import com.xy.fedex.def.Response;

import java.util.List;

public interface CatalogMetaFacade {
    Response<List<MetricModel>> getMetricModels(GetMetricModelRequest request);

    Response<MetricModel> getMetricModel(GetMetricModelRequest request);

    Response<List<DimModel>> getDimModels(GetDimModelRequest request);

    DimModel getDimModel(GetDimModelRequest request);
}
