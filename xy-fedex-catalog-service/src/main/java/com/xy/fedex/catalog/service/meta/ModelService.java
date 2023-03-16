package com.xy.fedex.catalog.service.meta;

import com.xy.fedex.catalog.api.dto.ModelRequest;
import com.xy.fedex.catalog.common.definition.ModelDefinition;

public interface ModelService {

    ModelRequest getModelRequest(String tableSource);

    Long saveModel(ModelRequest modelRequest);

    void checkModelRequest(ModelRequest modelRequest);

    ModelDefinition getModel(Long modelId);
}
