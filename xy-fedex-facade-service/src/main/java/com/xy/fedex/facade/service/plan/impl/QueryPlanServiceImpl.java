package com.xy.fedex.facade.service.plan.impl;

import com.alibaba.druid.sql.ast.statement.*;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlSelectQueryBlock;
import com.xy.fedex.dsl.sql.from.impl.SubQueryTableSource;
import com.xy.fedex.facade.service.meta.dto.QueryMatchedModelDTO;
import com.xy.fedex.facade.service.meta.match.ModelMatchService;
import com.xy.fedex.facade.service.plan.QueryPlanService;
import com.xy.fedex.facade.service.plan.dto.LogicalPlan;
import com.xy.fedex.facade.service.plan.dto.PhysicalPlan;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QueryPlanServiceImpl implements QueryPlanService {
    @Autowired
    private ModelMatchService modelMatchService;

    @Override
    public LogicalPlan getLogicalPlan(SQLSelect logicalSelect) {
        LogicalPlan logicalPlan = new LogicalPlan(logicalSelect);
        MySqlSelectQueryBlock mySqlSelectQueryBlock = (MySqlSelectQueryBlock) logicalSelect.getQueryBlock();
        logicalPlan.add(getQueryPlanNode(logicalSelect));
        return logicalPlan;
    }

    private LogicalPlan.Node getQueryPlanNode(SQLSelect select) {
        return getQueryPlanNode(select,null);
    }

    private LogicalPlan.Node getQueryPlanNode(SQLSelect select,String alias) {
        SQLTableSource tableSource = select.getQueryBlock().getFrom();
        return getQueryPlanNode(select,tableSource,alias);
    }

    private LogicalPlan.Node getQueryPlanNode(SQLSelect parent, SQLTableSource tableSource,String alias) {
        if(tableSource instanceof SQLExprTableSource) {
            return LogicalPlan.Node.newNode(parent,alias);
        }
        //这种场景下join的表只能是SubQuery
        if(tableSource instanceof SQLJoinTableSource) {
            SQLJoinTableSource joinTableSource = (SQLJoinTableSource) tableSource;
            SQLTableSource left = joinTableSource.getLeft();
            LogicalPlan.Node leftNode = getQueryPlanNode(parent,left,left.getAlias());

            SQLTableSource right = joinTableSource.getRight();
            LogicalPlan.Node rightNode = getQueryPlanNode(parent,right,right.getAlias());

            SQLSelect replacedSelect = getReplacedSelect(parent);
            LogicalPlan.Node x = LogicalPlan.Node.newNode(replacedSelect,getTableAlias());
            x.add(leftNode);
            x.add(rightNode);

            return x;
        }
        if(tableSource instanceof SQLSubqueryTableSource) {
            SQLSubqueryTableSource subQueryTableSource = (SQLSubqueryTableSource) tableSource;
            SQLSelect subQuerySelect = subQueryTableSource.getSelect();
            return getQueryPlanNode(subQuerySelect,subQueryTableSource.getAlias());
        }

        throw new IllegalArgumentException();
    }

    private SQLSelect getReplacedSelect(SQLSelect originSelect) {
        SQLSelect newSelect = originSelect.clone();
        SQLTableSource originTableSource = originSelect.getQueryBlock().getFrom();
        if(originTableSource instanceof SQLJoinTableSource) {
            SQLJoinTableSource originJoinTableSource = (SQLJoinTableSource)originTableSource;

            SQLJoinTableSource joinTableSource = ((SQLJoinTableSource) originTableSource).clone();
            joinTableSource.setLeft(originJoinTableSource.getLeft().getAlias(),null);
            joinTableSource.setRight(originJoinTableSource.getRight().getAlias(),null);

            newSelect.getQueryBlock().setFrom(joinTableSource);

            return newSelect;
        }
        if(originTableSource instanceof SubQueryTableSource) {
            SubQueryTableSource originSubQueryTableSource = (SubQueryTableSource)originTableSource;
            SQLExprTableSource newTableSource = new SQLExprTableSource(originSubQueryTableSource.getAlias());

            newSelect.getQueryBlock().setFrom(newTableSource);
            return newSelect;
        }
        throw new IllegalArgumentException();
    }

    private String getTableAlias() {
        return String.format("t_%s", RandomUtils.nextInt());
    }

    /**
     * 逻辑查询 -> 物理查询
     * 1.指标->模型映射关系
     * 2.指标->模型排序
     * 3.指标物理查询构建
     * @param logicalSelect
     * @return
     */
    @Override
    public PhysicalPlan getPhysicalPlan(SQLSelect logicalSelect) {
//        QueryMatchedModelDTO queryMatchedModelDTO = modelMatchService.getMetricMatchedModels(logicalSelect);
        return null;
    }
}
