package com.xy.fedex.facade.service.meta.query.impl;

import com.alibaba.druid.sql.ast.statement.SQLJoinTableSource;
import com.alibaba.druid.sql.ast.statement.SQLSelectItem;
import com.alibaba.druid.sql.ast.statement.SQLSubqueryTableSource;
import com.alibaba.druid.sql.ast.statement.SQLTableSource;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlSelectQueryBlock;
import com.xy.fedex.facade.service.meta.dto.QueryMatchedModelDTO;
import com.xy.fedex.facade.service.meta.query.ModelQueryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

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
            MySqlSelectQueryBlock metricSelect = metricModel.getMetricSelect();
            queryMetricContainer.addMetricSelect(metricSelect);
        }
    }

    public static class QueryMetricContainer {
        private MySqlSelectQueryBlock finalSelect;
        private Map<String,MySqlSelectQueryBlock> queryBlockMap;

        public MySqlSelectQueryBlock addMetricSelect(MySqlSelectQueryBlock metricSelect) {
            if(Objects.isNull(this.finalSelect)) {
                this.finalSelect = metricSelect;
            }
            SQLTableSource tableSource = metricSelect.getFrom();
            if(tableSource instanceof SQLSubqueryTableSource) {
                SQLSubqueryTableSource sqlSubqueryTableSource = (SQLSubqueryTableSource) tableSource;
                MySqlSelectQueryBlock subQueryMergedSelect = addMetricSelect((MySqlSelectQueryBlock) sqlSubqueryTableSource.getSelect().getQueryBlock());
                MySqlSelectQueryBlock mergedSelect = metricSelect.clone();
            }
            if(tableSource instanceof SQLJoinTableSource) {

            }
            else {
                String tableSourceSignature = getTableSourceSignature(metricSelect);
                if(queryBlockMap.containsKey(tableSourceSignature)) {
                    List<SQLSelectItem> metricSelectItems = metricSelect.getSelectList();
                    MySqlSelectQueryBlock select = queryBlockMap.get(tableSourceSignature);

                    Map<String,SQLSelectItem> selectItemMap = select.getSelectList().stream().collect(Collectors.toMap(SQLSelectItem::getAlias, Function.identity()));
                    for(SQLSelectItem selectItem:metricSelectItems) {
                        if(selectItemMap.containsKey(selectItem.getAlias())) {
                            continue;
                        }
                        select.addSelectItem(selectItem.clone());
                    }
                } else {
                    queryBlockMap.put(tableSourceSignature,metricSelect.clone());
                }
                return queryBlockMap.get(tableSourceSignature);
            }
        }

        private String getTableSourceSignature(MySqlSelectQueryBlock metricSelect) {
            //from xxx where xxx

        }

        public List<MySqlSelectQueryBlock> getModelQuery() {
            return null;
        }
    }
}
