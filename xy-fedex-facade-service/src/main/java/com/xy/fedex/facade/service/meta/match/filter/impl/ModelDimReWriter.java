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
public class ModelDimReWriter implements MetricModelReWriter {
    @Override
    public List<MetricModel> rewrite(List<MetricModel> metricModels, List<String> dims) {
        return metricModels.stream().filter(metricModel -> checkMetricModelMatchDims(metricModel,dims)).collect(Collectors.toList());
    }

    private boolean checkMetricModelMatchDims(MetricModel metricModel, List<String> dims) {
        if (metricModel instanceof PrimaryMetricModel) {
            PrimaryMetricModel primaryMetricModel = (PrimaryMetricModel) metricModel;
            if (!CollectionUtils.isEmpty(primaryMetricModel.getAdvanceCalculate().getForceDims()) && !new HashSet<>(dims).containsAll(primaryMetricModel.getAdvanceCalculate().getForceDims())) {
                return false;
            }
            if (CollectionUtils.isEmpty(dims)) {
                return true;
            }
            if (primaryMetricModel.getAdvanceCalculate().isAssist()) {
                return true;
            }
            return new HashSet<>(metricModel.getAdvanceCalculate().getAllowDims()).containsAll(dims);
        } else {
            DeriveMetricModel deriveMetricModel = (DeriveMetricModel) metricModel;
            List<MetricModel> relateMetricModels = deriveMetricModel.getRelateMetricModels();
            for (MetricModel relateMetricModel : relateMetricModels) {
                if (!checkMetricModelMatchDims(relateMetricModel, dims)) {
                    return false;
                }
            }
            return true;
        }
    }
}
