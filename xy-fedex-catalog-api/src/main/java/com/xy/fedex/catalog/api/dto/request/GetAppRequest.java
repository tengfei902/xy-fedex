package com.xy.fedex.catalog.api.dto.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class GetAppRequest implements Serializable {
//    @NotNull
    private Long bizLineId;
    private Long appId;
    private String appName;
    private String creator;
    private int offset = 0;
    private int limit = 10;
}
