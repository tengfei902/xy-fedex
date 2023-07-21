package com.xy.fedex.facade.service.meta.query;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.expr.SQLBinaryOpExpr;
import com.alibaba.druid.sql.ast.expr.SQLBinaryOperator;
import com.alibaba.druid.sql.ast.statement.*;
import com.google.common.base.Joiner;
import com.xy.fedex.catalog.common.definition.ModelDefinition;
import com.xy.fedex.facade.catalog.AppHolder;
import com.xy.fedex.facade.service.meta.dto.QueryMatchedModelDTO;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author tengfei
 */
public class PhysicalQueryHolder {
    private SQLSelect logicalSelect;
    private Map<String, QuerySegment> QUERY_MAP = new HashMap<>();

    private List<QuerySegment> DERIVE_QUERY_LIST = new ArrayList<>();

    public PhysicalQueryHolder(SQLSelect logicalSelect) {
        this.logicalSelect = logicalSelect;
    }

    /**
     * select ... from ... where ..group by ...
     *
     * @param metricModel
     */
    public String addMetricModel(QueryMatchedModelDTO.MetricModel metricModel) {
        if (metricModel instanceof QueryMatchedModelDTO.PrimaryMetricModel) {
            QueryMatchedModelDTO.PrimaryMetricModel primaryMetricModel = (QueryMatchedModelDTO.PrimaryMetricModel) metricModel;
            AppHolder.Model model = AppHolder.getModel(primaryMetricModel.getModelId());

            List<String> dimCodes = primaryMetricModel.getDims().stream().map(QueryMatchedModelDTO.Dim::getDimCode).collect(Collectors.toList());
            boolean secondary = !Objects.isNull(primaryMetricModel.getSecondary()) && !CollectionUtils.isEmpty(primaryMetricModel.getSecondary().getGroupByList()) && !dimCodes.containsAll(primaryMetricModel.getSecondary().getGroupByList());
            if (secondary) {
                //二次计算
                dimCodes.addAll(primaryMetricModel.getSecondary().getGroupByList().stream().map(QueryMatchedModelDTO.Dim::getDimCode).collect(Collectors.toList()));
                dimCodes = dimCodes.stream().distinct().collect(Collectors.toList());
                String key = getModelKey(primaryMetricModel.getModelId(),dimCodes,primaryMetricModel.getCondition().toString());

                QUERY_MAP.putIfAbsent(key,new QuerySegment());
                QuerySegment querySegment = QUERY_MAP.get(key);

                querySegment.addMetric(primaryMetricModel.getFormula(), primaryMetricModel.getAlias());
                //维度
                for(QueryMatchedModelDTO.Dim dim:primaryMetricModel.getDims()) {
                    querySegment.addDim(dim.getFormula(),dim.getAlias());
                }
                //二次计算维度
                for(QueryMatchedModelDTO.Dim dim : primaryMetricModel.getSecondary().getGroupByList()) {
                    querySegment.addDim(dim.getFormula(), dim.getAlias());
                }
                //from & where
                querySegment.setFrom(model.getTableSource());
                querySegment.setWhere(getCondition(model.getCondition(), primaryMetricModel.getCondition()));
            }

            String key = getModelKey(primaryMetricModel.getModelId(),dimCodes,primaryMetricModel.getCondition().toString());
            QUERY_MAP.putIfAbsent(key, new QuerySegment());

            QuerySegment querySegment = QUERY_MAP.get(key);
            querySegment.addMetric(primaryMetricModel.getFormula(), primaryMetricModel.getAlias());

            for (QueryMatchedModelDTO.Dim dim : primaryMetricModel.getDims()) {
                querySegment.addDim(dim.getFormula(), dim.getAlias());
            }

            if (secondary) {
                String secondaryKey = getModelKey(primaryMetricModel.getModelId(),dimCodes,primaryMetricModel.getCondition().toString());
                querySegment.setFrom(new SQLExprTableSource("related:"+secondaryKey));
            } else {
                querySegment.setFrom(model.getTableSource());
                querySegment.setWhere(getCondition(model.getCondition(), primaryMetricModel.getCondition()));
            }

            return key;
        } else {
            QueryMatchedModelDTO.DeriveMetricModel deriveMetricModel = (QueryMatchedModelDTO.DeriveMetricModel) metricModel;
            List<QueryMatchedModelDTO.MetricModel> relateMetricModels = deriveMetricModel.getRelateMetricModels();

            List<String> relateKeys = new ArrayList<>();
            for(QueryMatchedModelDTO.MetricModel relateMetricModel : relateMetricModels) {
                String key = addMetricModel(relateMetricModel);
                relateKeys.add(key);
            }
            String key = "related:"+Joiner.on("#").join(relateKeys);
            QUERY_MAP.putIfAbsent(key, new QuerySegment());
            QuerySegment querySegment = QUERY_MAP.get(key);
            querySegment.addMetric(deriveMetricModel.getFormula(),deriveMetricModel.getAlias());
            querySegment.setFrom(new SQLExprTableSource(key));
            return key;
        }
    }

    private SQLExpr getCondition(SQLExpr modelCondition, SQLExpr metricCondition) {
        if (Objects.isNull(modelCondition)) {
            return metricCondition;
        }
        if (Objects.isNull(metricCondition)) {
            return modelCondition;
        }
        return new SQLBinaryOpExpr(modelCondition, SQLBinaryOperator.BooleanAnd, metricCondition);
    }

    public SQLSelect getPhysicalSelect() {
        return null;
    }

    private String getModelKey(Long modelId, List<String> dims, String where) {
        return String.format("%s:%s:%s", modelId, Joiner.on(",").join(dims.stream().sorted().collect(Collectors.toList())), where);
    }

    @Data
    private static class QuerySegment {
        private Map<String, SQLSelectItem> metrics = new HashMap<>();
        private Map<String, SQLSelectItem> dims = new HashMap<>();
        private SQLTableSource from;
        private SQLExpr where;

        public void addMetric(SQLExpr metric, String alias) {
            if (metrics.containsKey(alias)) {
                return;
            }
            metrics.put(alias, new SQLSelectItem(metric, alias));
        }

        public void addDim(SQLExpr dim, String alias) {
            if (dims.containsKey(alias)) {
                return;
            }
            dims.put(alias, new SQLSelectItem(dim, alias));
        }
    }
}
