package com.xy.fedex.facade.service.plan.impl;

import com.alibaba.druid.sql.ast.statement.*;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlSelectQueryBlock;
import com.xy.fedex.dsl.sql.from.impl.SubQueryTableSource;
import com.xy.fedex.facade.service.plan.QueryPlanService;
import com.xy.fedex.facade.service.plan.dto.QueryPlan;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;

@Service
public class QueryPlanServiceImpl implements QueryPlanService {

    @Override
    public QueryPlan getQueryPlan(SQLSelect logicalSelect) {
        QueryPlan queryPlan = new QueryPlan(logicalSelect);
        MySqlSelectQueryBlock mySqlSelectQueryBlock = (MySqlSelectQueryBlock) logicalSelect.getQueryBlock();
        queryPlan.add(getQueryPlanNode(mySqlSelectQueryBlock));
        return queryPlan;
    }

    private QueryPlan.Node getQueryPlanNode(MySqlSelectQueryBlock mySqlSelectQueryBlock) {
        SQLTableSource tableSource = mySqlSelectQueryBlock.getFrom();
        return getQueryPlanNode(mySqlSelectQueryBlock,tableSource);
    }

    private QueryPlan.Node getQueryPlanNode(MySqlSelectQueryBlock parent,SQLTableSource tableSource) {
        if(tableSource instanceof SQLExprTableSource) {
            return QueryPlan.Node.newNode(parent,getTableAlias());
        }
        //这种场景下join的表只能是SubQuery
        if(tableSource instanceof SQLJoinTableSource) {
            SQLJoinTableSource joinTableSource = (SQLJoinTableSource) tableSource;
            SQLTableSource left = joinTableSource.getLeft();
            QueryPlan.Node leftNode = getQueryPlanNode(parent,left);

            SQLTableSource right = joinTableSource.getRight();
            QueryPlan.Node rightNode = getQueryPlanNode(parent,right);

            MySqlSelectQueryBlock replacedSelect = getReplacedSelect(parent);
            QueryPlan.Node x = QueryPlan.Node.newNode(replacedSelect,getTableAlias());
            x.add(leftNode);
            x.add(rightNode);

            return x;
        }
        if(tableSource instanceof SQLSubqueryTableSource) {
            SQLSubqueryTableSource subQueryTableSource = (SQLSubqueryTableSource) tableSource;
            SQLSelect subQuerySelect = subQueryTableSource.getSelect();
            MySqlSelectQueryBlock subQueryBlock = (MySqlSelectQueryBlock) subQuerySelect.getQueryBlock();
            QueryPlan.Node node = getQueryPlanNode(subQueryBlock);

            MySqlSelectQueryBlock replacedSelect = getReplacedSelect(parent);
            QueryPlan.Node x = QueryPlan.Node.newNode(replacedSelect,subQueryTableSource.getAlias());
            x.add(node);

            return x;
        }

        throw new IllegalArgumentException();
    }

    private MySqlSelectQueryBlock getReplacedSelect(MySqlSelectQueryBlock originSelect) {
        MySqlSelectQueryBlock newSelect = originSelect.clone();
        SQLTableSource originTableSource = originSelect.getFrom();
        if(originTableSource instanceof SQLJoinTableSource) {
            SQLJoinTableSource originJoinTableSource = (SQLJoinTableSource)originTableSource;

            SQLJoinTableSource joinTableSource = ((SQLJoinTableSource) originTableSource).clone();
            joinTableSource.setLeft(originJoinTableSource.getLeft().getAlias(),null);
            joinTableSource.setRight(originJoinTableSource.getRight().getAlias(),null);

            newSelect.setFrom(joinTableSource);

            return newSelect;
        }
        if(originTableSource instanceof SubQueryTableSource) {
            SubQueryTableSource originSubQueryTableSource = (SubQueryTableSource)originTableSource;
            SQLExprTableSource newTableSource = new SQLExprTableSource(originSubQueryTableSource.getAlias());

            newSelect.setFrom(newTableSource);
            return newSelect;
        }
        throw new IllegalArgumentException();
    }

    private String getTableAlias() {
        return String.format("t_%s", RandomUtils.nextInt());
    }
}
