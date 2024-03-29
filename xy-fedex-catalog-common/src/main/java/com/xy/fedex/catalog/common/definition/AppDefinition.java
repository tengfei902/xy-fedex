package com.xy.fedex.catalog.common.definition;

import com.xy.fedex.catalog.common.definition.field.Dim;
import com.xy.fedex.catalog.common.definition.field.Metric;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author tengfei
 */
@Data
public class AppDefinition implements Serializable {
    private String appCode;
    private String appName;
    private String appComment;
    private List<Metric> metrics;
    private List<Dim> dims;
    private List<String> models;
}
