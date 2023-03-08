package com.xy.fedex.catalog.po;

import lombok.Data;

@Data
public class AppMetricPO {
    private Long metricModelId;
    private Integer metricType;
    private Long metricId;
    private Long modelId;
    private String formula;
    private String metricCode;
    private String metricName;
    private String metricComment;
}
