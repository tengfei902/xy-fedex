package com.xy.fedex.catalog.service;

import com.xy.fedex.catalog.common.definition.field.Metric;
import com.xy.fedex.catalog.common.definition.field.impl.MetricModel;

import java.util.List;

/**
 * @author tengfei
 */
public interface MetricService {
    /**
     * 查询指标列表
     * @param metricCodes
     * @return
     */
    List<Metric> getMetrics(List<String> metricCodes);

    Metric getMetric(String metricCode);

    /**
     * 保存指标
     * @param metrics
     */
    void saveMetrics(List<Metric> metrics);

    void saveMetric(Metric metric);

    void saveMetricModels(List<MetricModel> metricModels);
}
