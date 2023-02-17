package com.xy.fedex.catalog.common.definition;

import com.xy.fedex.catalog.common.definition.dim.Dim;
import com.xy.fedex.catalog.common.definition.metric.Metric;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class ModelDefinition implements Serializable {
    private Long modelId;
    private String modelName;
    private String modelDesc;
    private List<Metric.MetricModel> metrics;
    private List<Dim.DimModel> dims;
    private String tableSource;
    private String condition;

    @Data
    public static class ModelProp implements Serializable {
        private Map<String,String> properties;
    }
}
