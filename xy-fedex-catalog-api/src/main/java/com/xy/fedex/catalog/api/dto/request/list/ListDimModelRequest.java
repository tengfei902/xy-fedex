package com.xy.fedex.catalog.api.dto.request.list;

import lombok.Data;

/**
 * @author tengfei
 */
@Data
public class ListDimModelRequest extends ListBaseRequest {
    private Long appId;
    private Long modelId;
    private Long dimId;
}
