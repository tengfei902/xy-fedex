package com.xy.fedex.catalog.common.definition.field;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AdvanceCalculate implements Serializable {
    private String agg;
    private String condition;
    private List<String> groupByDims;
    private String sortType;
    private Integer limit;
    private Integer offset;
}
