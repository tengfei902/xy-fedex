package com.xy.fedex.facade.service.logical;

import com.alibaba.druid.sql.ast.statement.SQLSelect;

public interface FedexLogicalService {
    SQLSelect refactorLogicalSelect(SQLSelect logicalSelect);
}
