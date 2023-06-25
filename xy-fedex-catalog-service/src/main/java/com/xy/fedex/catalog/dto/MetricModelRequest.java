package com.xy.fedex.catalog.dto;

import com.google.gson.Gson;
import lombok.Data;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author tengfei
 */
@Data
public class MetricModelRequest {
    private Long metricId;
    private String modelIdArray;
    private String modelId;
    private Integer offset;
    private Integer limit;

    private MetricModelRequest() {

    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private List<Long> modelIds;
        private Long metricId;
        private Integer offset;
        private Integer limit;

        public Builder modelIds(List<Long> modelIds) {
            this.modelIds = modelIds;
            return this;
        }

        public Builder metricId(Long metricId) {
            this.metricId = metricId;
            return this;
        }

        public Builder limit(Integer offset,Integer limit) {
            this.offset = offset;
            this.limit = limit;
            return this;
        }

        public MetricModelRequest build() {
            MetricModelRequest metricModelRequest = new MetricModelRequest();
            metricModelRequest.setMetricId(this.metricId);
            metricModelRequest.setModelIdArray(new Gson().toJson(this.modelIds.stream().distinct().sorted(Comparator.naturalOrder()).collect(Collectors.toList())));
            metricModelRequest.setLimit(this.limit);
            metricModelRequest.setOffset(this.offset);
            return metricModelRequest;
        }
    }
}
