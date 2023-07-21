package com.xy.fedex.facade.service.meta.dto;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.expr.SQLIdentifierExpr;
import com.alibaba.druid.sql.ast.statement.SQLSelect;
import com.alibaba.druid.sql.ast.statement.SQLSelectItem;
import com.alibaba.druid.sql.ast.statement.SQLSelectQueryBlock;
import com.xy.fedex.dsl.utility.SQLExprUtils;
import com.xy.fedex.facade.catalog.AppHolder;
import com.xy.fedex.facade.exceptions.FieldNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.stream.Collectors;

@Data
public class QueryMatchedModelDTO {
    private SQLSelect logicalSelect;
    /**
     * 指标模型映射关系
     */
    private List<MetricMatchedModelDTO> metricMatchedModelList = new ArrayList<>();
    private transient SelectFields fields;

    public QueryMatchedModelDTO(SQLSelect logicalSelect) {
        this.fields = new SelectFields(logicalSelect);
        this.logicalSelect = logicalSelect;
    }

    public void addMetricMatchedModels(String alias, List<AppHolder.MetricModel> metricModels) {
        AppHolder.Metric metric = fields.getMetric(alias);
        MetricMatchedModelDTO metricMatchedModelDTO = new MetricMatchedModelDTO();
        metricMatchedModelDTO.setMetricCode(metric.getMetricCode());
        List<MetricModel> matchedMetricModels = metricModels.stream().map(metricModel -> getMatchedMetricModel(alias,metricModel)).collect(Collectors.toList());
        metricMatchedModelDTO.setMetricModels(matchedMetricModels);
        metricMatchedModelList.add(metricMatchedModelDTO);
    }

    private MetricModel getMatchedMetricModel(String alias, AppHolder.MetricModel metricModel) {
        if(metricModel instanceof AppHolder.PrimaryMetricModel) {
            return getMatchedPrimaryMetricModel(alias,(AppHolder.PrimaryMetricModel) metricModel);
        } else {
            return getMatchedDeriveMetricModel(alias,(AppHolder.DeriveMetricModel) metricModel);
        }
    }

    private PrimaryMetricModel getMatchedPrimaryMetricModel(String alias,AppHolder.PrimaryMetricModel metricModel) {
        PrimaryMetricModel primaryMetricModel = new PrimaryMetricModel();
        primaryMetricModel.setMetricModelId(metricModel.getMetricModelId());
        primaryMetricModel.setModelId(metricModel.getModelId());
        primaryMetricModel.setMetricId(metricModel.getMetricId());
        primaryMetricModel.setMetricCode(metricModel.getMetricCode());
        primaryMetricModel.setFormula(metricModel.getFormula());
        primaryMetricModel.setAssist(metricModel.isAssist());

        SecondaryCalculate secondaryCalculate = new SecondaryCalculate();
        com.xy.fedex.catalog.common.definition.field.impl.SecondaryCalculate secondary = metricModel.getSecondaryCalculate();
        if(!Objects.isNull(secondary)) {
            secondaryCalculate.setAgg(secondary.getAgg());
            List<Dim> groupByItems = secondary.getGroupByList().stream().map(s -> {
                AppHolder.DimModel dimModel = AppHolder.getModel(metricModel.getModelId()).getDim(s);
                return new Dim(dimModel.getDimCode(), dimModel.getFormula(), dimModel.getDimCode());
            }).collect(Collectors.toList());
            secondaryCalculate.setGroupByList(groupByItems);
        }

        primaryMetricModel.setSecondary(secondaryCalculate);
        primaryMetricModel.setAlias(alias);

        SQLExpr selectCondition = this.logicalSelect.getQueryBlock().getWhere().clone();
        List<String> allowDims = metricModel.getAllowDims();

        SQLExpr matchedCondition = SQLExprUtils.getMatchedCondition(selectCondition,allowDims);
        primaryMetricModel.setCondition(matchedCondition);
        primaryMetricModel.setDims(getGroupByDims(primaryMetricModel.getModelId(), allowDims));
        return primaryMetricModel;
    }

    private List<Dim> getGroupByDims(Long modelId,List<String> allowDims) {
        List<Dim> dims = new ArrayList<>();
        for(String alias:this.fields.getDimMap().keySet()) {
            AppHolder.Dim dim = this.fields.getDimMap().get(alias);
            if(!allowDims.contains(dim.getDimCode())) {
                continue;
            }
            AppHolder.DimModel dimModel = AppHolder.getModel(modelId).getDim(dim.getDimCode());
            dims.add(new Dim(dim.getDimCode(),dimModel.getFormula(),alias));

        }
        return dims;
    }

