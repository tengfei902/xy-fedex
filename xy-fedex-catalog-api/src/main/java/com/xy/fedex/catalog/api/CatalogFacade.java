package com.xy.fedex.catalog.api;

import com.xy.fedex.catalog.api.dto.DimModelRequest;
import com.xy.fedex.catalog.api.dto.MetricModelRequest;
import com.xy.fedex.catalog.api.dto.ModelRequest;
import com.xy.fedex.catalog.api.dto.request.PrepareModelRequest;
import com.xy.fedex.catalog.api.dto.response.PrepareModelResponse;
import com.xy.fedex.catalog.common.definition.ModelDefinition;
import com.xy.fedex.catalog.common.definition.field.Dim;
import com.xy.fedex.catalog.common.definition.field.Metric;
import com.xy.fedex.def.Response;

public interface CatalogFacade {
    /**
     * 获取模型结构
     * @return
     */
    Response<ModelRequest> getModelRequest(PrepareModelRequest prepareModelRequest);

    Response<PrepareModelResponse> getPrepareModel(PrepareModelRequest prepareModelRequest);

    /**
     * 保存模型
     * @param modelRequest
     * @return
     */
    Response<Long> saveModel(ModelRequest modelRequest);

    Response<ModelDefinition> createModel(String select);

    Response<Metric.MetricModel> createMetricModel(MetricModelRequest metricModelRequest);

    Response<Dim.DimModel> createDimModelRequest(DimModelRequest dimModelRequest);
}
