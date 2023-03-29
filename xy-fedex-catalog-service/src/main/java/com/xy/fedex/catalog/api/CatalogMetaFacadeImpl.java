package com.xy.fedex.catalog.api;

import com.xy.fedex.catalog.api.dto.request.GetDimModelRequest;
import com.xy.fedex.catalog.api.dto.request.GetMetricModelRequest;
import com.xy.fedex.catalog.common.definition.field.impl.DimModel;
import com.xy.fedex.catalog.common.definition.field.impl.MetricModel;
import com.xy.fedex.catalog.dto.DimModelRequest;
import com.xy.fedex.catalog.dto.MetricModelRequest;
import com.xy.fedex.catalog.service.meta.MetaService;
import com.xy.fedex.def.Response;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@DubboService(version = "${dubbo.server.version}")
public class CatalogMetaFacadeImpl implements CatalogMetaFacade {
    @Autowired
    private MetaService metaService;

    @Override
    public Response<List<MetricModel>> getMetricModels(GetMetricModelRequest request) {
        List<MetricModel> metricModels = metaService.getMetricModels(MetricModelRequest.builder().appId(request.getAppId()).metricId(request.getMetricId()).build());
        return Response.success(metricModels);
    }

    @Override
    public Response<MetricModel> getMetricModel(GetMetricModelRequest request) {
        MetricModel metricModel = metaService.getMetricModel(MetricModelRequest.builder().appId(request.getAppId()).modelId(request.getModelId()).metricId(request.getMetricId()).build());
        return Response.success(metricModel);
    }

    @Override
    public Response<List<DimModel>> getDimModels(GetDimModelRequest request) {
        List<DimModel> dimModels = metaService.getDimModels(DimModelRequest.builder().appId(request.getAppId()).dimId(request.getDimId()).build());
        return Response.success(dimModels);
    }

    @Override
    public DimModel getDimModel(GetDimModelRequest request) {
        return null;
    }
}
