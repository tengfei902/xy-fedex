package com.xy.fedex.catalog.common.definition;

import com.xy.fedex.catalog.common.definition.dim.Dim;
import com.xy.fedex.catalog.common.definition.metric.Metric;
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
}
