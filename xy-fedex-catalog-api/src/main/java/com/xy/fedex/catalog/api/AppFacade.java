package com.xy.fedex.catalog.api;

import com.xy.fedex.catalog.api.dto.request.GetAppRequest;
import com.xy.fedex.catalog.api.dto.request.SaveAppRequest;
import com.xy.fedex.catalog.api.dto.request.save.field.metric.SaveDeriveMetricModelRequest;
import com.xy.fedex.catalog.api.dto.response.Page;
import com.xy.fedex.catalog.common.definition.AppDefinition;
import com.xy.fedex.def.Response;

/**
 * @author tengfei
 */
public interface AppFacade {
    /**
     * 查询应用
     * @param getAppRequest
     * @return
     */
    Response<AppDefinition> getApp(GetAppRequest getAppRequest);

    /**
     * 查询应用列表
     * @param getAppRequest
     * @return
     */
    Response<Page<AppDefinition>> getAppList(GetAppRequest getAppRequest);

    /**
     * 保存应用
     * @param saveAppRequest
     * @return
     */
    Response<Boolean> saveApp(SaveAppRequest saveAppRequest);

    /**
     * save derive metric model
     * @return
     */
    Response<Boolean> saveDeriveMetricModel(SaveDeriveMetricModelRequest saveDeriveMetricModelRequest);
}
