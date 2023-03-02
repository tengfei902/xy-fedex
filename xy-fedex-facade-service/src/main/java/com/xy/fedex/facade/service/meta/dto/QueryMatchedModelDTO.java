package com.xy.fedex.facade.service.meta.dto;

import com.alibaba.druid.sql.ast.statement.SQLSelect;
import com.xy.fedex.catalog.common.definition.field.Metric;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class QueryMatchedModelDTO {
    private SQLSelect logicalSelect;
    private List<MetricMatchedModelDTO> metricMatchedModelList = new ArrayList<>();

    public QueryMatchedModelDTO(SQLSelect logicalSelect) {
        this.logicalSelect = logicalSelect;
    }

    public void addMetricMatchedModels(String metricName, Metric metric, List<Metric.MetricModel> metricModels) {
        MetricMatchedModelDTO metricMatchedModelDTO = new MetricMatchedModelDTO(metricName,metric,metricModels);
        metricMatchedModelList.add(metricMatchedModelDTO);
    }

    @AllArgsConstructor
    @Data
    public static class MetricMatchedModelDTO {
        private String metricName;
        private Metric metric;
        private List<Metric.MetricModel> metricModels;
    }
}
