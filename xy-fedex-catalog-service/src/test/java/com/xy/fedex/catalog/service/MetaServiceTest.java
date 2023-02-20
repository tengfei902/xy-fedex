package com.xy.fedex.catalog.service;

import com.google.gson.Gson;
import com.xy.fedex.catalog.BaseTest;
import com.xy.fedex.catalog.common.enums.DimType;
import com.xy.fedex.catalog.common.enums.MetricFormat;
import com.xy.fedex.catalog.dao.DimDao;
import com.xy.fedex.catalog.dao.MetricDao;
import com.xy.fedex.catalog.dto.DimDTO;
import com.xy.fedex.catalog.dto.MetricDTO;
import com.xy.fedex.catalog.enums.CatalogStatus;
import com.xy.fedex.catalog.po.DimPO;
import com.xy.fedex.catalog.po.MetricPO;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

public class MetaServiceTest extends BaseTest {
    @Autowired
    private MetaService metaService;
    @Autowired
    private MetricDao metricDao;
    @Autowired
    private DimDao dimDao;

    @Test
    public void testSaveMetric() {
        Long tenantId = 0L;
        Long bizLineId = 0L;

        MetricDTO metricDTO = MetricDTO.builder()
                .tenantId(tenantId)
                .bizLineId(bizLineId)
                .subjectId(1000L)
                .metricCode("trade_cnt")
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
        Assert.assertTrue(metricPO.getTenantId().compareTo(metricDTO.getTenantId()) == 0);

        System.out.println(new Gson().toJson(metricPO));
    }

    @Transactional
    @Rollback(value = false)
    @Test
    public void testInitMetrics() {
        //trade cnt
        Long tenantId = 0L;
        Long bizLineId = 0L;
        Long subjectId = 1000L;

        MetricDTO tradeCntMetric = MetricDTO.builder()
                .tenantId(tenantId)
                .bizLineId(bizLineId)
                .subjectId(subjectId)
                .metricCode("trade_cnt")
                .metricName("订单数")
                .metricComment("订单数")
                .build();
        metaService.saveMetric(tradeCntMetric);

        MetricDTO feeMetric = MetricDTO.builder()
                .tenantId(tenantId)
                .bizLineId(bizLineId)
                .subjectId(subjectId)
                .metricCode("fee")
                .metricName("手续费")
                .metricComment("手续费")
                .build();
        metaService.saveMetric(feeMetric);

        MetricDTO totalFeeMetric = MetricDTO.builder()
                .tenantId(tenantId)
                .bizLineId(bizLineId)
                .subjectId(subjectId)
                .metricCode("total_fee")
                .metricName("累计手续费")
                .metricComment("累计手续费")
                .build();
        metaService.saveMetric(totalFeeMetric);

        MetricDTO actualAmountMetric = MetricDTO.builder()
                .tenantId(tenantId)
                .bizLineId(bizLineId)
                .subjectId(subjectId)
                .metricCode("actual_amount")
                .metricName("实际金额")
                .metricComment("实际金额")
                .build();
        metaService.saveMetric(actualAmountMetric);

        MetricDTO amountMetric = MetricDTO.builder()
                .tenantId(tenantId)
                .bizLineId(bizLineId)
                .subjectId(subjectId)
                .metricCode("amount")
                .metricName("交易金额")
                .metricComment("交易金额")
                .build();
        metaService.saveMetric(amountMetric);
    }

    @Test
    public void testSaveDim() {
        DimDTO dimDTO = DimDTO.builder()
                .tenantId(0L)
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
        Long tenantId = 0L;

        DimDTO dimDTO = DimDTO.builder()
                .tenantId(tenantId)
                .dimCode("dt")
                .dimName("日期")
                .dimComment("日期")
                .dimType(DimType.DATE.getDimType())
                .build();
        metaService.saveDim(dimDTO);

        DimDTO hourDim = DimDTO.builder()
                .tenantId(tenantId)
                .dimCode("hour")
                .dimName("小时")
                .dimComment("小时")
                .dimType(DimType.HOUR.getDimType())
                .build();
        metaService.saveDim(hourDim);

        DimDTO groupIdDim = DimDTO.builder()
                .tenantId(tenantId)
                .dimCode("group_id")
                .dimName("组")
                .dimComment("组")
                .dimType(DimType.TEXT.getDimType())
                .build();
        metaService.saveDim(groupIdDim);

        DimDTO accountStatus = DimDTO.builder()
                .tenantId(tenantId)
                .dimCode("account_status")
                .dimName("账户状态")
                .dimComment("账户状态")
                .dimType(DimType.TEXT.getDimType())
                .build();
        metaService.saveDim(accountStatus);

        DimDTO tradeNoDim = DimDTO.builder()
                .tenantId(tenantId)
                .dimCode("out_trade_no")
                .dimName("订单号")
                .dimComment("订单号")
                .dimType(DimType.TEXT.getDimType())
                .build();
        metaService.saveDim(tradeNoDim);

        DimDTO oprType = DimDTO.builder()
                .tenantId(tenantId)
                .dimCode("opr_type")
                .dimName("订单类型")
                .dimComment("订单类型")
                .dimType(DimType.TEXT.getDimType())
                .build();
        metaService.saveDim(oprType);

        DimDTO providerCode = DimDTO.builder()
                .tenantId(tenantId)
                .dimCode("provider_code")
                .dimName("供应商编号")
                .dimComment("供应商编号")
                .dimType(DimType.TEXT.getDimType())
                .build();
        metaService.saveDim(providerCode);

        DimDTO providerName = DimDTO.builder()
                .tenantId(tenantId)
                .dimCode("provider_name")
                .dimName("供应商名称")
                .dimComment("供应商名称")
                .dimType(DimType.TEXT.getDimType())
                .build();
        metaService.saveDim(providerName);

        DimDTO mchIdDim = DimDTO.builder()
                .tenantId(tenantId)
                .dimCode("mch_id")
                .dimName("客户id")
                .dimComment("客户id")
                .dimType(DimType.TEXT.getDimType())
                .build();
        metaService.saveDim(mchIdDim);

        DimDTO serviceDim = DimDTO.builder()
                .tenantId(tenantId)
                .dimCode("service")
                .dimName("通道类型")
                .dimComment("通道类型")
                .dimType(DimType.TEXT.getDimType())
                .build();
        metaService.saveDim(serviceDim);

        DimDTO tradeTypeDim = DimDTO.builder()
                .tenantId(tenantId)
                .dimCode("trade_type")
                .dimName("交易类型")
                .dimComment("交易类型")
                .dimType(DimType.TEXT.getDimType())
                .build();
        metaService.saveDim(tradeTypeDim);

        DimDTO payResultDim = DimDTO.builder()
                .tenantId(tenantId)
                .dimCode("pay_result")
                .dimName("交易结果")
                .dimComment("交易结果")
                .dimType(DimType.TEXT.getDimType())
                .build();
        metaService.saveDim(payResultDim);
    }
}
