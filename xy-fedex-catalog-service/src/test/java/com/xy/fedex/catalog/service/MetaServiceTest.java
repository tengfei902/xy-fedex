package com.xy.fedex.catalog.service;

import com.google.gson.Gson;
import com.xy.fedex.catalog.BaseTest;
import com.xy.fedex.catalog.api.dto.request.list.ListMetricModelRequest;
import com.xy.fedex.catalog.common.definition.field.impl.DimModel;
import com.xy.fedex.catalog.common.definition.field.impl.MetricModel;
import com.xy.fedex.catalog.common.enums.DimType;
import com.xy.fedex.catalog.common.enums.MetricFormat;
import com.xy.fedex.catalog.common.enums.MetricType;
import com.xy.fedex.catalog.dao.DimDao;
import com.xy.fedex.catalog.dao.MetricDao;
import com.xy.fedex.catalog.dto.DimDTO;
import com.xy.fedex.catalog.dto.DimModelRequest;
import com.xy.fedex.catalog.dto.MetricDTO;
import com.xy.fedex.catalog.dto.MetricModelRequest;
import com.xy.fedex.catalog.enums.CatalogStatus;
import com.xy.fedex.catalog.po.DimPO;
import com.xy.fedex.catalog.po.MetricPO;
import com.xy.fedex.catalog.service.meta.MetaService;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class MetaServiceTest extends BaseTest {
    @Autowired
    private MetaService metaService;
    @Autowired
    private MetricDao metricDao;
    @Autowired
    private DimDao dimDao;

    @Test
    public void testSaveMetric() {
        Long bizLineId = 0L;

        MetricDTO metricDTO = MetricDTO.builder()
                .bizLineId(bizLineId)
                .subjectId(1000L)
                .metricType(MetricType.PRIMARY)
                .metricCode("trade_cnt")
                .formula("sum(trade_cnt)")
                .metricName("订单数")
                .metricComment("订单数")
                .build();
        Long metricId = metaService.saveMetric(metricDTO);
        MetricPO metricPO = metricDao.selectByPrimaryKey(metricId);
        Assert.assertNotNull(metricPO);
        Assert.assertTrue(metricPO.getStatus() == CatalogStatus.AVAILABLE.getStatus());
        Assert.assertTrue(metricPO.getMetricCode().equals(metricDTO.getMetricCode()));
        Assert.assertTrue(metricPO.getMetricName().equals(metricDTO.getMetricName()));
        Assert.assertTrue(metricPO.getMetricComment().equals(metricDTO.getMetricComment()));
        Assert.assertTrue(metricPO.getMetricFormat() == MetricFormat.TEXT.getCode());
        Assert.assertTrue(metricPO.getBizLineId().compareTo(metricDTO.getBizLineId()) == 0);
        Assert.assertTrue(metricPO.getMetricType().compareTo(MetricType.PRIMARY.getMetricType()) == 0);
        Assert.assertNotNull(metricPO.getFormula());

        System.out.println(new Gson().toJson(metricPO));

        MetricDTO metric = metaService.getMetric(bizLineId,"trade_cnt");
        Assert.assertNotNull(metric);
        Assert.assertTrue(metric.getMetricCode().equals(metricDTO.getMetricCode()));
        Assert.assertTrue(metric.getMetricName().equals(metricDTO.getMetricName()));
        Assert.assertTrue(metric.getMetricComment().equals(metricDTO.getMetricComment()));
        Assert.assertTrue(metric.getMetricFormat() == MetricFormat.TEXT.getCode());
        Assert.assertTrue(metric.getBizLineId().compareTo(metricDTO.getBizLineId()) == 0);
        Assert.assertTrue(metric.getMetricType() == MetricType.PRIMARY);
        Assert.assertTrue(StringUtils.equalsIgnoreCase(metric.getFormula(),metricDTO.getFormula()));
    }

    @Transactional
    @Rollback(value = false)
    @Test
    public void testInitMetrics() {
        //trade cnt
        Long bizLineId = 0L;

//        MetricDTO orderCnt = MetricDTO.builder().bizLineId(bizLineId).metricCode("order_cnt").formula("count(distinct out_trade_no)").metricName("订单数").metricComment("订单数").build();
//        metaService.saveMetric(orderCnt);
//
//        MetricDTO feeMetric = MetricDTO.builder().bizLineId(bizLineId).metricCode("fee").formula("sum(fee)").metricName("手续费").metricComment("手续费").build();
//        metaService.saveMetric(feeMetric);
//
//        MetricDTO actualAmountMetric = MetricDTO.builder().bizLineId(bizLineId).metricCode("actual_amount").formula("sum(actual_amount)").metricName("实际金额").metricComment("实际金额").build();
//        metaService.saveMetric(actualAmountMetric);
//
//        MetricDTO amountMetric = MetricDTO.builder().bizLineId(bizLineId).metricCode("amount").formula("sum(amount)").metricName("交易金额").metricComment("交易金额").build();
//        metaService.saveMetric(amountMetric);
//
//        MetricDTO lockAmount = MetricDTO.builder().bizLineId(bizLineId).metricCode("lock_amount").formula("sum(lock_amount)").metricName("锁定金额").build();
//        metaService.saveMetric(lockAmount);
//
//        MetricDTO paidAmount = MetricDTO.builder().bizLineId(bizLineId).metricCode("paid_amount").formula("sum(paid_amount)").metricName("已支付金额").build();
//        metaService.saveMetric(paidAmount);
//
//        MetricDTO totalAmount = MetricDTO.builder().bizLineId(bizLineId).metricCode("total_amount").formula("sum(total_amount)").metricName("总金额").build();
//        metaService.saveMetric(totalAmount);
//
//        MetricDTO agentPayAmount = MetricDTO.builder().bizLineId(bizLineId).metricCode("agent_pay_amount").formula("sum(agent_pay_amount)").metricName("机构支付金额").metricComment("机构支付金额").build();
//        metaService.saveMetric(agentPayAmount);
//
//        MetricDTO refundOrderCnt = MetricDTO.builder().bizLineId(bizLineId).metricCode("refund_order_cnt").formula("count(distinct out_trade_no)").metricName("退款订单").metricComment("退款订单").build();
//        metaService.saveMetric(refundOrderCnt);
//
//        MetricDTO refundAmount = MetricDTO.builder().bizLineId(bizLineId).metricCode("refund_amount").formula("sum(total_fee)").build();
//        metaService.saveMetric(refundAmount);
//
//        MetricDTO refundFee = MetricDTO.builder().bizLineId(bizLineId).metricCode("refund_fee").formula("sum(fee)").build();
//        metaService.saveMetric(refundFee);
//
//        MetricDTO refundActualAmount = MetricDTO.builder().bizLineId(bizLineId).metricCode("refund_actual_amount").formula("sum(actual_amount)").build();
//        metaService.saveMetric(refundActualAmount);
//
//        refundOrderCnt = metaService.getMetric(bizLineId,"refund_order_cnt");
//        orderCnt = metaService.getMetric(bizLineId,"order_cnt");
//
//        MetricDTO refundOrderCntRate = MetricDTO.builder().bizLineId(bizLineId).metricCode("refund_order_cnt_rate").formula(String.format("%s/%s",refundOrderCnt.getMetricId(),orderCnt.getMetricId())).metricType(MetricType.DERIVE).build();
//        metaService.saveMetric(refundOrderCntRate);
//
//        amountMetric = metaService.getMetric(bizLineId,"amount");
//        refundAmount = metaService.getMetric(bizLineId,"refund_amount");
//        MetricDTO refundAmountRate = MetricDTO.builder().bizLineId(bizLineId).metricCode("refund_amount_rate").formula(String.format("%s/%s",refundAmount.getMetricId(),amountMetric.getMetricId())).metricType(MetricType.DERIVE).build();
//        metaService.saveMetric(refundAmountRate);

        MetricDTO amount = metaService.getMetric(bizLineId,"refund_amount");
        MetricDTO fee = metaService.getMetric(bizLineId,"refund_fee");
        MetricDTO feeRate = MetricDTO.builder().bizLineId(bizLineId).metricCode("refund_fee_rate").formula(String.format("{%s}/{%s}",fee.getMetricId(),amount.getMetricId())).metricType(MetricType.DERIVE).build();
        metaService.saveMetric(feeRate);
    }

    @Test
    public void testSaveDim() {
        DimDTO dimDTO = DimDTO.builder()
                .bizLineId(0L)
                .dimCode("dt")
                .dimName("日期")
                .dimComment("日期")
                .dimType(0)
                .build();
        Long dimId = metaService.saveDim(dimDTO);
        DimPO dimPO = dimDao.selectByPrimaryKey(dimId);
        System.out.println(new Gson().toJson(dimPO));
    }

    @Transactional
    @Rollback(value = false)
    @Test
    public void testInitDim() {
        Long bizLineId = 0L;
//        DimDTO dimDTO = DimDTO.builder().bizLineId(bizLineId).dimCode("dt").dimName("日期").dimComment("日期").dimType(DimType.DATE.getDimType()).build();
//        metaService.saveDim(dimDTO);
//
//        DimDTO hourDim = DimDTO.builder().bizLineId(bizLineId).dimCode("hour").dimName("小时").dimComment("小时").dimType(DimType.HOUR.getDimType()).build();
//        metaService.saveDim(hourDim);
//
//        DimDTO groupIdDim = DimDTO.builder().bizLineId(bizLineId).dimCode("group_id").dimName("组").dimComment("组").dimType(DimType.TEXT.getDimType()).build();
//        metaService.saveDim(groupIdDim);
//
//        DimDTO accountStatus = DimDTO.builder().bizLineId(bizLineId).dimCode("account_status").dimName("账户状态").dimComment("账户状态").dimType(DimType.TEXT.getDimType()).build();
//        metaService.saveDim(accountStatus);
//
//        DimDTO tradeNoDim = DimDTO.builder().bizLineId(bizLineId).dimCode("out_trade_no").dimName("订单号").dimComment("订单号").dimType(DimType.TEXT.getDimType()).build();
//        metaService.saveDim(tradeNoDim);
//
//        DimDTO oprType = DimDTO.builder().bizLineId(bizLineId).dimCode("opr_type").dimName("订单类型").dimComment("订单类型").dimType(DimType.TEXT.getDimType()).build();
//        metaService.saveDim(oprType);
//
//        DimDTO providerCode = DimDTO.builder().bizLineId(bizLineId).dimCode("provider_code").dimName("供应商编号").dimComment("供应商编号").dimType(DimType.TEXT.getDimType()).build();
//        metaService.saveDim(providerCode);
//
//        DimDTO providerName = DimDTO.builder().bizLineId(bizLineId).dimCode("provider_name").dimName("供应商名称").dimComment("供应商名称").dimType(DimType.TEXT.getDimType()).build();
//        metaService.saveDim(providerName);
//
//        DimDTO mchIdDim = DimDTO.builder().bizLineId(bizLineId).dimCode("mch_id").dimName("客户id").dimComment("客户id").dimType(DimType.TEXT.getDimType()).build();
//        metaService.saveDim(mchIdDim);
//
//        DimDTO serviceDim = DimDTO.builder().bizLineId(bizLineId).dimCode("service").dimName("通道类型").dimComment("通道类型").dimType(DimType.TEXT.getDimType()).build();
//        metaService.saveDim(serviceDim);
//
//        DimDTO tradeTypeDim = DimDTO.builder().bizLineId(bizLineId).dimCode("trade_type").dimName("交易类型").dimComment("交易类型").dimType(DimType.TEXT.getDimType()).build();
//        metaService.saveDim(tradeTypeDim);
//
//        DimDTO payResultDim = DimDTO.builder().bizLineId(bizLineId).dimCode("pay_result").dimName("交易结果").dimComment("交易结果").dimType(DimType.TEXT.getDimType()).build();
//        metaService.saveDim(payResultDim);
//
//        DimDTO buyerIdDim = DimDTO.builder().bizLineId(bizLineId).dimCode("buyer_id").dimName("购买方id").dimComment("购买方id").dimType(DimType.TEXT.getDimType()).build();
//        metaService.saveDim(buyerIdDim);
        DimDTO channelProviderName = DimDTO.builder().bizLineId(bizLineId).dimCode("channel_provider_name").dimName("通道类型").dimComment("通道类型").dimType(DimType.TEXT.getDimType()).build();
        metaService.saveDim(channelProviderName);
    }

    @Test
    public void testGetMetricModel() {
//        MetricModel metricModel = metaService.getMetricModel(MetricModelRequest.builder().appId(21L).metricId(14L).modelId(10L).build());
//        System.out.println(new Gson().toJson(metricModel));
//        Assert.assertNotNull(metricModel);
    }

    @Test
    public void testGetMetricModels() {
        List<MetricModel> metricModels = metaService.getMetricModels(ListMetricModelRequest.builder().appId(21L).metricId(14L).build());
        System.out.println(new Gson().toJson(metricModels));
    }

    @Test
    public void testGetDimModels() {
        List<DimModel> dimModels = metaService.getDimModels(DimModelRequest.builder().appId(21L).dimId(9L).build());
        System.out.println(new Gson().toJson(dimModels));
    }
}
