package com.xy.fedex.facade.service.meta.match;

import com.alibaba.druid.sql.ast.statement.SQLSelect;
import com.xy.fedex.facade.service.meta.dto.QueryMatchedModelDTO;

public interface ModelMatchService {
    QueryMatchedModelDTO getMetricMatchedModels(SQLSelect logicalSelect);
}