    private DeriveMetricModel getMatchedDeriveMetricModel(String alias,AppHolder.DeriveMetricModel deriveMetricModel) {
        DeriveMetricModel metricModel = new DeriveMetricModel();
        metricModel.setMetricId(deriveMetricModel.getMetricId());
        metricModel.setMetricCode(deriveMetricModel.getMetricCode());
        metricModel.setMetricModelId(deriveMetricModel.getMetricModelId());
        metricModel.setAlias(alias);
        metricModel.setFormula(deriveMetricModel.getFormula());
        metricModel.setRelateMetricModels(new ArrayList<>());
        List<AppHolder.MetricModel> relateMetricModels = deriveMetricModel.getRelateMetricModels();

        for(int i = 0;i<relateMetricModels.size();i++) {
            MetricModel relateMetricModel = getMatchedMetricModel(alias+"_"+i,relateMetricModels.get(i));
            metricModel.getRelateMetricModels().add(relateMetricModel);
        }

        return metricModel;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class MetricMatchedModelDTO {
        private String metricCode;
        private MetricModel metric;
        private List<MetricModel> metricModels;
    }

    @ToString
    @Data
    public static class MetricModel {
        protected String metricCode;
        protected String alias;
        protected Long metricId;
        protected Long metricModelId;
        protected SQLExpr formula;
        protected List<Long> modelIds;
    }

    @ToString
    @NoArgsConstructor
    @Data
    public static class PrimaryMetricModel extends MetricModel {
        private Long modelId;
        private SQLExpr condition;
        private SecondaryCalculate secondary;
        private List<Dim> dims;
        private boolean assist;
    }

    @Data
    public static class SecondaryCalculate {
        private String agg;
        private List<Dim> groupByList;

        public boolean isEmpty() {
            return StringUtils.isEmpty(this.agg) && CollectionUtils.isEmpty(this.groupByList);
        }
    }

    @ToString
    @Data
    public static class DeriveMetricModel extends MetricModel {
        private List<MetricModel> relateMetricModels;
    }

    @AllArgsConstructor
    @Data
    public static class Dim {
        private String dimCode;
        private SQLExpr formula;
        private String alias;
    }

    @Data
    public static class SelectFields {
        private SQLSelect logicalSelect;
        private Map<String, AppHolder.Metric> selectMetricMap = new HashMap<>();
        private Map<String, AppHolder.Dim> selectDimMap = new HashMap<>();
        private List<String> allDims = new ArrayList<>();

        public SelectFields(SQLSelect logicalSelect) {
            this.logicalSelect = logicalSelect;
            SQLSelectQueryBlock sqlSelectQueryBlock = logicalSelect.getQueryBlock();
            AppHolder.App app = AppHolder.getApp(sqlSelectQueryBlock.getFrom().toString());
            //查询指标和维度
            List<SQLSelectItem> selectItems = sqlSelectQueryBlock.getSelectList();
            for (SQLSelectItem item : selectItems) {
                SQLIdentifierExpr itemExpr = (SQLIdentifierExpr) item.getExpr();
                String alias = StringUtils.isEmpty(item.getAlias()) ? itemExpr.getName() : item.getAlias();
                AppHolder.Field field = app.findField(itemExpr.getName());

                if (field instanceof AppHolder.Metric) {
                    selectMetricMap.put(alias, (AppHolder.Metric) field);
                } else {
                    selectDimMap.put(alias, (AppHolder.Dim) field);
                }
            }
            //所有维度
            List<String> allFields = SQLExprUtils.getAllFields(sqlSelectQueryBlock);
            for (String fieldName : allFields) {
                AppHolder.Field field = app.findField(fieldName);
                if(field instanceof AppHolder.Dim) {
                    allDims.add(fieldName);
                }
            }
        }

        public List<AppHolder.Metric> getMetrics() {
            return new ArrayList<>(this.selectMetricMap.values());
        }

        public List<AppHolder.Dim> getDims() {
            return new ArrayList<>(this.selectDimMap.values());
        }

        public Map<String, AppHolder.Dim> getDimMap() {
            return this.selectDimMap;
        }

        public List<String> getMetricAliases() {
            return new ArrayList<>(selectMetricMap.keySet());
        }

        public AppHolder.Metric getMetric(String metricAlias) {
            if(!selectMetricMap.containsKey(metricAlias)) {
                throw new FieldNotFoundException(String.format("metric field %s not found in sql:%s",metricAlias,this.logicalSelect.toString()));
            }
            return selectMetricMap.get(metricAlias);
        }

        public List<Pair<String, AppHolder.Dim>> getDimWithAlias() {
            return selectDimMap.keySet().stream().map(s -> {
                Pair<String, AppHolder.Dim> pair = new ImmutablePair<>(s, selectDimMap.get(s));
                return pair;
            }).collect(Collectors.toList());
        }
    }
}
