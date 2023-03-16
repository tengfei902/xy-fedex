package com.xy.fedex.catalog.common.definition;

import com.xy.fedex.catalog.common.enums.DimType;
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
        private Long metricId;
        private String metricCode;
        private String metricName;
        private String metricComment;
        private MetricType metricType;
    }

    @Data
    public static class Dim implements Serializable {
        private Long dimId;
        private String dimCode;
        private String dimName;
        private String dimComment;
        private DimType dimType;
    }
}
