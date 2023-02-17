package com.xy.fedex.facade.service.meta.order;

import com.xy.fedex.facade.service.meta.dto.QueryMatchedModelDTO;

public interface ModelOrderService {

    QueryMatchedModelDTO sortMetaMatchedModels(QueryMatchedModelDTO metricModelMatchDTO);
}
