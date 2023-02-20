package com.xy.fedex.catalog.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class DimDTO {
    private Long tenantId;
    private Long dimId;
    private String dimCode;
    private String dimName;
    private Integer dimType;
    private String dimComment;
}
