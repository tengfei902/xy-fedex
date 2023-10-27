package com.xy.fedex.catalog.api;

import com.xy.fedex.catalog.common.definition.ModelDefinition;
import com.xy.fedex.def.Response;

public interface ModelFacade {
  Response<Boolean> saveModel(String ddl);

  Response<Boolean> saveModel(ModelDefinition modelDefinition);

  Response<ModelDefinition> getModel(String modelCode);
}
