package com.xy.fedex.catalog.api.dto.request.save.field.dim;

import lombok.Data;

import java.io.Serializable;

/**
 * @author tengfei
 */
@Data
public class SaveDimModelRequest implements Serializable {
    private String dimCode;
    private String formula;
    private String dimComment;
    private Long dimId;
    private Long modelId;
    private Long dimModelId;
}
