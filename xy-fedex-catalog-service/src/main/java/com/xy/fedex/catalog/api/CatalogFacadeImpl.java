package com.xy.fedex.catalog.api;

import com.xy.fedex.catalog.api.dto.DimModelRequest;
import com.xy.fedex.catalog.api.dto.MetricModelRequest;
import com.xy.fedex.catalog.api.dto.ModelRequest;
import com.xy.fedex.catalog.common.definition.ModelDefinition;
import com.xy.fedex.catalog.common.definition.dim.Dim;
import com.xy.fedex.catalog.common.definition.metric.Metric;
import com.xy.fedex.catalog.service.ModelService;
import com.xy.fedex.def.Response;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

@DubboService(version = "${dubbo.server.version}")
public class CatalogFacadeImpl implements CatalogFacade {
    @Autowired
    private ModelService modelService;

    @Override
    public Response<ModelDefinition> createModel(ModelRequest modelRequest) {
        return null;
    }

    @Override
    public Response<ModelDefinition> createModel(String select) {
        return null;
    }

    @Override
    public Response<Metric.MetricModel> createMetricModel(MetricModelRequest metricModelRequest) {
        return null;
    }

    @Override
    public Response<Dim.DimModel> createDimModelRequest(DimModelRequest dimModelRequest) {
        return null;
    }
}
