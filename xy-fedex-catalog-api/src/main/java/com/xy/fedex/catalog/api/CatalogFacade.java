package com.xy.fedex.catalog.api;

import com.xy.fedex.catalog.api.dto.request.list.ListMetricRequest;
import com.xy.fedex.catalog.api.dto.request.save.field.metric.SaveDimRequest;
import com.xy.fedex.catalog.api.dto.request.save.field.metric.SaveMetricRequest;
import com.xy.fedex.catalog.api.dto.response.list.ListResult;
import com.xy.fedex.catalog.common.definition.AppDefinition;
import com.xy.fedex.catalog.common.definition.ModelDefinition;
import com.xy.fedex.catalog.common.definition.field.Metric;
import com.xy.fedex.def.Response;

/**
 * @author tengfei
 */
public interface CatalogFacade {
    /**
     *
     * @param sql
     * @return
     */
    Response<Long> execute(String sql);
    /**
     * 创建应用
     * @param appDDL
     * @return
     */
    Long saveApp(String appDDL);
    /**
     * 获取应用
     * @param appId
     * @return
     */
    Response<AppDefinition> getApp(Long appId);
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
