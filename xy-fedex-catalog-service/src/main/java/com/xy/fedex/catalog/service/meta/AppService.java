package com.xy.fedex.catalog.service.meta;

import com.xy.fedex.catalog.api.dto.request.SaveAppRequest;
import com.xy.fedex.catalog.common.definition.AppDefinition;

/**
 * @author tengfei
 */
public interface AppService {
    /**
     * 保存应用
     * @param saveAppRequest
     * @return
     */
    Long saveApp(SaveAppRequest saveAppRequest);

    /**
     * 获取应用
     * @param appId
     * @return
     */
    AppDefinition getApp(Long appId);

    /**
     * 获取应用
     * @param bizLineId
     * @param appName
     * @return
     */
    AppDefinition getApp(Long bizLineId,String appName);
}
