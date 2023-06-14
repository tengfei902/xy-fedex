package com.xy.fedex.catalog.api.dto.request.save.field.dim;

import com.xy.fedex.catalog.api.dto.request.save.field.SaveFieldRequest;
import lombok.Data;

/**
 * @author tengfei
 */
@Data
public class SaveDimModelRequest extends SaveFieldRequest {
    private Long dimId;
    private Long modelId;
    private Long dimModelId;
}
