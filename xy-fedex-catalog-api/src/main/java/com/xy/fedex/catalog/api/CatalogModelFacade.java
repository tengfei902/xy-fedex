package com.xy.fedex.catalog.api;

import com.xy.fedex.catalog.api.dto.ModelRequest;
import com.xy.fedex.catalog.api.dto.request.GetModelRequest;
import com.xy.fedex.catalog.api.dto.request.PrepareModelRequest;
import com.xy.fedex.catalog.api.dto.response.PrepareModelResponse;
import com.xy.fedex.catalog.common.definition.ModelDefinition;
import com.xy.fedex.def.Response;

public interface CatalogModelFacade {

    PrepareModelResponse prepareModel(PrepareModelRequest prepareModelRequest);

    Long saveModel(ModelRequest modelRequest);

    Response<ModelDefinition> getModel(GetModelRequest request);
}
