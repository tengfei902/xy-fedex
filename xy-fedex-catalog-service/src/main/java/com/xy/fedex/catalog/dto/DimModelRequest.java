package com.xy.fedex.catalog.dto;

import com.sun.istack.internal.NotNull;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class DimModelRequest {
    @NotNull
    private Long appId;
    @NotNull
    private Long dimId;
    private Long modelId;
}
