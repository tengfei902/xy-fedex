package com.xy.fedex.catalog.api.dto.request.list;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author tengfei
 */
@Builder
@Data
public class ListMetricModelRequest extends ListBaseRequest {
    private Long appId;
    private Long metricId;
    private Long modelId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListMetricModelRequest that = (ListMetricModelRequest) o;
        return Objects.equals(appId, that.appId) && Objects.equals(metricId, that.metricId) && Objects.equals(modelId, that.modelId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(appId, metricId, modelId);
    }
}
