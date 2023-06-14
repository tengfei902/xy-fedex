package com.xy.fedex.facade.service.cs;

import com.xy.fedex.catalog.api.dto.request.GetModelRequest;
import com.xy.fedex.catalog.common.definition.ModelDefinition;
import com.xy.fedex.def.Response;
import com.xy.fedex.facade.utils.ApplicationContextUtils;

public class ModelHolder {

//    private static CatalogModelFacade catalogModelFacade;

//    static {
//        catalogModelFacade = ApplicationContextUtils.getBean(CatalogModelFacade.class);
//    }

    public static ModelDefinition getModel(Long modelId) {
//        Response<ModelDefinition> response = catalogModelFacade.getModel(GetModelRequest.builder().modelId(modelId).build());
//        return response.getData();
        return null;
    }
}
