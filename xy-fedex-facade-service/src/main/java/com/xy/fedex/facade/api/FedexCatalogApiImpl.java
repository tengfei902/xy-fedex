package com.xy.fedex.facade.api;


import com.xy.fedex.catalog.common.definition.field.Dim;
import com.xy.fedex.catalog.common.definition.field.Metric;
import com.xy.fedex.facade.api.request.OrthogonalRequest;
import com.xy.fedex.facade.api.response.OrthogonalMatrix;
import com.xy.fedex.facade.service.cs.AppHolder;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@DubboService(version = "${dubbo.server.version}")
public class FedexCatalogApiImpl implements FedexCatalogApi {
    @Autowired
    private AppHolder appHolder;
    /**
     * 元数据正交矩阵
     * @param orthogonalRequest
     * @return
     */
    @Override
    public OrthogonalMatrix getOrthogonalMatrix(OrthogonalRequest orthogonalRequest) {
        List<String> selectedMetrics = orthogonalRequest.getMetrics();
        List<String> selectedDims = orthogonalRequest.getDims();

        AppHolder.App app = appHolder.getApp(orthogonalRequest.getApp());
        List<AppHolder.Metric> metrics = app.getMetrics();


        List<Metric> candidateMetrics = new ArrayList<>();
        List<Dim> candidateDims = new ArrayList<>();
        return null;
    }

}
