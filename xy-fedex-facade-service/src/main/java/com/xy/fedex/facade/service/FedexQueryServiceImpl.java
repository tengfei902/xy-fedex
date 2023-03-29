package com.xy.fedex.facade.service;

import com.alibaba.druid.sql.ast.statement.*;
import com.xy.fedex.facade.service.execute.FedexExecuteService;
import com.xy.fedex.facade.service.logical.FedexLogicalService;
import com.xy.fedex.facade.service.plan.QueryPlanService;
import com.xy.fedex.facade.service.plan.dto.LogicalPlan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class FedexQueryServiceImpl implements FedexQueryService {
    @Autowired
    private FedexLogicalService fedexLogicalService;
    @Autowired
    private FedexExecuteService fedexExecuteService;
    @Autowired
    private QueryPlanService queryPlanService;

    @Override
    public List<Map<String, Object>> query(SQLSelect logicalSelect) {
        SQLSelect refactoredSelect = fedexLogicalService.refactorLogicalSelect(logicalSelect);
        LogicalPlan logicalPlan = queryPlanService.getLogicalPlan(refactoredSelect);
        return null;
//        logicalPlan.lrd().stream().forEach(node -> );
//        return fedexExecuteService.execute(queryPlan);
    }
}
