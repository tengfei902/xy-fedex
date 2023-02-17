package com.xy.fedex.facade.service.execute;

import com.xy.fedex.facade.service.execute.dto.Digraph;
import com.xy.fedex.facade.service.execute.dto.QueryPlan;

import java.util.List;
import java.util.Map;

public interface FedexExecuteService {

    List<Map<String,Object>> execute(QueryPlan queryPlan);
}
