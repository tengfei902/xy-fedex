package com.xy.fedex.catalog.common.definition.field;

import lombok.Data;

import java.util.List;

@Data
public class AdvanceCalculate {
    private String agg;
    private String condition;
    private List<String> groupByDims;
    private String sortType;
    private Integer limit;
    private Integer offset;
}
