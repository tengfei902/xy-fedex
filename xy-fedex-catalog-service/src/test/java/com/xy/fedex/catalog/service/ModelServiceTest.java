package com.xy.fedex.catalog.service;

import com.xy.fedex.catalog.BaseTest;
import com.xy.fedex.catalog.api.dto.DimModelRequest;
import com.xy.fedex.catalog.api.dto.MetricModelRequest;
import com.xy.fedex.catalog.api.dto.ModelRequest;
import com.xy.fedex.catalog.common.definition.dim.Dim;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

public class ModelServiceTest extends BaseTest {
    @Autowired
    private ModelService modelService;

    @Test
    public void testCreateModelByRequest() {
        Long tenantId = 0L;
        Long bizLineId = 0L;
        Long dsnId = 0L;

        ModelRequest modelRequest = new ModelRequest();
        modelRequest.setTenantId(tenantId);
        modelRequest.setBizLineId(bizLineId);
        modelRequest.setDsnId(dsnId);
        modelRequest.setModelName("test");
        modelRequest.setModelDesc("test");

        MetricModelRequest metricModelRequest = new MetricModelRequest();
        metricModelRequest.setMetricId(11L);
        metricModelRequest.setFormula("count(*)");
//        metricModelRequest.setAllowDims();
//        metricModelRequest.setForceDims();

        modelRequest.setMetricModels(Arrays.asList(metricModelRequest));

        DimModelRequest dimModelRequest = new DimModelRequest();
        dimModelRequest.setDimModelId(10L);
        dimModelRequest.setFormula("DATE_FORMAT(t1.create_time,'%Y%m%d')");

        DimModelRequest hourDim = new DimModelRequest();
        hourDim.setDimModelId(11L);
        hourDim.setFormula("DATE_FORMAT(t1.create_time,'%Y%m%d%h')");

        DimModelRequest providerCodeDim = new DimModelRequest();
        providerCodeDim.setDimModelId(16L);
        providerCodeDim.setFormula("t1.channel_provider_code");

        DimModelRequest providerNameDim = new DimModelRequest();
        providerNameDim.setDimModelId(16L);
        providerNameDim.setFormula("t2.provider_name");

        modelRequest.setDimModels(Arrays.asList(dimModelRequest,hourDim,providerCodeDim,providerNameDim));
        modelRequest.setTableSource("pay_request t1 left join channel_provider t2 on t1.channel_provider_code = t2.provider_code");
//        modelRequest.setCondition("");
//        modelRequest.setModelProp();
        modelService.createModel(modelRequest);
    }

    @Test
    public void testCreateModelBySql() {

    }
}
