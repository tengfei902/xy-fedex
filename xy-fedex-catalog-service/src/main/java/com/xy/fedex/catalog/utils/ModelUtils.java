package com.xy.fedex.catalog.utils;

import com.google.common.base.Joiner;
import com.xy.fedex.catalog.api.dto.DimModelRequest;
import com.xy.fedex.catalog.api.dto.MetricModelRequest;
import com.xy.fedex.catalog.api.dto.ModelRequest;
import com.xy.fedex.catalog.dto.DimDTO;
import com.xy.fedex.catalog.dto.MetricDTO;
import com.xy.fedex.catalog.service.MetaService;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class ModelUtils {

    private static MetaService metaService;

    static {
        metaService = SpringContextUtils.getBean(MetaService.class);
    }

    public static String getModelDefinition(ModelRequest modelRequest) {
        List<MetricModelRequest> metricModels = modelRequest.getMetricModels();
        List<String> metrics = new ArrayList<>();
        if(!CollectionUtils.isEmpty(metricModels)) {
            metricModels.forEach(metricModel -> {
                MetricDTO metric = metaService.getMetric(metricModel.getMetricId());
                metrics.add(String.format("%s as %s",metricModel.getFormula(),metric.getMetricName()));
            });
        }
        List<DimModelRequest> dimModels = modelRequest.getDimModels();
        List<String> dims = new ArrayList<>();
        List<String> dimAliasList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(dimModels)) {
            dimModels.forEach(dimModelRequest -> {
                DimDTO dim = metaService.getDim(dimModelRequest.getDimId());
                dims.add(String.format("%s as %s",dimModelRequest.getFormula(),dim.getDimName()));
                dimAliasList.add(dim.getDimName());
            });
        }

        List<String> sqlParts = new ArrayList<>();
        sqlParts.add("select");
        sqlParts.add(Joiner.on(",").join(metrics));
        sqlParts.add(Joiner.on(",").join(dims));
        sqlParts.add("from");
        sqlParts.add(modelRequest.getTableSource());
        sqlParts.add("where");
        sqlParts.add(modelRequest.getCondition());
        sqlParts.add("group by");
        sqlParts.add(Joiner.on(",").join(dimAliasList));
        return Joiner.on(" ").join(sqlParts);
    }
}
