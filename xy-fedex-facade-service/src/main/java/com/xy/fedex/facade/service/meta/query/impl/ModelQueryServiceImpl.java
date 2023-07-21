package com.xy.fedex.facade.service.meta.query.impl;

import com.alibaba.druid.sql.ast.statement.SQLSelect;
import com.alibaba.druid.sql.ast.statement.SQLSelectItem;
import com.alibaba.druid.sql.ast.statement.SQLSubqueryTableSource;
import com.alibaba.druid.sql.ast.statement.SQLTableSource;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlSelectQueryBlock;
import com.xy.fedex.dsl.utility.SQLExprUtils;
import com.xy.fedex.facade.service.meta.dto.PhysicalQueryPlan;
import com.xy.fedex.facade.service.meta.dto.QueryMatchedModelDTO;
import com.xy.fedex.facade.service.meta.query.ModelQueryService;
import com.xy.fedex.facade.service.meta.query.PhysicalQueryHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author tengfei
 */
@Service
public class ModelQueryServiceImpl implements ModelQueryService {

    @Override
    public PhysicalQueryPlan getPhysicalQuery(QueryMatchedModelDTO metricModelMatchDTO) {
        List<List<QueryMatchedModelDTO.MetricModel>> physicalSelectMatchedModels = getPhysicalSelectMatchedModels(metricModelMatchDTO);

        for(List<QueryMatchedModelDTO.MetricModel> physicalSelectMatchedModel:physicalSelectMatchedModels) {
            PhysicalQueryHolder physicalQueryHolder = new PhysicalQueryHolder(metricModelMatchDTO.getLogicalSelect());
            for(QueryMatchedModelDTO.MetricModel metricModel:physicalSelectMatchedModel) {
                physicalQueryHolder.addMetricModel(metricModel);
            }
            SQLSelect physicalSelect = physicalQueryHolder.getPhysicalSelect();
        }
        return null;
    }

    private List<List<QueryMatchedModelDTO.MetricModel>> getPhysicalSelectMatchedModels(QueryMatchedModelDTO metricModelMatchDTO) {
        List<QueryMatchedModelDTO.MetricMatchedModelDTO> metricMatchedModels = metricModelMatchDTO.getMetricMatchedModelList();

        List<List<QueryMatchedModelDTO.MetricModel>> metricModelLists = new ArrayList<>();
        List<QueryMatchedModelDTO.MetricModel> metricModelList = new ArrayList<>();
        metricModelLists.add(metricModelList);

        for(QueryMatchedModelDTO.MetricMatchedModelDTO metricMatchedModel : metricMatchedModels) {
            metricModelList.add(metricMatchedModel.getMetricModels().get(0));
        }

        return metricModelLists;
    }

    private void getMetricMergedQuery(MySqlSelectQueryBlock logicalSelect,List<QueryMatchedModelDTO.MetricModel> metricModels) {
        QueryMetricContainer queryMetricContainer = new QueryMetricContainer();
        for(QueryMatchedModelDTO.MetricModel metricModel:metricModels) {
//            MySqlSelectQueryBlock metricSelect = metricModel.getMetricSelect();
//            queryMetricContainer.addMetricSelect(metricSelect);
        }
    }

    public static class QueryMetricContainer {
        private MySqlSelectQueryBlock finalSelect;
        private Map<String,MySqlSelectQueryBlock> queryBlockMap;

        public MySqlSelectQueryBlock addMetricSelect(MySqlSelectQueryBlock metricSelect) {
            SQLTableSource sqlTableSource = metricSelect.getFrom();
            if(sqlTableSource instanceof SQLSubqueryTableSource) {
                SQLSubqueryTableSource sqlSubqueryTableSource = (SQLSubqueryTableSource) sqlTableSource;
                MySqlSelectQueryBlock subQueryBlock = (MySqlSelectQueryBlock) sqlSubqueryTableSource.getSelect().getQueryBlock();
                addMetricSelect(subQueryBlock);
            } else {
                String tableSourceSignature = getTableSourceSignature(metricSelect);
                if(queryBlockMap.containsKey(tableSourceSignature)) {
                    MySqlSelectQueryBlock existSelect = queryBlockMap.get(tableSourceSignature);
                    List<String> existFields = SQLExprUtils.getSelectItemAliases(existSelect);
                    for(SQLSelectItem selectItem:metricSelect.getSelectList()) {
                        String metricSelectAlias = SQLExprUtils.getSelectItemAlias(selectItem);
                        if(existFields.contains(metricSelectAlias)) {
                            continue;
                        }
                        existSelect.addSelectItem(selectItem.getExpr().clone());
                    }
                } else {
                    queryBlockMap.put(tableSourceSignature,metricSelect.clone());
                }
            }
            return null;
        }

        private String getTableSourceSignature(MySqlSelectQueryBlock metricSelect) {
            //from xxx where xxx
            return null;
        }

        public List<MySqlSelectQueryBlock> getModelQuery() {
            return null;
        }
    }
}
