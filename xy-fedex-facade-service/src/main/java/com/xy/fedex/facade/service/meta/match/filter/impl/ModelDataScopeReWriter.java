package com.xy.fedex.facade.service.meta.match.filter.impl;

import com.xy.fedex.catalog.common.definition.field.impl.MetricModel;
import com.xy.fedex.facade.service.meta.match.filter.MetricModelReWriter;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 数据范围过滤
 */
@Component
public class ModelDataScopeReWriter implements MetricModelReWriter {
    @Override
    public List<MetricModel> rewrite(List<MetricModel> metricModels, List<String> dims) {
        return metricModels;
    }
}