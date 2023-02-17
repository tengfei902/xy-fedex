package com.xy.fedex.facade.service.meta.match.filter;

import com.xy.fedex.facade.service.meta.dto.QueryMatchedModelDTO;

public interface MetricMatchedModelReWriter {
    QueryMatchedModelDTO rewrite(QueryMatchedModelDTO queryMatchedModelDTO);
}
