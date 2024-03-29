package com.xy.fedex.catalog.common.definition.field.impl;

import com.xy.fedex.catalog.common.definition.field.MetaField;
import lombok.Data;

/**
 * @author tengfei
 */
@Data
public class MetricModel extends MetaField {
    private String metricCode;
    private String formula;
    private AdvanceCalculate advanceCalculate;
}
