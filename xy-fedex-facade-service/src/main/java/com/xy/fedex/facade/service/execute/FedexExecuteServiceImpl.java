package com.xy.fedex.facade.service.execute;

import com.xy.fedex.facade.service.execute.dto.Digraph;
import com.xy.fedex.facade.service.execute.dto.QueryPlan;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class FedexExecuteServiceImpl implements FedexExecuteService {
    @Override
    public List<Map<String, Object>> execute(QueryPlan queryPlan) {
        return null;
    }
}
