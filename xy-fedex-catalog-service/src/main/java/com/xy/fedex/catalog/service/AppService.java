package com.xy.fedex.catalog.service;

import com.xy.fedex.catalog.api.dto.AppRequest;
import com.xy.fedex.catalog.common.definition.AppDefinition;

public interface AppService {

    Long saveApp(AppRequest appRequest);

    AppDefinition getApp(Long appId);
}
