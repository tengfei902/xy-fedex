package com.xy.fedex.facade.service.meta.match.filter;

import com.alibaba.druid.sql.ast.statement.SQLSelect;
import com.xy.fedex.catalog.common.definition.field.Metric;
import com.xy.fedex.facade.service.meta.dto.QueryMatchedModelDTO;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractMetricMatchedModelReWriter implements MetricMatchedModelReWriter {
    @Override
    public QueryMatchedModelDTO rewrite(QueryMatchedModelDTO queryModelMatch) {
        List<Long> matchedModelIds = new ArrayList<>();

        List<QueryMatchedModelDTO.MetricMatchedModelDTO> metricMatchedModels = queryModelMatch.getMetricMatchedModelList();
        for(QueryMatchedModelDTO.MetricMatchedModelDTO metricMatchedModel:metricMatchedModels) {
            matchedModelIds.addAll(metricMatchedModel.getMetricModels().stream().map(Metric.MetricModel::getModelId).collect(Collectors.toList()));
        }
        matchedModelIds = matchedModelIds.stream().distinct().collect(Collectors.toList());

        doFilter(queryModelMatch.getLogicalSelect(),matchedModelIds);
        return queryModelMatch;
    }

    public abstract MetricModelReWriteResult doFilter(SQLSelect logicalSelect,List<Long> matchedModelIds);

    @Data
    public static class MetricModelReWriteResult {
    }
}
