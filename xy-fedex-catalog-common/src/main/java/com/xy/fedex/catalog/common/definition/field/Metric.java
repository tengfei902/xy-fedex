package com.xy.fedex.catalog.common.definition.field;

import com.xy.fedex.catalog.common.enums.MetricType;
import lombok.Data;

import java.io.Serializable;

/**
 * @author tengfei
 */
@Data
public class Metric implements Serializable {
    private String tenantId;
    private String accountId;
    private Long bizLineId;
    private Long metricId;
    private String metricCode;
    private String formula;
    private String metricName;
    private String metricComment;
    private Long subjectId;
    private Integer unit;
    private Integer metricFormat;
    private MetricType metricType;
}
