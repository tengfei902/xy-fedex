package com.xy.fedex.catalog.api.dto.request;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class GetDimModelRequest {
    private Long appId;
    private Long dimId;
    private Long modelId;
}
