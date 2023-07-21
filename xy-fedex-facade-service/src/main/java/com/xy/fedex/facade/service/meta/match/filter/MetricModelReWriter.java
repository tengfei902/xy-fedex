package com.xy.fedex.facade.service.meta.match.filter;

import com.xy.fedex.catalog.common.definition.field.impl.MetricModel;
import com.xy.fedex.facade.service.meta.dto.QueryMatchedModelDTO;

import java.util.List;

/**
 * @author tengfei
 */
public interface MetricModelReWriter {
    QueryMatchedModelDTO rewrite(QueryMatchedModelDTO queryMatchedModels);
}
