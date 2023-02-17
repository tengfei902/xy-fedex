package com.xy.fedex.facade.service.logical;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.statement.SQLSelect;
import org.springframework.stereotype.Service;

@Service
public class FedexLogicalServiceImpl implements FedexLogicalService {
    /**
     * 逻辑SQL重构,重构规则：
     * @param logicalSelect
     * @return
     */
    @Override
    public SQLSelect refactorLogicalSelect(SQLSelect logicalSelect) {
        return logicalSelect;
    }
}
