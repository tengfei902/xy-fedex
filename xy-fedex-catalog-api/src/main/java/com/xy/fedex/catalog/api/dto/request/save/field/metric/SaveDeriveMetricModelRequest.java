package com.xy.fedex.catalog.api.dto.request.save.field.metric;

import com.xy.fedex.catalog.common.definition.field.impl.AdvanceCalculate;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author tengfei
 */
@Data
public class SaveDeriveMetricModelRequest implements Serializable {
    private Long appId;
    private Long metricModelId;
    private Long metricId;
    private String formula;
    private List<AdvanceCalculate> advanceCalculates;
}
