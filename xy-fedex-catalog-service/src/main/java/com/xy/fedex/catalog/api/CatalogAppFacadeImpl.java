package com.xy.fedex.catalog.api;

import com.xy.fedex.catalog.api.dto.request.GetAppRequest;
import com.xy.fedex.catalog.api.dto.request.SaveAppRequest;
import com.xy.fedex.catalog.common.definition.AppDefinition;
import com.xy.fedex.catalog.service.meta.AppService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

@DubboService(version = "${dubbo.server.version}")
public class CatalogAppFacadeImpl implements CatalogAppFacade {
    @Autowired
    private AppService appService;

    @Override
    public AppDefinition getApp(GetAppRequest request) {
        if(Objects.isNull(request.getAppId())) {
            throw new IllegalArgumentException("appId cannot be null in getApp");
        }
        return appService.getApp(request.getBizLineId(),request.getAppId());
    }

    @Override
    public Long saveApp(SaveAppRequest request) {
        return null;
    }
}
