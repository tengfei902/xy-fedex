package com.xy.fedex.facade.service.cs.dto;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.statement.SQLTableSource;
import com.xy.fedex.catalog.common.definition.field.Dim;
import com.xy.fedex.catalog.common.definition.field.Metric;
import lombok.Data;

import java.util.List;

public class MetaContainer {

    public static AppDTO getApp(Long appId) {
        return null;
    }

    public static class AppDTO {

        public MetricDTO getMetric(String metric) {
            return null;
        }

        public ModelDTO getModel(Long modelId) {
            return null;
        }
    }

    @Data
    public static class ModelDTO {
        private Long modelId;
        private String modelName;
        private List<Metric.MetricModel> metrics;
        private List<Dim.DimModel> dims;
        private SQLTableSource tableSource;
        private SQLExpr condition;
    }

    @Data
    public static class MetricDTO {
        private Metric metric;
        private List<Metric.MetricModel> metricModels;
    }
}
