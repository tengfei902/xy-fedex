package com.xy.fedex.catalog.utils;

import com.google.common.base.Joiner;
import com.xy.fedex.catalog.api.dto.DimModelRequest;
import com.xy.fedex.catalog.api.dto.MetricModelRequest;
import com.xy.fedex.catalog.api.dto.ModelRequest;
import com.xy.fedex.catalog.dto.DimDTO;
import com.xy.fedex.catalog.dto.MetricDTO;
import com.xy.fedex.catalog.exception.ModelDefException;
import com.xy.fedex.catalog.service.meta.MetaService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

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
                metrics.add(String.format("%s as %s",metricModel.getFormula(),metric.getMetricCode()));
            });
        }
        List<DimModelRequest> dimModels = modelRequest.getDimModels();
        List<String> dims = new ArrayList<>();
        List<String> dimAliasList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(dimModels)) {
            dimModels.forEach(dimModelRequest -> {
                DimDTO dim = metaService.getDim(dimModelRequest.getDimId());
                dims.add(String.format("%s as %s",dimModelRequest.getFormula(),dim.getDimCode()));
                dimAliasList.add(dim.getDimCode());
            });
        }

        List<String> sqlParts = new ArrayList<>();
        sqlParts.add("select");
        sqlParts.add(mergeMetricAndDim(Joiner.on(",").join(metrics),Joiner.on(",").join(dims)));
        sqlParts.add("from");
        sqlParts.add(modelRequest.getTableSource());
        if(!StringUtils.isEmpty(modelRequest.getCondition())) {
            sqlParts.add("where");
            sqlParts.add(modelRequest.getCondition());
        }
        sqlParts.add("group by");
        sqlParts.add(Joiner.on(",").join(dimAliasList));
        return Joiner.on(" ").join(sqlParts);
    }

    private static String mergeMetricAndDim(String metric,String dim) {
        if(StringUtils.isEmpty(metric) && StringUtils.isEmpty(dim)) {
            throw new ModelDefException("metric and dim empty");
        }
        if(StringUtils.isEmpty(metric)) {
            return dim;
        }
        if(StringUtils.isEmpty(dim)) {
            return metric;
        }
        return String.format("%s,%s",metric,dim);
    }
}
