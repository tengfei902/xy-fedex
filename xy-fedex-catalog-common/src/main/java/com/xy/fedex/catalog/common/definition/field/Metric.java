package com.xy.fedex.catalog.common.definition.field;

import lombok.Data;

import java.io.Serializable;

/**
 * @author tengfei
 */
@Data
public class Metric implements Serializable {
    private String metricCode;
    private String metricName;
    private String metricComment;
    private String metricFormat;
}
