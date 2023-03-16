package com.xy.fedex.catalog.api.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Builder
@Data
public class MetricModelRequest implements Serializable {
    private Long metricModelId;
    private Long metricId;
    private String metricCode;
    private String formula;
    private AdvanceCalculate advanceCalculate;

    @Builder
    @Data
    public static class AdvanceCalculate implements Serializable {
        private String agg;
        private String condition;
        private List<String> groupByDims;
        private List<String> allowDims;
        private List<String> forceDims;
        private String orderBy;
    }
}
