package com.xy.fedex.catalog.service;

import com.google.gson.Gson;
import com.xy.fedex.catalog.BaseTest;
import com.xy.fedex.catalog.api.dto.DimModelRequest;
import com.xy.fedex.catalog.api.dto.MetricModelRequest;
import com.xy.fedex.catalog.api.dto.ModelRequest;
import com.xy.fedex.catalog.common.definition.ModelDefinition;
import com.xy.fedex.catalog.dao.*;
import com.xy.fedex.catalog.po.*;
import com.xy.fedex.catalog.service.meta.ModelService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

public class ModelServiceTest extends BaseTest {
    @Autowired
    private ModelService modelService;
    @Autowired
    private ModelDao modelDao;
    @Autowired
    private ModelTableRelationDao modelTableRelationDao;
    @Autowired
    private MetricModelDao metricModelDao;
    @Autowired
    private DimModelDao dimModelDao;
    @Autowired
    private MetricDao metricDao;
    @Autowired
    private DimDao dimDao;

    @Test
    public void testSaveModel() {
        ModelRequest modelRequest = new ModelRequest();
        modelRequest.setBizLineId(0L);
        modelRequest.setModelName("支付订单");
        modelRequest.setModelComment("支付订单");
        modelRequest.setDsnId(0L);
        modelRequest.setTableSource("pay_request t1 left join channel_provider t2 on t1.channel_provider_code = t2.provider_code left join pay_request_back t3 on t1.out_trade_no = t3.out_trade_no");
        modelRequest.setCondition("t3.id is not null");
        modelRequest.setMetricModels(getMetrics());
        modelRequest.setDimModels(getDims());
        Long modelId = modelService.saveModel(modelRequest);

        Assert.assertNotNull(modelId);
        ModelPO modelPO = modelDao.selectByPrimaryKey(modelId);
        Assert.assertNotNull(modelPO);
        Assert.assertEquals(modelPO.getModelDesc(),modelRequest.getModelComment());
        Assert.assertEquals(modelPO.getBizLineId(),modelRequest.getBizLineId());
        Assert.assertEquals(modelPO.getDsnId(),modelRequest.getDsnId());
        Assert.assertEquals(modelPO.getModelName(),modelRequest.getModelName());
        Assert.assertEquals(modelPO.getTableSource(),modelRequest.getTableSource());
        Assert.assertEquals(modelPO.getCondition(),modelRequest.getCondition());
        //Assert.assertEquals(modelPO.getModelProp(),modelRequest.getModelProp());
        List<MetricModelPO> metricModels = metricModelDao.selectByModelId(modelId);
        Assert.assertTrue(metricModels.size() == 5);
        for(MetricModelPO metricModel:metricModels) {
            Assert.assertEquals(metricModel.getModelId(),modelId);
            Long metricId = metricModel.getMetricId();
            MetricPO metricPO = metricDao.selectByPrimaryKey(metricId);
            Assert.assertNotNull(metricPO);
            Assert.assertNotNull(metricModel.getMetricType());
        }
        List<DimModelPO> dimModels = dimModelDao.selectByModelId(modelId);
        Assert.assertTrue(dimModels.size() == 11);
        for(DimModelPO dimModel:dimModels) {
            Long dimId = dimModel.getDimId();
            DimPO dim = dimDao.selectByPrimaryKey(dimId);
            Assert.assertNotNull(dim);
            Assert.assertNotNull(dimModel.getFormula());
            Assert.assertEquals(dimModel.getModelId(),modelId);
        }
    }

    private List<MetricModelRequest> getMetrics() {
        MetricModelRequest fee = MetricModelRequest.builder().metricCode("fee").formula("sum(t1.fee)").build();
        MetricModelRequest actualAmount = MetricModelRequest.builder().metricCode("actual_amount").formula("sum(t1.actual_amount)").build();
        MetricModelRequest totalAmount = MetricModelRequest.builder().metricCode("amount").formula("sum(t1.total_fee)").build();
        MetricModelRequest feeRate = MetricModelRequest.builder().metricCode("fee_rate").formula("sum(t1.fee)/sum(t1.total_fee)").build();
        MetricModelRequest orderCnt = MetricModelRequest.builder().metricCode("order_cnt").formula("count(distinct t1.out_trade_no)").advanceCalculate(MetricModelRequest.AdvanceCalculate.builder().forceDims(Arrays.asList("out_trade_no")).build()).build();
        return Arrays.asList(fee,actualAmount,totalAmount,feeRate,orderCnt);
    }

    private List<DimModelRequest> getDims() {
        DimModelRequest outTradeNo = DimModelRequest.builder().dimCode("out_trade_no").formula("t1.out_trade_no").build();
        DimModelRequest mchId = DimModelRequest.builder().dimCode("mch_id").formula("t1.mch_id").build();
        DimModelRequest buyerId = DimModelRequest.builder().dimCode("buyer_id").formula("t1.buyer_id").build();
        DimModelRequest service = DimModelRequest.builder().dimCode("service").formula("t1.service").build();
        DimModelRequest appid = DimModelRequest.builder().dimCode("appid").formula("t1.appid").build();
        DimModelRequest status = DimModelRequest.builder().dimCode("status").formula("t1.status").build();
        DimModelRequest payResult = DimModelRequest.builder().dimCode("pay_result").formula("t1.pay_result").build();
        DimModelRequest tradeType = DimModelRequest.builder().dimCode("trade_type").formula("t1.trade_type").build();
        DimModelRequest channelProviderCode = DimModelRequest.builder().dimCode("channel_provider_code").formula("t1.channel_provider_code").build();
        DimModelRequest channelProviderName = DimModelRequest.builder().dimCode("channel_provider_name").formula("t2.provider_name").build();
        DimModelRequest dt = DimModelRequest.builder().dimCode("dt").formula("date_format(t1.create_time,'%Y%m%d')").build();
        return Arrays.asList(outTradeNo,mchId,buyerId,service,appid,status,payResult,tradeType,channelProviderCode,channelProviderName,dt);

    }

