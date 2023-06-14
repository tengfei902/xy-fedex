package com.xy.fedex.catalog.api.dto.request.save.field.metric;

import lombok.Data;

import java.util.List;

/**
 * @author tengfei
 */
@Data
public class SaveDeriveMetricModelRequest extends SaveMetricModelRequest {
    private List<SaveMetricModelRequest> relateMetricModels;
}
