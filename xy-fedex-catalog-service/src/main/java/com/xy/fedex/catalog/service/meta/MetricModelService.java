package com.xy.fedex.catalog.service.meta;

import com.xy.fedex.catalog.api.dto.request.list.ListMetricModelRequest;
import com.xy.fedex.catalog.common.definition.field.impl.MetricModel;
import com.xy.fedex.catalog.dto.MetricModelRequest;

import java.util.List;

/**
 * @author tengfei
 */
public interface MetricModelService {
    /**
     * metric model列表
     * @param request
     * @return
     */
    List<MetricModel> getMetricModels(MetricModelRequest request);

    /**
     * metric model数量
     * @param request
     * @return
     */
    Integer getMetricModelCnt(MetricModelRequest request);

    /**
     * 保存metric model
     * @param metricModels
     */
    void saveMetricModel(List<MetricModel> metricModels);
}
