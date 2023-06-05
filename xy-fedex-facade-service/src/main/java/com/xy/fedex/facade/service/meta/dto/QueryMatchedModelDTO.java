package com.xy.fedex.facade.service.meta.dto;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.statement.SQLSelect;
import com.alibaba.druid.sql.ast.statement.SQLTableSource;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlSelectQueryBlock;
import com.xy.fedex.catalog.common.definition.field.Metric;
import com.xy.fedex.catalog.common.definition.field.SecondaryCalculate;
import com.xy.fedex.catalog.common.definition.field.impl.DimModel;
import com.xy.fedex.catalog.common.definition.field.impl.MetricModel;
import com.xy.fedex.dsl.sql.from.TableSource;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class QueryMatchedModelDTO {
    private MySqlSelectQueryBlock logicalSelect;
    private List<MetricMatchedModelDTO> metricMatchedModelList = new ArrayList<>();

    public QueryMatchedModelDTO(MySqlSelectQueryBlock logicalSelect) {
        this.logicalSelect = logicalSelect;
    }

    public void addMetricMatchedModels(String metricCode, Metric metric, List<MetricModel> metricModels) {
        MetricMatchedModelDTO metricMatchedModelDTO = new MetricMatchedModelDTO(metricCode,metric,metricModels);
        metricMatchedModelList.add(metricMatchedModelDTO);
    }

    @AllArgsConstructor
    @Data
    public static class MetricMatchedModelDTO {
        private String metricName;
        private Metric metric;
        private List<MetricModel> metricModels;
    }

    @Data
    public static class MetricModel {
        private String metricCode;
        private String alias;
        private Long metricId;
        private Long metricModelId;
        private String formula;
        private List<Long> modelIds;
    }

    @Data
    public static class PrimaryMetricModel extends MetricModel {
        private Long modelId;
        private List<String> groupByItems;
        private SQLExpr condition;
        private SecondaryCalculate secondary;
        private boolean assist;
    }

    @Data
    public static class DeriveMetricModel extends MetricModel {
        private List<PrimaryMetricModel> relateMetricModels;
    }
}
