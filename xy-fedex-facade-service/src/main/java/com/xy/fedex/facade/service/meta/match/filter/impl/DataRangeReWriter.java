package com.xy.fedex.facade.service.meta.match.filter.impl;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.statement.SQLSelect;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlSelectQueryBlock;
import com.xy.fedex.catalog.common.definition.metric.Metric;
import com.xy.fedex.dsl.utility.SQLExprUtils;
import com.xy.fedex.facade.service.cs.dto.MetaContainer;
import com.xy.fedex.facade.service.meta.dto.QueryMatchedModelDTO;
import com.xy.fedex.facade.service.meta.match.filter.AbstractMetricMatchedModelReWriter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 数据范围过滤
 */
@Component
public class DataRangeReWriter extends AbstractMetricMatchedModelReWriter {
    @Override
    public MetricModelReWriteResult doFilter(SQLSelect logicalSelect, List<Long> matchedModelIds) {
        MySqlSelectQueryBlock mySqlSelectQueryBlock = (MySqlSelectQueryBlock) logicalSelect.getQueryBlock();
        SQLExpr logicalCondition = mySqlSelectQueryBlock.getWhere();

        Long appId = Long.parseLong(SQLExprUtils.getTableSource(logicalSelect));
        MetaContainer.AppDTO app = MetaContainer.getApp(appId);
        for(Long matchedModelId:matchedModelIds) {
            MetaContainer.ModelDTO model = app.getModel(matchedModelId);

            SQLExpr sqlExpr = model.getCondition();

        }
        return null;
    }
}
