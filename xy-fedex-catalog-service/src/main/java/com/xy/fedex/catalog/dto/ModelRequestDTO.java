package com.xy.fedex.catalog.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ModelRequestDTO {
    private BasicModelRequestDTO basicModel;
    private Map<String,String> modelProp;
    private List<MetricModelRequestDTO> metricModels;
    private List<DimModelRequestDTO> dimModels;

    @Data
    public static class BasicModelRequestDTO {
        private Long modelId;
        private String modelName;
        private String modelDesc;
        private Long bizLineId;
        private Long tenantId;
        private String creator;
        private Long dsnId;
        private String tableSource;
        private String condition;
    }

    @Data
    public static class MetricModelRequestDTO {
        private Long metricModelId;
        private Long metricId;
        private String formula;
        private String condition;
        private List<String> allowDims;
        private List<String> forceDims;
        private AdvanceCalculate advanceCalculate;
        private String orderBy;
    }

    @Data
    public static class AdvanceCalculate {
        private String agg;
        private String condition;
        private List<String> dims;
    }

    @Data
    public static class DimModelRequestDTO {
        private Long dimModelId;
        private Long dimId;
        private String dimName;
        private String formula;
    }
}
