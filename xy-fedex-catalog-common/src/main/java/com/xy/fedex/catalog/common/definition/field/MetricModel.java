package com.xy.fedex.catalog.common.definition.field;

import com.xy.fedex.catalog.common.enums.MetricType;
import lombok.Data;

/**
 * @author tengfei
 */
@Data
public class MetricModel extends MetaField {
    private Long metricModelId;
    private MetricType metricType;
}
