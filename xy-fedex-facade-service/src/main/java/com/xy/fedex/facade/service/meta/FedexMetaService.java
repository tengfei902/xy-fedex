package com.xy.fedex.facade.service.meta;

import com.alibaba.druid.sql.ast.statement.SQLSelect;
import com.xy.fedex.facade.service.meta.dto.PhysicalQueryPlan;

public interface FedexMetaService {

    PhysicalQueryPlan getPhysicalSelects(SQLSelect logicalSelect);

    void getMetaOrthogonality();
}
