package com.xy.fedex.catalog.dao;

import com.google.gson.Gson;
import com.xy.fedex.catalog.BaseTest;
import com.xy.fedex.catalog.common.enums.MetricType;
import com.xy.fedex.catalog.po.MetricModelPO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

public class MetricModelPODaoTest extends BaseTest {
    @Autowired
    private MetricModelDao metricModelDao;

    @Test
    public void testMetricModel() {
        MetricModelPO metricModel = new MetricModelPO();
        metricModel.setMetricId(100L);
        metricModel.setModelIdArray(new Gson().toJson(Arrays.asList(1L,2L)));
        metricModel.setMetricType(MetricType.DERIVE.getMetricType());
        metricModel.setFormula("sum(a+b)");
        metricModelDao.insertSelective(metricModel);
    }
}
