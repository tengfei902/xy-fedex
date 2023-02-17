package com.xy.fedex.facade.service;

import com.alibaba.druid.sql.ast.statement.*;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlSelectQueryBlock;
import com.xy.fedex.facade.service.execute.FedexExecuteService;
import com.xy.fedex.facade.service.execute.dto.QueryPlan;
import com.xy.fedex.facade.service.logical.FedexLogicalService;
import com.xy.fedex.facade.service.meta.FedexMetaService;
import com.xy.fedex.facade.service.meta.dto.PhysicalQueryPlan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class FedexQueryServiceImpl implements FedexQueryService {
    @Autowired
    private FedexLogicalService fedexLogicalService;
    @Autowired
    private FedexMetaService fedexMetaService;
    @Autowired
    private FedexExecuteService fedexExecuteService;

    @Override
    public List<Map<String, Object>> query(SQLSelect logicalSelect) {
        SQLSelect refactoredSelect = fedexLogicalService.refactorLogicalSelect(logicalSelect);
        QueryPlan queryPlan = new QueryPlan();
        addNode(queryPlan,refactoredSelect);
        return fedexExecuteService.execute(queryPlan);
    }

    private void addNode(QueryPlan queryPlan, SQLSelect select) {
        MySqlSelectQueryBlock mySqlSelectQueryBlock = (MySqlSelectQueryBlock) select.getQueryBlock();
        SQLTableSource sqlTableSource = mySqlSelectQueryBlock.getFrom();

        if (sqlTableSource instanceof SQLExprTableSource) {
            PhysicalQueryPlan physicalQueryPlan = fedexMetaService.getPhysicalSelects(select);
            queryPlan.add(physicalQueryPlan);
        }
        if (sqlTableSource instanceof SQLJoinTableSource) {
            SQLJoinTableSource sqlJoinTableSource = (SQLJoinTableSource) sqlTableSource;

            SQLTableSource left = sqlJoinTableSource.getLeft();
            SQLSubqueryTableSource leftSubQuery = (SQLSubqueryTableSource) left;
            addNode(queryPlan,leftSubQuery.getSelect());

            SQLTableSource right = sqlJoinTableSource.getRight();
            SQLSubqueryTableSource rightSubQuery = (SQLSubqueryTableSource) right;
            addNode(queryPlan,rightSubQuery.getSelect());
        }
        if (sqlTableSource instanceof SQLSubqueryTableSource) {
            SQLSubqueryTableSource sqlSubqueryTableSource = (SQLSubqueryTableSource) sqlTableSource;
            SQLSelect subSelect = sqlSubqueryTableSource.getSelect();
            addNode(queryPlan,subSelect);
        }
    }
}
