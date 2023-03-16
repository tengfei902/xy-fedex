package com.xy.fedex.catalog.common.definition;

import com.xy.fedex.catalog.common.definition.field.AdvanceCalculate;
import lombok.Data;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class ModelDefinition implements Serializable {
    private Long modelId;
    private String modelName;
    private String modelDesc;
    private String tableSource;
    private String condition;
    private Map<String,String> modelProp;
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
        private AdvanceCalculate advanceCalculate;
    }

    @Data
    public static class Dim implements Serializable {
        private Long dimModelId;
        private Long dimId;
        private String dimCode;
        private String dimName;
        private String dimComment;
        private String formula;
    }
}
