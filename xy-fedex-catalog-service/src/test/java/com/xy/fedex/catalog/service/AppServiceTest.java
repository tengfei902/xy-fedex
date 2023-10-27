package com.xy.fedex.catalog.service;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.xy.fedex.catalog.BaseTest;
import com.xy.fedex.catalog.common.definition.AppDefinition;
import com.xy.fedex.catalog.common.definition.field.Dim;
import com.xy.fedex.catalog.common.definition.field.Metric;
import com.xy.fedex.catalog.constants.Constants;
import com.xy.fedex.catalog.dto.ModelDTO;
import com.xy.fedex.catalog.dto.request.AppRequest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AppServiceTest extends BaseTest {
    @Autowired
    private AppService appService;
    @Autowired
    private DimService dimService;
    @Autowired
    private MetricService metricService;
    @Autowired
    private ModelService modelService;

    @Test
    public void testSaveApp() {
        String appCode = "test";
        String appName = "test_show_name";
        String comment = "test app show name";

        List<String> dimCodes = Arrays.asList("dt","hour","group_id","tenant_id");
        initDims(dimCodes);

        List<String> metricCodes = Arrays.asList("order_cnt","fee","amount");
        initMetrics(metricCodes);
        //dims
        List<Dim> dims = dimService.getDims(dimCodes);
        //metrics
        List<Metric> metrics = metricService.getMetrics(Arrays.asList("order_cnt","fee","amount"));

        AppRequest appRequest = AppRequest.builder().appCode(appCode).appName(appName).appComment(comment)
                .dims(dims)
                .metrics(metrics)
                .build();
        Long appId = appService.saveApp(appRequest);

        AppDefinition appDefinition = appService.getApp(appId);
        System.out.println(new Gson().toJson(appDefinition));
    }

    private void initDims(List<String> dimCodes) {
        dimService.saveDims(dimCodes.stream().map(s -> {
            Dim dim = new Dim();
            dim.setDimCode(s);
            return dim;
        }).collect(Collectors.toList()));
    }

    private void initMetrics(List<String> metricCodes) {
        metricService.saveMetrics(metricCodes.stream().map(s -> {
            Metric metric = new Metric();
            metric.setMetricCode(s);
            return metric;
        }).collect(Collectors.toList()));
    }

    private void initModels(List<String> modelCodes) {
        for(String modelCode:modelCodes) {
            ModelDTO modelDTO = new ModelDTO();
            modelDTO.setModelCode(modelCode);
//            modelDTO.setTableSource("select");
            modelDTO.setDsnCode("test_dsn_code");
            Map<String,String> modelParams = new HashMap<>();
//            modelParams.put(Constants.MODEL_FORCE_DIMS,"a,b,c");
            modelDTO.setModelParams(modelParams);
            modelService.saveModel(modelDTO);
        }
    }
}
