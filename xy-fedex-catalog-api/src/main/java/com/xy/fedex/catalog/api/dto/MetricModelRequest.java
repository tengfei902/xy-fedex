package com.xy.fedex.catalog.api.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class MetricModelRequest implements Serializable {
    private Long metricModelId;
    private Long metricId;
    private String formula;
    private String condition;
    private List<String> allowDims;
    private List<String> forceDims;
    private AdvanceCalculate advanceCalculate;
    private String orderBy;

    @Data
    public static class AdvanceCalculate {
        private String agg;
        private String condition;
        private List<String> dims;
    }
}
