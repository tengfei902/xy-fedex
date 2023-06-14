package com.xy.fedex.catalog.api.dto.request.save.field.metric;

import com.xy.fedex.catalog.api.dto.request.save.field.SaveFieldRequest;
import lombok.Data;

/**
 * @author tengfei
 */
@Data
public class SaveMetricModelRequest extends SaveFieldRequest {
    private Long metricId;
    private Long metricModelId;
}
