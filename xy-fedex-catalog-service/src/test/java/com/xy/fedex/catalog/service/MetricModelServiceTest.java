package com.xy.fedex.catalog.service;

import com.google.gson.Gson;
import com.xy.fedex.catalog.BaseTest;
import com.xy.fedex.catalog.common.definition.field.impl.AdvanceCalculate;
import com.xy.fedex.catalog.common.definition.field.impl.MetricModel;
import com.xy.fedex.catalog.common.enums.MetricType;
import com.xy.fedex.catalog.dao.MetricModelDao;
import com.xy.fedex.catalog.dto.MetricModelRequest;
import com.xy.fedex.catalog.po.MetricModelPO;
import com.xy.fedex.catalog.service.meta.MetricModelService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MetricModelServiceTest extends BaseTest {
    @Autowired
    private MetricModelService metricModelService;
    @Autowired
    private MetricModelDao metricModelDao;

    @Test
    public void testGetMetricModels() {
        //primary metric model test
        MetricModelPO primaryMetricModel1 = new MetricModelPO();
        primaryMetricModel1.setMetricId(1L);
        primaryMetricModel1.setModelIdArray(new Gson().toJson(Arrays.asList(100L)));
        primaryMetricModel1.setMetricType(MetricType.PRIMARY.getMetricType());
        primaryMetricModel1.setFormula("sum(t1.total_amt)");

        List<AdvanceCalculate> advanceCalculates = new ArrayList<>();
        AdvanceCalculate advanceCalculate = new AdvanceCalculate();
        advanceCalculate.setAllowDims(Arrays.asList("d1","d2","d3"));
        advanceCalculates.add(advanceCalculate);

        primaryMetricModel1.setAdvanceCalculate(new Gson().toJson(advanceCalculates));
        metricModelDao.insertSelective(primaryMetricModel1);

        MetricModelPO primaryMetricModel2 = new MetricModelPO();
        primaryMetricModel2.setMetricId(2L);
        primaryMetricModel2.setModelIdArray(new Gson().toJson(Arrays.asList(101L)));
        primaryMetricModel2.setMetricType(MetricType.PRIMARY.getMetricType());
        primaryMetricModel2.setFormula("count(distinct t1.uuid)");

        List<AdvanceCalculate> advanceCalculates2 = new ArrayList<>();
//        AdvanceCalculate advanceCalculate2 = new AdvanceCalculate();
//        advanceCalculate2.setAllowDims(Arrays.asList("d1","d2","d3"));
//        advanceCalculates2.add(advanceCalculate2);

        primaryMetricModel2.setAdvanceCalculate(new Gson().toJson(advanceCalculates2));
        metricModelDao.insertSelective(primaryMetricModel2);

        MetricModelRequest metricModelRequest = MetricModelRequest.builder().modelIds(Arrays.asList(100L,11L)).build();
        List<MetricModel> metricModels = metricModelService.getMetricModels(metricModelRequest);
        System.out.println(new Gson().toJson(metricModels));

        //derive metric model test
        MetricModelPO deriveMetricModel = new MetricModelPO();
        deriveMetricModel.setModelIdArray(new Gson().toJson(Arrays.asList(100L,101L)));
        deriveMetricModel.setMetricType(MetricType.DERIVE.getMetricType());
        deriveMetricModel.setFormula("${0}/${1}");

        List<AdvanceCalculate> list = new ArrayList<>();

        AdvanceCalculate advanceCalculate1 = new AdvanceCalculate();
        advanceCalculate1.setMetricModelId(primaryMetricModel1.getId());
        advanceCalculate1.setAllowDims(Arrays.asList("d1","d2","d3","d4"));

        AdvanceCalculate advanceCalculate2 = new AdvanceCalculate();
        advanceCalculate2.setMetricModelId(primaryMetricModel1.getId());
        advanceCalculate2.setAllowDims(Arrays.asList("d1"));
        advanceCalculate2.setAssist(true);

        list.add(advanceCalculate1);
        list.add(advanceCalculate2);

        deriveMetricModel.setAdvanceCalculate(new Gson().toJson(list));
        deriveMetricModel.setMetricId(102L);

        metricModelDao.insertSelective(deriveMetricModel);

        metricModelRequest = MetricModelRequest.builder().modelIds(Arrays.asList(100L,101L,11L)).build();
        metricModels = metricModelService.getMetricModels(metricModelRequest);
        System.out.println(new Gson().toJson(metricModels));
    }
}
