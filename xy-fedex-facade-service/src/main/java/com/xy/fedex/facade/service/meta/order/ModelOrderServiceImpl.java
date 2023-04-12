package com.xy.fedex.facade.service.meta.order;

import com.xy.fedex.facade.service.meta.dto.QueryMatchedModelDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModelOrderServiceImpl implements ModelOrderService {
    @Override
    public QueryMatchedModelDTO sortMetaMatchedModels(QueryMatchedModelDTO metricModelMatchDTO) {
        for(QueryMatchedModelDTO.MetricMatchedModelDTO metricMatchedModelDTO : metricModelMatchDTO.getMetricMatchedModelList()) {
            List<QueryMatchedModelDTO.MetricModel> metricModels = metricMatchedModelDTO.getMetricModels();

        }

        return metricModelMatchDTO;
    }
}
