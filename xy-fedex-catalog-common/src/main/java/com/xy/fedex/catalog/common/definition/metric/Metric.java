package com.xy.fedex.catalog.common.definition.metric;

import lombok.Data;

import java.util.List;

@Data
public class Metric {
    private Long metricId;
    private String metric;
    private String displayName;

    @Data
    public static class MetricModel {
        private Long metricModelId;
        private Long modelId;
        private String metric;
        private String displayName;
        private String formula;
        private List<String> allowDims;
        private List<String> forceDims;
    }

    @Data
    public static class PrimaryMetricModel extends MetricModel {
        private boolean assist;
    }

    @Data
    public static class DeriveMetricModel extends MetricModel {
        private List<MetricModel> relateMetricModels;
    }
}
