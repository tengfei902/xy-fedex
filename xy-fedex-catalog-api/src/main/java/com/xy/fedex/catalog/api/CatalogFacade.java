package com.xy.fedex.catalog.api;

import com.xy.fedex.catalog.api.dto.request.list.ListMetricRequest;
import com.xy.fedex.catalog.api.dto.request.save.field.metric.SaveDimRequest;
import com.xy.fedex.catalog.api.dto.request.save.field.metric.SaveMetricRequest;
import com.xy.fedex.catalog.api.dto.response.list.ListResult;
import com.xy.fedex.catalog.common.definition.ModelDefinition;
import com.xy.fedex.catalog.common.definition.field.Metric;

/**
 * @author tengfei
 */
public interface CatalogFacade {
    /**
     * 创建数据模型
     * @param modelDDL
     * @return
     */
    Long saveModel(String modelDDL);

    /**
     * 查询模型
     * @param modelId
     * @return
     */
    ModelDefinition getModel(Long modelId);

    /**
     * 保存原子指标
     * @return
     */
    Long saveMetric(SaveMetricRequest saveMetricRequest);

    /**
     * 保存维度
     * @return
     */
    Long saveDim(SaveDimRequest saveDimRequest);

    ListResult<Metric> getMetrics(ListMetricRequest listMetricRequest);
}
