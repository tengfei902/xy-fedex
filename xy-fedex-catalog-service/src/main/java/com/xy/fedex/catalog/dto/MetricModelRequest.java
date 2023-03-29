package com.xy.fedex.catalog.dto;

import com.sun.istack.internal.NotNull;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MetricModelRequest {
    @NotNull
    private Long appId;
    private Long modelId;
    @NotNull
    private Long metricId;
}
