package com.xy.fedex.catalog.po;

import lombok.Data;

import java.io.Serializable;

@Data
public class DimModelDetailPO implements Serializable {
    private Long dimModelId;

    private Long modelId;

    private Long dimId;

    private String formula;

    private String dimCode;

    private String dimName;

    private Integer dimType;

    private String dimComment;
}
