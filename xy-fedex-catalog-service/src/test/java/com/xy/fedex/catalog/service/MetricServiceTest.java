package com.xy.fedex.catalog.service;

import com.google.gson.Gson;
import com.xy.fedex.catalog.BaseTest;
import com.xy.fedex.catalog.common.definition.field.Metric;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

public class MetricServiceTest extends BaseTest {
    @Autowired
    private MetricService metricService;

    @Test
    public void saveMetric() {
        //单个metric保存
        Metric metric = new Metric();
        metric.setMetricCode("sale_amt");
        metric.setMetricName("sale amount");
        metric.setMetricComment("sale amount ddd");
        metricService.saveMetrics(Arrays.asList(metric));

        Metric saleAmtMetric = metricService.getMetric("sale_amt");
        System.out.println(new Gson().toJson(saleAmtMetric));
        Assert.assertEquals(saleAmtMetric.getMetricCode(),metric.getMetricCode());
        Assert.assertEquals(saleAmtMetric.getMetricName(),metric.getMetricName());
        Assert.assertEquals(saleAmtMetric.getMetricComment(),metric.getMetricComment());

        //多个metric保存
        Metric metric1 = new Metric();
        metric1.setMetricCode("sale_cnt");

        Metric metric2 = new Metric();
        metric2.setMetricCode("refund_amt");

        metricService.saveMetrics(Arrays.asList(metric1,metric2));

        List<Metric> metrics = metricService.getMetrics(Arrays.asList("sale_amt","sale_cnt","refund_amt"));
        System.out.println(new Gson().toJson(metrics));

        //更新metric
        metric.setMetricComment("test update");
        metricService.saveMetric(metric);

        Metric saleAmtMetric2 = metricService.getMetric("sale_amt");
        Assert.assertEquals(saleAmtMetric2.getMetricComment(),"test update");
    }
}
