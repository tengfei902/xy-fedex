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
import com.xy.fedex.catalog.dao.DimModelDao;
import com.xy.fedex.catalog.dao.MetricDao;
import com.xy.fedex.catalog.dto.DimDTO;
import com.xy.fedex.catalog.dto.DimModelRequest;
import com.xy.fedex.catalog.dto.MetricDTO;
import com.xy.fedex.catalog.dto.MetricModelRequest;
import com.xy.fedex.catalog.enums.CatalogStatus;
import com.xy.fedex.catalog.po.DimModelPO;
import com.xy.fedex.catalog.po.DimPO;
import com.xy.fedex.catalog.po.MetricPO;
import com.xy.fedex.catalog.service.meta.MetaService;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

public class MetaServiceTest extends BaseTest {
    @Autowired
    private MetaService metaService;
    @Autowired
    private MetricDao metricDao;
    @Autowired
    private DimDao dimDao;
    @Autowired
    private DimModelDao dimModelDao;

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

    @Test
    public void testGetDimModels() {
        DimModelPO dimModel1 = new DimModelPO();
        dimModel1.setDimId(1L);
        dimModel1.setModelId(100L);
        dimModel1.setFormula("dt");
        dimModelDao.insertSelective(dimModel1);

        DimModelPO dimModel2 = new DimModelPO();
        dimModel2.setDimId(2L);
        dimModel2.setModelId(100L);
        dimModel2.setFormula("hour");
        dimModelDao.insertSelective(dimModel2);

        DimModelPO dimModel3 = new DimModelPO();
        dimModel3.setDimId(3L);
        dimModel3.setModelId(101L);
        dimModel3.setFormula("shop_id");
        dimModelDao.insertSelective(dimModel3);

        DimModelPO dimModel4 = new DimModelPO();
        dimModel4.setDimId(3L);
        dimModel4.setModelId(102L);
        dimModel4.setFormula("shop_id");
        dimModelDao.insertSelective(dimModel4);

        List<DimModel> dimModels = metaService.getDimModels(DimModelRequest.builder().modelIds(Arrays.asList(100L,101L,102L)).dimId(3L).build());
        System.out.println(new Gson().toJson(dimModels));
    }
}
