package com.xy.fedex.catalog.common.definition.field;

import lombok.Data;

import java.util.List;

@Data
public class AdvanceCalculate {
    private String agg;
    private List<String> groupByDims;
}
