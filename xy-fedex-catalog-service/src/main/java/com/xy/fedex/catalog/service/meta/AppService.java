package com.xy.fedex.catalog.service.meta;

import com.xy.fedex.catalog.api.dto.request.SaveAppRequest;
import com.xy.fedex.catalog.common.definition.AppDefinition;

public interface AppService {

    Long saveApp(SaveAppRequest saveAppRequest);

    AppDefinition getApp(Long bizLineId, Long appId);
}
