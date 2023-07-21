package com.xy.fedex.catalog.api.dto.request.save.field.metric;

import com.xy.fedex.catalog.common.definition.field.impl.AdvanceCalculate;
import com.xy.fedex.catalog.common.enums.MetricType;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author tengfei
 */
@Data
public class SaveMetricModelRequest implements Serializable {
    private String metricCode;
    private String formula;
    private String metricComment;
    private Long metricId;
    private Long metricModelId;
    private List<AdvanceCalculate> advanceCalculates;
    private MetricType metricType;
    private String creator;
}
