package com.xy.fedex.catalog.api;

import com.xy.fedex.catalog.api.dto.DimModelRequest;
import com.xy.fedex.catalog.api.dto.MetricModelRequest;
import com.xy.fedex.catalog.api.dto.ModelRequest;
import com.xy.fedex.catalog.common.definition.ModelDefinition;
import com.xy.fedex.catalog.common.definition.dim.Dim;
import com.xy.fedex.catalog.common.definition.metric.Metric;
import com.xy.fedex.def.Response;

public interface CatalogFacade {

    Response<ModelDefinition> createModel(ModelRequest modelRequest);

    Response<ModelDefinition> createModel(String select);

    Response<Metric.MetricModel> createMetricModel(MetricModelRequest metricModelRequest);

    Response<Dim.DimModel> createDimModelRequest(DimModelRequest dimModelRequest);
}
