package com.xy.fedex.facade.service.meta.match;

import com.alibaba.druid.DbType;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLName;
import com.alibaba.druid.sql.ast.expr.SQLBinaryOpExpr;
import com.alibaba.druid.sql.ast.expr.SQLBinaryOperator;
import com.alibaba.druid.sql.ast.expr.SQLIdentifierExpr;
import com.alibaba.druid.sql.ast.statement.*;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlSelectQueryBlock;
import com.google.common.base.Joiner;
import com.xy.fedex.catalog.common.definition.ModelDefinition;
import com.xy.fedex.catalog.common.definition.field.SecondaryCalculate;
import com.xy.fedex.catalog.common.definition.field.impl.DeriveMetricModel;
import com.xy.fedex.catalog.common.definition.field.impl.MetricModel;
import com.xy.fedex.catalog.common.definition.field.impl.PrimaryMetricModel;
import com.xy.fedex.dsl.utility.SQLExprUtils;
import com.xy.fedex.facade.exceptions.NoMetricModelMatchedException;
import com.xy.fedex.facade.service.cs.AppHolder;
import com.xy.fedex.facade.service.cs.ModelHolder;
import com.xy.fedex.facade.service.meta.dto.QueryMatchedModelDTO;
import com.xy.fedex.facade.service.meta.match.filter.MetricMatchedModelReWriter;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ModelMatchServiceImpl implements ModelMatchService {
    private List<MetricMatchedModelReWriter> filters;

    @Override
    public QueryMatchedModelDTO getMetricMatchedModels(SQLSelect logicalSelect) {
        QueryMatchedModelDTO queryMatchedModelDTO = getQueryMatchedModelsByDim(logicalSelect);
        for (MetricMatchedModelReWriter rewriter : filters) {
            queryMatchedModelDTO = rewriter.rewrite(queryMatchedModelDTO);
        }
        return queryMatchedModelDTO;
    }

    /**
     * 指标维度正交关系匹配初始化
     *
     * @param logicalSelect
     * @return
     */
    private QueryMatchedModelDTO getQueryMatchedModelsByDim(SQLSelect logicalSelect) {
        MySqlSelectQueryBlock logicalQueryBlock = (MySqlSelectQueryBlock) logicalSelect.getQueryBlock();
        SelectFields selectFields = new SelectFields(logicalSelect);
        QueryMatchedModelDTO queryMatchedModelDTO = new QueryMatchedModelDTO(logicalSelect);

        List<AppHolder.Dim> dims = selectFields.getDims();
        List<String> dimCodes = dims.stream().map(AppHolder.Dim::getDimCode).distinct().collect(Collectors.toList());

        for (AppHolder.Metric metric : selectFields.getMetrics()) {
            List<MetricModel> metricModels = metric.getMetricModels().stream().filter(metricModel -> checkMetricModelMatchDims(metricModel, dimCodes)).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(metricModels)) {
                throw new NoMetricModelMatchedException(String.format("no metric model matched for metric:%s in select:%s", metric.getMetricCode(), logicalSelect.toString()));
            }
            List<QueryMatchedModelDTO.MetricModel> matchedMetricModels = getMatchedMetricModels(metricModels,null,null,logicalQueryBlock);
            queryMatchedModelDTO.addMetricMatchedModels(null,null,matchedMetricModels);
        }
        return queryMatchedModelDTO;
    }

    private boolean checkMetricModelMatchDims(MetricModel metricModel, List<String> dimCodes) {
        if (metricModel instanceof PrimaryMetricModel) {
            PrimaryMetricModel primaryMetricModel = (PrimaryMetricModel) metricModel;
            if (!CollectionUtils.isEmpty(primaryMetricModel.getAdvanceCalculate().getForceDims()) && !new HashSet<>(dimCodes).containsAll(primaryMetricModel.getAdvanceCalculate().getForceDims())) {
                return false;
            }
            if(CollectionUtils.isEmpty(dimCodes)) {
                return true;
            }
            if(primaryMetricModel.getAdvanceCalculate().isAssist()) {
                return true;
            }
            return new HashSet<>(metricModel.getAdvanceCalculate().getAllowDims()).containsAll(dimCodes);
        } else {
            DeriveMetricModel deriveMetricModel = (DeriveMetricModel) metricModel;
            List<MetricModel> relateMetricModels = deriveMetricModel.getRelateMetricModels();
            for (MetricModel relateMetricModel : relateMetricModels) {
                if (!checkMetricModelMatchDims(relateMetricModel, dimCodes)) {
                    return false;
                }
            }
            return true;
        }
    }

    private List<QueryMatchedModelDTO.MetricModel> getMatchedMetricModels(List<MetricModel> metricModels,String alias, List<Pair<String, String>> dims,MySqlSelectQueryBlock logicalSelect) {
        return metricModels.stream().map(metricModel -> getMatchedMetricModel(metricModel,alias,dims,logicalSelect)).collect(Collectors.toList());
    }

    private QueryMatchedModelDTO.MetricModel getMatchedMetricModel(MetricModel metricModel, String alias, List<Pair<String, String>> dims,MySqlSelectQueryBlock logicalSelect) {
        QueryMatchedModelDTO.MetricModel selectMetric = new QueryMatchedModelDTO.MetricModel();
        selectMetric.setMetricId(metricModel.getMetricId());
        selectMetric.setMetricModelId(metricModel.getMetricModelId());
        selectMetric.setModelIds(null);
        MySqlSelectQueryBlock metricSelect = getMetricSelect(metricModel,alias,dims,logicalSelect);
        selectMetric.setMetricSelect(metricSelect);
        return selectMetric;
    }

    private MySqlSelectQueryBlock getMetricSelect(MetricModel metricModel, String alias, List<Pair<String, String>> dims,MySqlSelectQueryBlock logicalSelect) {
        if (metricModel instanceof PrimaryMetricModel) {
            PrimaryMetricModel primaryMetricModel = (PrimaryMetricModel) metricModel;
            ModelDefinition model = ModelHolder.getModel(primaryMetricModel.getModelId());
            MySqlSelectQueryBlock metricSelect = getMetricSelectSql(primaryMetricModel,alias,dims,model,logicalSelect);
            return metricSelect;
        } else {
            DeriveMetricModel deriveMetricModel = (DeriveMetricModel) metricModel;
            List<MetricModel> relateMetricModels = deriveMetricModel.getRelateMetricModels();
            String formula = deriveMetricModel.getFormula();

            List<SQLSubqueryTableSource> relateMetricSelects = new ArrayList<>();

            for(int i = 0;i < deriveMetricModel.getRelateMetricModels().size(); i++) {
                MetricModel relateMetricModel = deriveMetricModel.getRelateMetricModels().get(i);
                Long metricModelId = relateMetricModel.getMetricModelId();
                String metricCode = relateMetricModel.getCode();
                String metricAlias = metricCode+"_"+i;
                MySqlSelectQueryBlock relateMetricSelect = getMetricSelect(metricModel,metricAlias,dims,logicalSelect);

                String tableAlias = "t_"+deriveMetricModel.getMetricModelId()+"_"+i;
                SQLSubqueryTableSource sqlSubqueryTableSource = new SQLSubqueryTableSource(relateMetricSelect,tableAlias);
                formula = formula.replaceAll(String.format("\\${%s}",i),tableAlias+"."+metricAlias);

                relateMetricSelects.add(sqlSubqueryTableSource);
            }

            SQLTableSource tableSource = getJoinTableSource(relateMetricSelects);

            MySqlSelectQueryBlock metricSelect = new MySqlSelectQueryBlock();

            metricSelect.addSelectItem(formula,alias);
            for(Pair<String, String> dim:dims) {
                metricSelect.addSelectItem(dim.getLeft(),dim.getLeft());
            }
            metricSelect.setFrom(tableSource);
            return metricSelect;
        }
    }

    private SQLTableSource getJoinTableSource(List<SQLSubqueryTableSource> relateMetricSelects) {
        SQLTableSource tableSource = null;
        SQLSubqueryTableSource subQueryTableSource = relateMetricSelects.get(0);
        for(int i = 1;i<relateMetricSelects.size();i++) {
            SQLExpr condition = getJoinCondition(subQueryTableSource,relateMetricSelects.get(i));
            //TODO JOIN TYPE INPUT
            if(Objects.isNull(tableSource)) {
                tableSource = new SQLJoinTableSource(subQueryTableSource, SQLJoinTableSource.JoinType.INNER_JOIN, relateMetricSelects.get(i),condition);
            } else {
                tableSource = new SQLJoinTableSource(tableSource,SQLJoinTableSource.JoinType.INNER_JOIN, relateMetricSelects.get(i),condition);
            }
        }
        return tableSource;
    }

    private SQLExpr getJoinCondition(SQLSubqueryTableSource subQueryTableSource1,SQLSubqueryTableSource subQueryTableSource2) {
        List<String> names1 = subQueryTableSource1.getColumns().stream().map(SQLName::getSimpleName).collect(Collectors.toList());
        List<String> names2 = subQueryTableSource2.getColumns().stream().map(SQLName::getSimpleName).collect(Collectors.toList());

        List<String> conditions = new ArrayList<>();

        for(String name : names1) {
            if(names2.contains(name)) {
                conditions.add(String.format("%s.%s = %s.%s",subQueryTableSource1.getAlias(),name,subQueryTableSource2.getAlias(),name));
            }
        }

        return SQLUtils.toMySqlExpr(Joiner.on(" and ").join(conditions));
    }

    private MySqlSelectQueryBlock getMetricSelectSql(PrimaryMetricModel metricModel, String alias, List<Pair<String, String>> dims, ModelDefinition model,MySqlSelectQueryBlock logicalSelect) {
        List<String> groupByDimCodes = dims.stream().map(Pair::getRight).distinct().collect(Collectors.toList());
        SecondaryCalculate localSecondaryCalculate = getLocalSecondaryCalculate(metricModel.getAdvanceCalculate().getSecondary(),dims);

        if(!Objects.isNull(localSecondaryCalculate)) {
            SQLExpr metric = SQLUtils.toMySqlExpr(String.format("%s as %s",metricModel.getFormula(),alias));
            List<String> secondaryGroupByItems = localSecondaryCalculate.getGroupByList();
            //group by items
            List<Pair<String,String>> groupByItems = new ArrayList<>(dims);
            groupByItems.addAll(secondaryGroupByItems.stream().map(s -> new ImmutablePair<>(s,s +"_"+ RandomUtils.nextInt())).collect(Collectors.toList()));

            //from
            SQLExpr tableSource = SQLUtils.toMySqlExpr(model.getTableSource());
            //condition
            String modelCondition = model.getCondition();
            SQLExpr logicalCondition = getLocalizedLogicalCondition(logicalSelect,model);
            SQLExpr conditionExpr = getCondition(modelCondition,logicalCondition);

            MySqlSelectQueryBlock secondaryMetricSelect = getMetricSelect(metric,groupByItems,tableSource,conditionExpr,model);

            MySqlSelectQueryBlock metricSelect = new MySqlSelectQueryBlock();
            SQLSelectGroupByClause groupByClause = new SQLSelectGroupByClause();
            metricSelect.setGroupBy(groupByClause);
            //metric
            metricSelect.addSelectItem(String.format("%s(%s)",localSecondaryCalculate.getAgg(),alias),alias);
            //dims
            for(Pair<String, String> dimPair : dims) {
                String dimAlias = dimPair.getLeft();
                ModelDefinition.Dim dim = model.getDim(dimPair.getRight());

                SQLExpr expr = SQLUtils.toSQLExpr(dim.getFormula(), DbType.mysql);
                SQLSelectItem selectItem = new SQLSelectItem(expr, dimAlias);

                metricSelect.addSelectItem(selectItem);
                groupByClause.addItem(expr);
            }
            metricSelect.setFrom(secondaryMetricSelect,"t_"+RandomUtils.nextInt());
            return metricSelect;
        } else {
            SQLExpr metric = SQLUtils.toMySqlExpr(String.format("%s as %s",metricModel.getFormula(),alias));
            List<Pair<String,String>> groupByItems = new ArrayList<>(dims);
            SQLExpr tableSource = SQLUtils.toMySqlExpr(model.getTableSource());
            //condition
            String modelCondition = model.getCondition();
            SQLExpr logicalCondition = getLocalizedLogicalCondition(logicalSelect,model);
            SQLExpr conditionExpr = getCondition(modelCondition,logicalCondition);
            return getMetricSelect(metric,groupByItems,tableSource,conditionExpr,model);
        }
    }

    private SecondaryCalculate getLocalSecondaryCalculate(SecondaryCalculate secondaryCalculate,List<Pair<String,String>> logicalDims) {
        if(Objects.isNull(secondaryCalculate) || StringUtils.isEmpty(secondaryCalculate.getAgg()) || CollectionUtils.isEmpty(secondaryCalculate.getGroupByList())) {
            return null;
        }
        List<String> logicalDimCodes = logicalDims.stream().map(Pair::getRight).collect(Collectors.toList());
        if(new HashSet<>(logicalDimCodes).containsAll(secondaryCalculate.getGroupByList())) {
            return null;
        }
        SecondaryCalculate localSecondaryCalculate = new SecondaryCalculate();
        localSecondaryCalculate.setAgg(secondaryCalculate.getAgg());
        List<String> secondaryGroupByDims = new ArrayList<>(secondaryCalculate.getGroupByList());
        secondaryGroupByDims.removeAll(logicalDimCodes);
        localSecondaryCalculate.setGroupByList(secondaryGroupByDims);
        return localSecondaryCalculate;
    }

    private SQLExpr getCondition(String modelCondition,SQLExpr logicalCondition) {
        if(StringUtils.isEmpty(modelCondition)) {
            return logicalCondition;
        }
        if(Objects.isNull(logicalCondition)) {
            return SQLUtils.toMySqlExpr(modelCondition);
        }
        SQLExpr modelConditionExpr = SQLUtils.toMySqlExpr(modelCondition);
        return new SQLBinaryOpExpr(modelConditionExpr, SQLBinaryOperator.BooleanAnd,logicalCondition);
    }

    private MySqlSelectQueryBlock getMetricSelect(SQLExpr metric, List<Pair<String, String>> dims,SQLExpr tableSource, SQLExpr condition,ModelDefinition model) {
        MySqlSelectQueryBlock metricSelect = new MySqlSelectQueryBlock();
        //指标
        metricSelect.addSelectItem(metric);
        //维度
        SQLSelectGroupByClause groupByClause = new SQLSelectGroupByClause();
        metricSelect.setGroupBy(groupByClause);
        for(Pair<String, String> dimPair : dims) {
            String dimAlias = dimPair.getLeft();
            ModelDefinition.Dim dim = model.getDim(dimPair.getRight());

            SQLExpr expr = SQLUtils.toSQLExpr(dim.getFormula(), DbType.mysql);
            SQLSelectItem selectItem = new SQLSelectItem(expr, dimAlias);

            metricSelect.addSelectItem(selectItem);
            groupByClause.addItem(expr);
        }
        //表
        metricSelect.setFrom(tableSource);
        //where
        metricSelect.addCondition(condition);
        return metricSelect;
    }

    private SQLExpr getLocalizedLogicalCondition(MySqlSelectQueryBlock logicalSelect,ModelDefinition model) {
        SQLExpr condition = logicalSelect.getWhere().clone();
        SQLExprUtils.getSqlConditionFieldExpr(condition,new LocalizedFieldCallBackFunction(model));
        return condition;
    }

    public static class LocalizedFieldCallBackFunction implements SQLExprUtils.SQLExprFunction {
        private ModelDefinition modelDefinition;

        public LocalizedFieldCallBackFunction(ModelDefinition modelDefinition) {
            this.modelDefinition = modelDefinition;
        }
        @Override
        public void doCallBack(SQLExpr expr) {
            if(expr instanceof SQLIdentifierExpr) {
                SQLIdentifierExpr sqlIdentifierExpr = (SQLIdentifierExpr) expr;
                String name = sqlIdentifierExpr.getName();
                ModelDefinition.Dim dim = modelDefinition.getDim(name);
                sqlIdentifierExpr.setName(dim.getFormula());
            }
        }
    }

    private List<String> getGroupBy() {
        return null;
    }

    @Data
    public static class SelectFields {
        private Map<String, AppHolder.Metric> metricMap = new HashMap<>();
        private Map<String, AppHolder.Dim> dimMap = new HashMap<>();

        public SelectFields(SQLSelect logicalSelect) {
            MySqlSelectQueryBlock selectQueryBlock = (MySqlSelectQueryBlock) logicalSelect.getQueryBlock();
            AppHolder.App app = AppHolder.getApp(SQLExprUtils.getTableSource(logicalSelect));

            List<SQLSelectItem> selectItems = selectQueryBlock.getSelectList();
            for (SQLSelectItem item : selectItems) {
                SQLIdentifierExpr itemExpr = (SQLIdentifierExpr) item.getExpr();
                String alias = StringUtils.isEmpty(item.getAlias()) ? itemExpr.getName() : item.getAlias();
                AppHolder.Field field = app.findField(itemExpr.getName());

                if (field instanceof AppHolder.Metric) {
                    metricMap.put(alias, (AppHolder.Metric) field);
                } else {
                    dimMap.put(alias, (AppHolder.Dim) field);
                }
            }
        }

        public List<AppHolder.Metric> getMetrics() {
            return new ArrayList<>(this.metricMap.values());
        }

        public List<AppHolder.Dim> getDims() {
            return new ArrayList<>(this.dimMap.values());
        }

        public List<Pair<String, AppHolder.Dim>> getDimWithAlias() {
            return dimMap.keySet().stream().map(s -> {
                Pair<String, AppHolder.Dim> pair = new ImmutablePair<>(s,dimMap.get(s));
                return pair;
            }).collect(Collectors.toList());
        }
    }
}
