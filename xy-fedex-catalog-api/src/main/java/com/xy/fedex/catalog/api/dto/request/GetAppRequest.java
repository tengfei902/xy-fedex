package com.xy.fedex.catalog.api.dto.request;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author tengfei
 */
@Builder
@Data
public class GetAppRequest implements Serializable {
    private String appName;
    private int offset = 0;
    private int limit = 10;
}
