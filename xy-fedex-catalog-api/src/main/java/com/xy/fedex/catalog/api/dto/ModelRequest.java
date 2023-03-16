package com.xy.fedex.catalog.api.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class ModelRequest implements Serializable {
    private Long modelId;
    private String modelName;
    private String modelComment;
    private Long bizLineId;
    private Long dsnId;
    private Map<String,String> modelProp;
    private List<MetricModelRequest> metricModels;
    private List<DimModelRequest> dimModels;
    private String tableSource;
    private String condition;
}
