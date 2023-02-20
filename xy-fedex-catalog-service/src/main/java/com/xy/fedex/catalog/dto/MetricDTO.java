package com.xy.fedex.catalog.dto;

import lombok.Builder;
import lombok.Data;
@Builder
@Data
public class MetricDTO {
    private Long tenantId;
    private Long bizLineId;
    private Long metricId;
    private String metricCode;
    private String metricName;
    private String metricComment;
    private Long subjectId;
    private Integer unit;
    private Integer metricFormat;
}
