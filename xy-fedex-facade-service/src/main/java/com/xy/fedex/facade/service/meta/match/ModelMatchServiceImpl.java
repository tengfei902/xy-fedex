package com.xy.fedex.facade.service.meta.match;

import com.alibaba.druid.sql.ast.statement.*;
import com.xy.fedex.facade.exceptions.NoMetricModelMatchedException;
import com.xy.fedex.facade.catalog.AppHolder;
import com.xy.fedex.facade.service.meta.dto.QueryMatchedModelDTO;
import com.xy.fedex.facade.service.meta.match.filter.MetricModelReWriter;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author tengfei
 */
public class ModelMatchServiceImpl implements ModelMatchService {
    private List<MetricModelReWriter> filters;

    public List<MetricModelReWriter> getFilters() {
        return filters;
    }

    public void setFilters(List<MetricModelReWriter> filters) {
        this.filters = filters;
    }

    @Override
    public QueryMatchedModelDTO getMetricMatchedModels(SQLSelect select) {
        QueryMatchedModelDTO queryMatchedModelDTO = new QueryMatchedModelDTO(select);

        for (String metricAlias : queryMatchedModelDTO.getFields().getMetricAliases()) {
            AppHolder.Metric metric = queryMatchedModelDTO.getFields().getMetric(metricAlias);
            List<AppHolder.MetricModel> metricModels = metric.getMetricModels();
            //过滤模型
            metricModels = metricModels.stream().filter(metricModel -> checkMetricModelMatch(metricModel,queryMatchedModelDTO.getFields().getAllDims())).collect(Collectors.toList());
            if(CollectionUtils.isEmpty(metricModels)) {
                throw new NoMetricModelMatchedException(String.format("No metric model matched for metric:%s in query:%s",metricAlias,select.toString()));
            }
            queryMatchedModelDTO.addMetricMatchedModels(metricAlias, metricModels);
        }
        return queryMatchedModelDTO;
    }

    private boolean checkMetricModelMatch(AppHolder.MetricModel metricModel, List<String> dims) {
        if(CollectionUtils.isEmpty(dims)) {
            return true;
        }
        if(metricModel instanceof AppHolder.PrimaryMetricModel) {
            AppHolder.PrimaryMetricModel primaryMetricModel = (AppHolder.PrimaryMetricModel) metricModel;
            if(primaryMetricModel.isAssist()) {
                return true;
            }
            List<String> allowDims = CollectionUtils.isEmpty(primaryMetricModel.getAllowDims())?Collections.EMPTY_LIST:primaryMetricModel.getAllowDims();
            if(!allowDims.containsAll(dims)) {
                return false;
            }
            if(!CollectionUtils.isEmpty(primaryMetricModel.getForceDims()) && !new HashSet<>(dims).containsAll(primaryMetricModel.getForceDims())) {
                return false;
            }
        } else {
            AppHolder.DeriveMetricModel deriveMetricModel = (AppHolder.DeriveMetricModel) metricModel;
            List<AppHolder.MetricModel> relateMetricModels = deriveMetricModel.getRelateMetricModels();
            for(AppHolder.MetricModel relateMetricModel:relateMetricModels) {
                if(!checkMetricModelMatch(relateMetricModel,dims)) {
                    return false;
                }
            }
        }
        return true;
    }
}
