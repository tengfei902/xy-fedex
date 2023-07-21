package com.xy.fedex.facade.service.meta.match.filter.impl;

import com.xy.fedex.catalog.common.definition.field.impl.DeriveMetricModel;
import com.xy.fedex.catalog.common.definition.field.impl.MetricModel;
import com.xy.fedex.catalog.common.definition.field.impl.PrimaryMetricModel;
import com.xy.fedex.facade.service.meta.dto.QueryMatchedModelDTO;
import com.xy.fedex.facade.service.meta.match.filter.AbstractMetricModelReWriter;
import com.xy.fedex.facade.service.meta.match.filter.MetricModelReWriter;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ModelDimReWriter extends AbstractMetricModelReWriter {
    @Override
    public List<QueryMatchedModelDTO.MetricModel> getMatchedMetricModels(QueryMatchedModelDTO.SelectFields fields, List<QueryMatchedModelDTO.MetricModel> matchedMetricModels) {
        return null;
    }
}
