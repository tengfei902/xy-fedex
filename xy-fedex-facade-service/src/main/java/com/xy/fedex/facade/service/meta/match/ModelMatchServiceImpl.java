package com.xy.fedex.facade.service.meta.match;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.statement.SQLExprTableSource;
import com.alibaba.druid.sql.ast.statement.SQLSelect;
import com.alibaba.druid.sql.ast.statement.SQLSelectItem;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlSelectQueryBlock;
import com.xy.fedex.catalog.common.definition.field.impl.MetricModel;
import com.xy.fedex.dsl.utility.SQLExprUtils;
import com.xy.fedex.facade.exceptions.NoMetricModelMatchedException;
import com.xy.fedex.facade.service.cs.AppHolder;
import com.xy.fedex.facade.service.meta.dto.QueryMatchedModelDTO;
import com.xy.fedex.facade.service.meta.match.filter.MetricMatchedModelReWriter;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ModelMatchServiceImpl implements ModelMatchService {
    @Autowired
    private AppHolder appHolder;
    private List<MetricMatchedModelReWriter> filters;

    @Override
    public QueryMatchedModelDTO getMetricMatchedModels(SQLSelect logicalSelect) {
        QueryMatchedModelDTO queryMatchedModelDTO = getQueryMatchedModelsByDim(logicalSelect);
        for(MetricMatchedModelReWriter rewriter:filters) {
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
        MySqlSelectQueryBlock block = (MySqlSelectQueryBlock) logicalSelect.getQueryBlock();
        String tableName = SQLExprUtils.getTableName((SQLExprTableSource) block.getFrom());
        AppHolder.App app = appHolder.getApp(tableName);

        List<SQLSelectItem> selectItems = block.getSelectList();
        selectItems.stream().map(sqlSelectItem -> {
            String alias = sqlSelectItem.getAlias();
            SQLExpr expr = sqlSelectItem.getExpr();
            return null;
        });

        Map<String,List<String>> fieldAliasMap = new HashMap<>();
        List<AppHolder.Field> fields = fieldAliasMap.keySet().stream().map(app::findField).collect(Collectors.toList());
        List<AppHolder.Metric> metrics = fields.stream().filter(field -> field instanceof AppHolder.Metric).map(field -> (AppHolder.Metric) field).collect(Collectors.toList());
        List<AppHolder.Dim> dims = fields.stream().filter(field -> field instanceof AppHolder.Dim).map(field -> (AppHolder.Dim) field).collect(Collectors.toList());

        List<String> dimList = dims.stream().map(AppHolder.Dim::getDimCode).collect(Collectors.toList());
        QueryMatchedModelDTO queryMatchedModelDTO = new QueryMatchedModelDTO(logicalSelect);

        for (AppHolder.Metric metric : metrics) {
            List<MetricModel> metricModels = metric.getMetricModels().stream().filter(metricModel -> {
                 if(metricModel.getAllowDims().containsAll(dimList)) {
                     return true;
                 }
                 return false;
            }).collect(Collectors.toList());

            if(CollectionUtils.isEmpty(metricModels)) {
                throw new NoMetricModelMatchedException(String.format("no metric model matched for metric:%s in app:%s",metric.getMetricCode(),app.getAppId()));
            }

//            queryMatchedModelDTO.addMetricMatchedModels(metric.getMetricCode(),metricModels);
        }

        return queryMatchedModelDTO;
    }

    private Long getAppId(SQLSelect logicalSelect) {
        return null;
    }


}
