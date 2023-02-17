package com.xy.fedex.facade.service;

import com.alibaba.druid.sql.ast.statement.SQLSelect;

import java.util.List;
import java.util.Map;

public interface FedexQueryService {

    List<Map<String,Object>> query(SQLSelect logicalSelect);
}
