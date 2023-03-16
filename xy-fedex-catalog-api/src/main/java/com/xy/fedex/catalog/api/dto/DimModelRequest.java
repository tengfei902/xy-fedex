package com.xy.fedex.catalog.api.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Builder
@Data
public class DimModelRequest implements Serializable {
    private Long dimModelId;
    private Long dimId;
    private String dimCode;
    private String formula;
}
