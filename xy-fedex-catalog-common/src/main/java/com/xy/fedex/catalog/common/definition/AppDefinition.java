package com.xy.fedex.catalog.common.definition;

import com.xy.fedex.catalog.common.definition.field.Dim;
import com.xy.fedex.catalog.common.enums.MetricType;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AppDefinition implements Serializable {
    private Long appId;
    private String appName;
    private String appDesc;
    private List<Long> modelIds;
    private List<Metric> metrics;
    private List<Dim> dims;

    @Data
    public static class Metric implements Serializable {
        private Long metricModelId;
        private Long metricId;
        private String metricCode;
        private String metricName;
        private String metricComment;
        private String formula;
        private MetricType metricType;
    }
}
