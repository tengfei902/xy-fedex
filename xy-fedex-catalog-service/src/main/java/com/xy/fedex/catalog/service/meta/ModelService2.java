package com.xy.fedex.catalog.service.meta;

import com.xy.fedex.catalog.api.dto.ModelRequest;
import com.xy.fedex.catalog.api.dto.request.save.SaveModelRequest;
import com.xy.fedex.catalog.common.definition.ModelDefinition;

/**
 * @author tengfei
 */
public interface ModelService2 {

    ModelRequest getModelRequest(String tableSource);

    Long saveModel(SaveModelRequest modelRequest);

    void checkModelRequest(SaveModelRequest modelRequest);

    ModelDefinition getModel(Long modelId);
}
