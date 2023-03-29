package com.xy.fedex.catalog.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MetricModelRequest {
    private Long appId;
    private Long modelId;
    private Long metricId;
}
