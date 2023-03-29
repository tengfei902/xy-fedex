package com.xy.fedex.catalog.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class DimModelRequest {
    private Long appId;
    private Long dimId;
    private Long modelId;
}
