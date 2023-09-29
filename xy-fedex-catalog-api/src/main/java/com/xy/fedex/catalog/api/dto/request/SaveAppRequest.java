package com.xy.fedex.catalog.api.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author tengfei
 */
@Data
public class SaveAppRequest implements Serializable {
    private String appName;
    private String appComment;
    private List<Metric> metrics;
    private List<Dim> dims;
    private List<String> relateModels;

    public void add(Metric metric) {
        if(Objects.isNull(this.metrics)) {
            this.metrics = new ArrayList<>();
        }
        this.metrics.add(metric);
    }

    public void add(Dim dim) {
        if(Objects.isNull(this.dims)) {
            this.dims = new ArrayList<>();
        }
        this.dims.add(dim);
    }

    public void addMetric(String metricCode,String metricComment) {
        if(Objects.isNull(this.metrics)) {
            this.metrics = new ArrayList<>();
        }
        this.metrics.add(new Metric(metricCode,metricComment));
    }

    public void addDim(String dimCode,String dimComment) {
        if(Objects.isNull(this.dims)) {
            this.dims = new ArrayList<>();
        }
        this.dims.add(new Dim(dimCode,dimComment));
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class Metric implements Serializable {
        private String metricCode;
        private String metricComment;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class Dim implements Serializable {
        private String dimCode;
        private String dimComment;
    }
}
