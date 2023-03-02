package com.xy.fedex.facade.service.meta.match;

import com.alibaba.druid.sql.ast.statement.SQLSelect;
import com.xy.fedex.catalog.common.definition.field.Metric;
import com.xy.fedex.dsl.utility.SQLExprUtils;
import com.xy.fedex.facade.exceptions.NoModelMatchedForMetricException;
import com.xy.fedex.facade.service.cs.CatalogService;
import com.xy.fedex.facade.service.cs.dto.MetaContainer;
import com.xy.fedex.facade.service.meta.dto.QueryMatchedModelDTO;
import com.xy.fedex.facade.service.meta.match.filter.MetricMatchedModelReWriter;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ModelMatchServiceImpl implements ModelMatchService {
    @Autowired
    private CatalogService catalogService;

    private List<MetricMatchedModelReWriter> filters;

    @Override
    public QueryMatchedModelDTO getMetaMatchedModels(SQLSelect logicalSelect) {
        QueryMatchedModelDTO queryMatchedModelDTO = getQueryMatchedModelsByDim(logicalSelect);
        for(MetricMatchedModelReWriter rewriter:filters) {
            queryMatchedModelDTO = rewriter.rewrite(queryMatchedModelDTO);
        }
        return queryMatchedModelDTO;
    }

    /**
     * 指标维度正交关系匹配初始化
     *
     * @param logicalSelect
     * @return
     */
    private QueryMatchedModelDTO getQueryMatchedModelsByDim(SQLSelect logicalSelect) {
        Long appId = Long.parseLong(SQLExprUtils.getTableSource(logicalSelect));
        List<String> metrics = SQLExprUtils.getMetrics(logicalSelect);
        List<String> dims = SQLExprUtils.getDims(logicalSelect);

        QueryMatchedModelDTO queryMatchedModelDTO = new QueryMatchedModelDTO(logicalSelect);

        for (String metric : metrics) {
            MetaContainer.MetricDTO metricDTO = MetaContainer.getApp(appId).getMetric(metric);
            List<Metric.MetricModel> metricModels = metricDTO.getMetricModels();

            List<Metric.MetricModel> matchedMetricModels = metricModels.stream().filter(metricModel -> {
                if(CollectionUtils.isEmpty(metricModel.getAllowDims()) && CollectionUtils.isEmpty(dims)) {
                    return true;
                }
                if(!CollectionUtils.isEmpty(metricModel.getForceDims())) {
                    if(!dims.containsAll(metricModel.getForceDims())) {
                        return false;
                    }
                }
                return metricModel.getAllowDims().containsAll(dims);
            }).collect(Collectors.toList());

            if(CollectionUtils.isEmpty(matchedMetricModels)) {
                throw new NoModelMatchedForMetricException(metric,dims,logicalSelect.toString());
            }

            queryMatchedModelDTO.addMetricMatchedModels(metric,metricDTO.getMetric(),matchedMetricModels);
        }

        return queryMatchedModelDTO;
    }

    private Long getAppId(SQLSelect logicalSelect) {
        return null;
    }


}
