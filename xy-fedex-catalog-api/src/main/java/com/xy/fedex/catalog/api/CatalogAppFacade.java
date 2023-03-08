package com.xy.fedex.catalog.api;

import com.xy.fedex.catalog.api.dto.request.GetAppRequest;
import com.xy.fedex.catalog.api.dto.request.SaveAppRequest;
import com.xy.fedex.catalog.common.definition.AppDefinition;

public interface CatalogAppFacade {
    AppDefinition getApp(GetAppRequest request);

    Long saveApp(SaveAppRequest request);
}
