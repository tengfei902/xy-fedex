package com.xy.fedex.facade.service.meta.query.impl;

import com.alibaba.druid.sql.ast.statement.SQLSelectItem;
import com.alibaba.druid.sql.ast.statement.SQLSubqueryTableSource;
import com.alibaba.druid.sql.ast.statement.SQLTableSource;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlSelectQueryBlock;
import com.xy.fedex.dsl.utility.SQLExprUtils;
import com.xy.fedex.facade.service.meta.dto.QueryMatchedModelDTO;
import com.xy.fedex.facade.service.meta.query.ModelQueryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ModelQueryServiceImpl implements ModelQueryService {
    @Override
    public void getModelQuery(QueryMatchedModelDTO metricModelMatchDTO) {
        MySqlSelectQueryBlock logicalSelect = metricModelMatchDTO.getLogicalSelect();
        List<QueryMatchedModelDTO.MetricMatchedModelDTO> metricMatchedModels = metricModelMatchDTO.getMetricMatchedModelList();


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
