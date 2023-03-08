package com.xy.fedex.catalog.dto;

import com.xy.fedex.catalog.common.enums.MetricType;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MetricDTO {
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