    @Transactional
    @Rollback(value = false)
    @Test
    public void testInitModel() {
        ModelRequest orderModel = getOrderModelRequest();
        modelService.saveModel(orderModel);
        ModelRequest refundOrderModel = getRefundModelRequest();
        modelService.saveModel(refundOrderModel);
    }

    private ModelRequest getOrderModelRequest() {
        ModelRequest modelRequest = new ModelRequest();
        modelRequest.setBizLineId(0L);
        modelRequest.setModelName("支付订单");
        modelRequest.setModelComment("支付订单");
        modelRequest.setDsnId(0L);
        modelRequest.setTableSource("pay_request t1 left join channel_provider t2 on t1.channel_provider_code = t2.provider_code left join pay_request_back t3 on t1.out_trade_no = t3.out_trade_no");
        modelRequest.setCondition("t3.id is not null");
        modelRequest.setMetricModels(getMetrics());
        modelRequest.setDimModels(getDims());
        return modelRequest;
    }

    private ModelRequest getRefundModelRequest() {
        //支付订单模型
        ModelRequest modelRequest = new ModelRequest();
        modelRequest.setBizLineId(0L);
        modelRequest.setModelName("支付订单模型");
        modelRequest.setModelComment("支付订单");
        modelRequest.setDsnId(0L);
        modelRequest.setTableSource("pay_request t1 left join channel_provider t2 on t1.channel_provider_code = t2.provider_code left join pay_request_back t3 on t1.out_trade_no = t3.out_trade_no");
        modelRequest.setCondition("t3.id is not null");
        modelRequest.setMetricModels(getMetrics());
        modelRequest.setDimModels(getDims());

        //退款订单模型
        ModelRequest refundOrder = new ModelRequest();
        refundOrder.setBizLineId(0L);
        refundOrder.setModelName("退款订单模型");
        refundOrder.setModelComment("退款订单");
        refundOrder.setDsnId(0L);
        refundOrder.setTableSource("pay_request_back t1 left join pay_request t2 on t1.out_trade_no = t2.out_trade_no left join channel_provider t3 on t2.channel_provider_code = t3.provider_code");
        //reverseOrder.setCondition();
        MetricModelRequest refundFee = MetricModelRequest.builder().metricCode("refund_fee").formula("sum(t2.fee)").build();
        MetricModelRequest refundActualAmount = MetricModelRequest.builder().metricCode("refund_actual_amount").formula("sum(t2.actual_amount)").build();
        MetricModelRequest refundTotalAmount = MetricModelRequest.builder().metricCode("refund_amount").formula("sum(t2.total_fee)").build();
        MetricModelRequest refundFeeRate = MetricModelRequest.builder().metricCode("refund_fee_rate").formula("sum(t2.fee)/sum(t2.total_fee)").build();
        MetricModelRequest refundOrderCnt = MetricModelRequest.builder().metricCode("refund_order_cnt").formula("count(distinct t1.out_trade_no)").advanceCalculate(MetricModelRequest.AdvanceCalculate.builder().forceDims(Arrays.asList("out_trade_no")).build()).build();
        refundOrder.setMetricModels(Arrays.asList(refundFee,refundActualAmount,refundTotalAmount,refundFeeRate,refundOrderCnt));

        DimModelRequest outTradeNo = DimModelRequest.builder().dimCode("out_trade_no").formula("t1.out_trade_no").build();
        DimModelRequest mchId = DimModelRequest.builder().dimCode("mch_id").formula("t1.mch_id").build();
        DimModelRequest buyerId = DimModelRequest.builder().dimCode("buyer_id").formula("t2.buyer_id").build();
        DimModelRequest service = DimModelRequest.builder().dimCode("service").formula("t2.service").build();
        DimModelRequest appid = DimModelRequest.builder().dimCode("appid").formula("t2.appid").build();
        DimModelRequest status = DimModelRequest.builder().dimCode("status").formula("t2.status").build();
        DimModelRequest payResult = DimModelRequest.builder().dimCode("pay_result").formula("t2.pay_result").build();
        DimModelRequest tradeType = DimModelRequest.builder().dimCode("trade_type").formula("t2.trade_type").build();
        DimModelRequest channelProviderCode = DimModelRequest.builder().dimCode("channel_provider_code").formula("t2.channel_provider_code").build();
        DimModelRequest channelProviderName = DimModelRequest.builder().dimCode("channel_provider_name").formula("t3.provider_name").build();
        DimModelRequest dt = DimModelRequest.builder().dimCode("dt").formula("date_format(t2.create_time,'%Y%m%d')").build();
        refundOrder.setDimModels(Arrays.asList(outTradeNo,mchId,buyerId,service,appid,status,payResult,tradeType,channelProviderCode,channelProviderName,dt));
        return refundOrder;
    }

    @Test
    public void testGetModel() {
        ModelDefinition modelDefinition = modelService.getModel(10L);
        System.out.println(new Gson().toJson(modelDefinition));
    }
}
