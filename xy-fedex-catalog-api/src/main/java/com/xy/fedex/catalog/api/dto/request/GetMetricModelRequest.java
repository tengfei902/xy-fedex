package com.xy.fedex.catalog.api.dto.request;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Builder
@Data
public class GetMetricModelRequest implements Serializable {
    private Long appId;
    private Long metricId;
    private Long modelId;
}
