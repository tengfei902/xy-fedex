package com.xy.fedex.catalog.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @author tengfei
 */
@Builder
@Data
public class DimDTO {
    private String tenantId;
    private String creator;
    private Long bizLineId;
    private Long dimId;
    private String dimCode;
    private String dimName;
    private Integer dimType;
    private String dimComment;
    private Long dimFamilyId;
    private Boolean master;
}
