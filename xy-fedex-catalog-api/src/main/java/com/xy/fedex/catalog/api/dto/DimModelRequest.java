package com.xy.fedex.catalog.api.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class DimModelRequest implements Serializable {
    private Long dimModelId;
    private Long dimId;
    private String dimName;
    private String formula;
}
