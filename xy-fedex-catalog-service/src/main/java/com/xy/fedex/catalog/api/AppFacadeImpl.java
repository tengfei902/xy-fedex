package com.xy.fedex.catalog.api;

import com.xy.fedex.catalog.api.dto.request.GetAppRequest;
import com.xy.fedex.catalog.api.dto.request.SaveAppRequest;
import com.xy.fedex.catalog.api.dto.request.save.field.metric.SaveDeriveMetricModelRequest;
import com.xy.fedex.catalog.api.dto.response.Page;
import com.xy.fedex.catalog.common.definition.AppDefinition;
import com.xy.fedex.catalog.common.definition.field.Dim;
import com.xy.fedex.catalog.common.definition.field.Metric;
import com.xy.fedex.catalog.dto.ModelDTO;
import com.xy.fedex.catalog.dto.request.AppRequest;
import com.xy.fedex.catalog.service.AppService;
import com.xy.fedex.catalog.service.DimService;
import com.xy.fedex.catalog.service.MetricService;
import com.xy.fedex.catalog.service.ModelService;
import com.xy.fedex.def.Response;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


/**
 * @author tengfei
 */
@Component
@DubboService(version = "${dubbo.server.version}")
public class AppFacadeImpl implements AppFacade {
    @Autowired
    private AppService appService;
    @Autowired
    private MetricService metricService;
    @Autowired
    private DimService dimService;
    @Autowired
    private ModelService modelService;


    @Override
    public Response<AppDefinition> getApp(GetAppRequest getAppRequest) {
        return null;
    }

    @Override
    public Response<Page<AppDefinition>> getAppList(GetAppRequest getAppRequest) {
        return null;
    }

    @Override
    public Response<Boolean> saveApp(SaveAppRequest saveAppRequest) {
        //metrics
        List<SaveAppRequest.Metric> inputMetrics = saveAppRequest.getMetrics();
        List<Metric> metrics = metricService.getMetrics(inputMetrics.stream().map(SaveAppRequest.Metric::getMetricCode).collect(Collectors.toList()));
        //dims
        List<SaveAppRequest.Dim> inputDims = saveAppRequest.getDims();
        List<Dim> dims = dimService.getDims(inputDims.stream().map(SaveAppRequest.Dim::getDimCode).collect(Collectors.toList()));
        //models
        List<String> modelNames = saveAppRequest.getRelateModels();
//        List<ModelDTO> models = modelService.getModels(modelNames);
        //save app
//        AppRequest appRequest = AppRequest.builder().appName(saveAppRequest.getAppName()).appComment(saveAppRequest.getAppComment()).metrics(metrics).dims(dims).models(models).build();
//        appService.saveApp(appRequest);
        return Response.success(true);
    }

    @Override
    public Response<Boolean> saveDeriveMetricModel(SaveDeriveMetricModelRequest saveDeriveMetricModelRequest) {
        return null;
    }
}
