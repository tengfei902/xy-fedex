package com.xy.fedex.catalog.api.dto.request;

import com.xy.fedex.catalog.common.definition.field.impl.DimModel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class SaveAppRequest implements Serializable {
    private Long appId;
    private String appName;
    private String appDesc;
    private Long bizLineId;
    private String appOwner;
    private String version;
    private Date createTime;
    private Date updateTime;

    private Schema schema;

    private List<Model> models;
    private List<Long> relatedModelIds;

    @Data
    public static class Schema implements Serializable {
        private List<Metric> metrics;
        private List<Dim> dims;
    }

    @Data
    public static class Column implements Serializable {
        private Long id;
        private String code;
        private String name;
    }

    @Data
    public static class Metric extends Column {
        private boolean enabled = true;
    }

    public static class Dim extends Column {

    }

    @Data
    public static class Model implements Serializable {
        private Long modelId;
        private String modelName;
        private String modelDesc;
        private String owner;
        private String version;
        private Date createTime;
        private Date updateTime;
        private String tableSource;
        private List<MetricModel> metrics;
        private List<DimModel> dims;

        public static class Builder {
            public Builder(String select) {

            }

            public Model build() {
                return null;
            }
        }
    }

    @Data
    public static class MetricModel implements Serializable {
        private String metricCode;
        private String formula;
        private List<String> allowDims;
        private List<String> forceDims;
        private AdvanceCalculate advanceCalculate;
    }

    @Data
    public static class AdvanceCalculate implements Serializable {
        private String agg;
        private List<String> groupByItems;
    }
}
