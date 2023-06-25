package com.xy.fedex.catalog.po;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class MetricModelDetailPO implements Serializable {
    private Long metricModelId;

    private List<Long> modelIds;

    private Long metricId;

    private Integer metricType;

    private String formula;

    private String advanceCalculate;

    private String relateMetrics;

    private String metricCode;

    private String metricName;

    private String metricComment;
}
