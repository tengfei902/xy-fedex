package com.xy.fedex.catalog.api.dto.request;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PrepareModelRequest {
    private Long dsnId;
    private String tableSource;
}
