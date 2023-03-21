package com.xy.fedex.facade.api;

import com.xy.fedex.facade.api.request.OrthogonalRequest;
import com.xy.fedex.facade.api.response.OrthogonalMatrix;

public interface FedexCatalogApi {
    /**
     * 元数据正交矩阵
     * @param orthogonalRequest
     * @return
     */
    OrthogonalMatrix getOrthogonalMatrix(OrthogonalRequest orthogonalRequest);
}
