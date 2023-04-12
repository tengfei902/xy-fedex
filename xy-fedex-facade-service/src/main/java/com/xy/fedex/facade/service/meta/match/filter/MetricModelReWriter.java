package com.xy.fedex.facade.service.meta.match.filter;

import com.xy.fedex.catalog.common.definition.field.impl.MetricModel;

import java.util.List;

public interface MetricModelReWriter {
    List<MetricModel> rewrite(List<MetricModel> metricModels,List<String> dims);
}
