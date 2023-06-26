package com.xy.fedex.facade.catalog;

import com.google.gson.Gson;
import com.xy.fedex.catalog.common.definition.field.impl.MetricModel;
import com.xy.fedex.facade.BaseTest;
import org.junit.jupiter.api.Test;

import java.util.List;

public class AppHolderTest extends BaseTest {

    @Test
    public void testGetApp() {
        AppHolder.App app = AppHolder.getApp(33L);
        System.out.println(new Gson().toJson(app));

        List<AppHolder.Metric> metrics = app.getMetrics();
        for(AppHolder.Metric metric:metrics) {
            System.out.println(new Gson().toJson(metric));
            List<MetricModel> metricModels = metric.getMetricModels();
            for(MetricModel metricModel:metricModels) {
                System.out.println(new Gson().toJson(metricModel));
            }
        }
    }
}
