package com.xy.fedex.catalog.service;

import com.xy.fedex.catalog.api.dto.request.SaveAppRequest;
import com.xy.fedex.catalog.common.definition.AppDefinition;
import com.xy.fedex.catalog.dto.request.AppRequest;

/**
 * @author tengfei
 */
public interface AppService {
    /**
     * 保存应用
     * @param appRequest
     * @return
     */
    Long saveApp(AppRequest appRequest);

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
