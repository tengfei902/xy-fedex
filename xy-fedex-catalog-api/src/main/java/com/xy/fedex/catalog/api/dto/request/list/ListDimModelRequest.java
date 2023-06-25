package com.xy.fedex.catalog.api.dto.request.list;

import lombok.Builder;
import lombok.Data;

/**
 * @author tengfei
 */
@Builder
@Data
public class ListDimModelRequest extends ListBaseRequest {
    private Long appId;
    private Long modelId;
    private Long dimId;
}
