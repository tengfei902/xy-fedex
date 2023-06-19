package com.xy.fedex.catalog.api.dto.request;

import lombok.Data;
import java.io.Serializable;
import java.util.List;

@Data
public class SaveAppRequest implements Serializable {
    private Long appId;
    private String appName;
    private String appComment;
    private String tenantId;
    private Long bizLineId;
    private String creator;
    private List<Metric> metrics;
    private List<Dim> dims;
    private List<Long> relateModelIds;

    @Data
    public static class Metric implements Serializable {
        private Long metricId;
        private String metricCode;
        private String metricName;
        private String metricComment;
        private String metricFormat;
    }

    @Data
    public static class Dim implements Serializable {
        private Long dimId;
        private String dimCode;
        private String dimName;
        private String dimComment;
        private String dimFormat;
    }
}
