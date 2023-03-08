package com.xy.fedex.catalog.api;

import com.xy.fedex.catalog.api.dto.ModelRequest;
import com.xy.fedex.def.Response;

public interface CatalogFacade {
    /**
     * 保存模型
     * @param modelRequest
     * @return
     */
    Response<Long> saveModel(ModelRequest modelRequest);
}
