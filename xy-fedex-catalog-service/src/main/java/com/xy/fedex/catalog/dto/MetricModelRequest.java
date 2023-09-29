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
    private Long appId;
    private Long metricId;
    private List<Long> modelIds;
    private Integer offset;
    private Integer limit;

    private MetricModelRequest() {

    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long appId;
        private List<Long> modelIds;
        private Long metricId;
        private Integer offset;
        private Integer limit;

        public Builder appId(Long appId) {
            this.appId = appId;
            return this;
        }

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
            metricModelRequest.setAppId(this.appId);
            metricModelRequest.setMetricId(this.metricId);
            metricModelRequest.setModelIds(this.modelIds);
            metricModelRequest.setLimit(this.limit);
            metricModelRequest.setOffset(this.offset);
            return metricModelRequest;
        }
    }
}
