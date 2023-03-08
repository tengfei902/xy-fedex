package com.xy.fedex.catalog.api.dto.request;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SaveAppRequest implements Serializable {
    private Long appId;
    private String appName;
    private String appDesc;
    private Long tenantId;
    private Long bizLineId;
    private List<Long> relatedModelIds;
}
