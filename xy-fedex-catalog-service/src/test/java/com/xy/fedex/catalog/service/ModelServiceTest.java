package com.xy.fedex.catalog.service;

import com.xy.fedex.catalog.BaseTest;
import com.xy.fedex.catalog.api.dto.DimModelRequest;
import com.xy.fedex.catalog.api.dto.MetricModelRequest;
import com.xy.fedex.catalog.api.dto.ModelRequest;
import com.xy.fedex.catalog.service.meta.ModelService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

public class ModelServiceTest extends BaseTest {
    @Autowired
    private ModelService modelService;

    @Transactional
    @Rollback(value = false)
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
        metricModelRequest.setFormula("count(t1.out_trade_no)");

        //fee
        MetricModelRequest feeModelRequest = new MetricModelRequest();
        feeModelRequest.setMetricId(12L);
        feeModelRequest.setFormula("sum(t1.fee)");

        MetricModelRequest totalFeeModelRequest = new MetricModelRequest();
        totalFeeModelRequest.setMetricId(13L);
        totalFeeModelRequest.setFormula("sum(t1.total_fee)");

        MetricModelRequest actualAmountModelRequest = new MetricModelRequest();
        actualAmountModelRequest.setMetricId(14L);
        actualAmountModelRequest.setFormula("sum(t1.actual_amount)");

        MetricModelRequest amountModelRequest = new MetricModelRequest();
        amountModelRequest.setMetricId(15L);
        amountModelRequest.setFormula("sum(t1.amount)");
        //metrics
        modelRequest.setMetricModels(Arrays.asList(metricModelRequest,feeModelRequest,totalFeeModelRequest,actualAmountModelRequest,amountModelRequest));

        DimModelRequest dimModelRequest = new DimModelRequest();
        dimModelRequest.setDimId(10L);
        dimModelRequest.setFormula("DATE_FORMAT(t1.create_time,'%Y%m%d')");

        DimModelRequest hourDim = new DimModelRequest();
        hourDim.setDimId(11L);
        hourDim.setFormula("DATE_FORMAT(t1.create_time,'%Y%m%d%h')");

        DimModelRequest providerCodeDim = new DimModelRequest();
        providerCodeDim.setDimId(16L);
        providerCodeDim.setFormula("t1.channel_provider_code");

        DimModelRequest providerNameDim = new DimModelRequest();
        providerNameDim.setDimId(17L);
        providerNameDim.setFormula("t2.provider_name");

        DimModelRequest groupIdDim = new DimModelRequest();
        groupIdDim.setDimId(12L);
        groupIdDim.setFormula("t1.group_id");

        DimModelRequest accountStatusDim = new DimModelRequest();
        accountStatusDim.setDimId(13L);
        accountStatusDim.setFormula("t1.account_status");

        DimModelRequest outTradeNoDim = new DimModelRequest();
        outTradeNoDim.setDimId(14L);
        outTradeNoDim.setFormula("t1.out_trade_no");

        DimModelRequest oprTypeDim = new DimModelRequest();
        oprTypeDim.setDimId(15L);
        oprTypeDim.setFormula("t1.opr_type");

        DimModelRequest mchDim = new DimModelRequest();
        mchDim.setDimId(18L);
        mchDim.setFormula("t1.mch_id");

        DimModelRequest serviceDim = new DimModelRequest();
        serviceDim.setDimId(19L);
        serviceDim.setFormula("t1.service");

        DimModelRequest tradeTypeDim = new DimModelRequest();
        tradeTypeDim.setDimId(20L);
        tradeTypeDim.setFormula("t1.trade_type");

        DimModelRequest payResultDim = new DimModelRequest();
        payResultDim.setDimId(21L);
        payResultDim.setFormula("t1.pay_result");

        modelRequest.setDimModels(Arrays.asList(dimModelRequest,hourDim,providerCodeDim,providerNameDim,groupIdDim,accountStatusDim,outTradeNoDim,oprTypeDim,mchDim,serviceDim,tradeTypeDim,payResultDim));
        modelRequest.setTableSource("pay_request t1 left join channel_provider t2 on t1.channel_provider_code = t2.provider_code");
        Long modelId = modelService.saveModel(modelRequest);

        System.out.println(modelId);
    }

    @Test
    public void testCreateModelBySql() {

    }
}
