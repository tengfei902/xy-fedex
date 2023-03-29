package com.xy.fedex.catalog.common.definition.field.impl;

import com.xy.fedex.catalog.common.definition.field.AdvanceCalculate;
import com.xy.fedex.catalog.common.definition.field.MetaField;
import lombok.Data;

import java.util.List;

@Data
public class MetricModel extends MetaField {
    private Long metricModelId;
    private Long metricId;
    private List<String> allowDims;
    private AdvanceCalculate advanceCalculate;
}
