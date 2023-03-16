package com.xy.fedex.catalog.api;

import com.xy.fedex.catalog.api.dto.ModelRequest;
import com.xy.fedex.catalog.api.dto.request.PrepareModelRequest;
import com.xy.fedex.catalog.api.dto.response.PrepareModelResponse;

public interface CatalogModelFacade {

    PrepareModelResponse prepareModel(PrepareModelRequest prepareModelRequest);

    Long saveModel(ModelRequest modelRequest);
}
