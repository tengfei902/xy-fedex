package com.xy.fedex.facade.service.meta.match.filter.impl;

import com.xy.fedex.catalog.common.definition.field.impl.DeriveMetricModel;
import com.xy.fedex.catalog.common.definition.field.impl.MetricModel;
import com.xy.fedex.catalog.common.definition.field.impl.PrimaryMetricModel;
import com.xy.fedex.facade.service.meta.match.filter.MetricModelReWriter;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ModelForceDimReWriter implements MetricModelReWriter {
    @Override
    public List<MetricModel> rewrite(List<MetricModel> metricModels, List<String> dims) {
        return metricModels.stream().filter(metricModel -> checkForDims(metricModel,dims)).collect(Collectors.toList());
    }

    private boolean checkForDims(MetricModel metricModel,List<String> dims) {
        if(CollectionUtils.isEmpty(dims)) {
            return true;
        }
        if(metricModel instanceof PrimaryMetricModel) {
            return new HashSet<>(metricModel.getAdvanceCalculate().getAllowDims()).containsAll(dims);
        } else {
            DeriveMetricModel deriveMetricModel = (DeriveMetricModel) metricModel;
            List<MetricModel> relateMetricModels = deriveMetricModel.getRelateMetricModels();
            for(MetricModel relateMetricModel:relateMetricModels) {
                if(!checkForDims(relateMetricModel,dims)) {
                    return false;
                }
            }
            return true;
        }
    }
}
