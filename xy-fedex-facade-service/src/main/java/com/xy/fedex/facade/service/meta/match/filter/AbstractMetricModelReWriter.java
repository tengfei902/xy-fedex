package com.xy.fedex.facade.service.meta.match.filter;

import com.xy.fedex.catalog.common.definition.field.impl.MetricModel;
import com.xy.fedex.facade.exceptions.NoMetricModelMatchedException;
import com.xy.fedex.facade.service.meta.dto.QueryMatchedModelDTO;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

public abstract class AbstractMetricModelReWriter implements MetricModelReWriter {
    @Override
    public QueryMatchedModelDTO rewrite(QueryMatchedModelDTO queryMatchedModels) {
        for(QueryMatchedModelDTO.MetricMatchedModelDTO metricMatchedModelDTO:queryMatchedModels.getMetricMatchedModelList()) {
            List<QueryMatchedModelDTO.MetricModel> matchedMetricModels = getMatchedMetricModels(queryMatchedModels.getFields(),metricMatchedModelDTO.getMetricModels());
            if(CollectionUtils.isEmpty(matchedMetricModels)) {
                throw new NoMetricModelMatchedException("no metric model matched for metric:"+metricMatchedModelDTO.getMetricCode());
            }
            metricMatchedModelDTO.setMetricModels(matchedMetricModels);
        }
        return queryMatchedModels;
    }

    public abstract List<QueryMatchedModelDTO.MetricModel> getMatchedMetricModels(QueryMatchedModelDTO.SelectFields fields,List<QueryMatchedModelDTO.MetricModel> matchedMetricModels);
}
