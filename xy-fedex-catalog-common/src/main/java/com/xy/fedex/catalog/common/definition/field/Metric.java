package com.xy.fedex.catalog.common.definition.field;

import com.xy.fedex.catalog.common.enums.MetricType;
import lombok.Data;

import java.util.List;

@Data
public class Metric {
    private Long metricModelId;
    private Long metricId;
    private String metricCode;
    private String metricName;
    private String metricComment;
    private String formula;
    private MetricType metricType;
}
