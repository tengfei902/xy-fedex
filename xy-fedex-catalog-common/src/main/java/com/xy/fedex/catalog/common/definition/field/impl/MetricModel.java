package com.xy.fedex.catalog.common.definition.field.impl;

import com.xy.fedex.catalog.common.definition.field.AdvanceCalculate;
import com.xy.fedex.catalog.common.definition.field.MetaField;
import lombok.Data;

@Data
public class MetricModel extends MetaField {
    private Long metricModelId;
    private Long metricId;
    private AdvanceCalculate advanceCalculate;
}
