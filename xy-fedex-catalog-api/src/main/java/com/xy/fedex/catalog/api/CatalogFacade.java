package com.xy.fedex.catalog.api;

import com.xy.fedex.catalog.api.dto.request.list.GetAppRequest;
import com.xy.fedex.catalog.api.dto.request.list.ListDimModelRequest;
import com.xy.fedex.catalog.api.dto.request.list.ListMetricModelRequest;
import com.xy.fedex.catalog.api.dto.request.list.ListMetricRequest;
import com.xy.fedex.catalog.api.dto.request.save.field.metric.SaveDimRequest;
import com.xy.fedex.catalog.api.dto.request.save.field.metric.SaveMetricRequest;
import com.xy.fedex.catalog.api.dto.response.list.ListResult;
import com.xy.fedex.catalog.common.definition.AppDefinition;
import com.xy.fedex.catalog.common.definition.ModelDefinition;
import com.xy.fedex.catalog.common.definition.field.Metric;
import com.xy.fedex.catalog.common.definition.field.impl.DimModel;
import com.xy.fedex.catalog.common.definition.field.impl.MetricModel;
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
     * @param request
     * @return
     */
    Response<AppDefinition> getApp(GetAppRequest request);
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
    Response<ModelDefinition> getModel(Long modelId);

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

    /**
     * 指标列表
     * @param listMetricRequest
     * @return
     */
    ListResult<Metric> getMetrics(ListMetricRequest listMetricRequest);

    /**
     * metric model列表
     * @param listMetricModelRequest
     * @return
     */
    Response<ListResult<MetricModel>> getMetricModels(ListMetricModelRequest listMetricModelRequest);

    /**
     * dim model列表
     * @return
     */
    Response<ListResult<DimModel>> getDimModels(ListDimModelRequest listDimModelRequest);
}
