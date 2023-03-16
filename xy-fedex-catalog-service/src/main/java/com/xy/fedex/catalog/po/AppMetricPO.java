package com.xy.fedex.catalog.po;

import lombok.Data;

@Data
public class AppMetricPO {
    private Integer metricType;
    private Long metricId;
    private String metricCode;
    private String metricName;
    private String metricComment;
}
