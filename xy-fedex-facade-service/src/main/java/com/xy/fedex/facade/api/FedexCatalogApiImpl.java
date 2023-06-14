package com.xy.fedex.facade.api;


import com.xy.fedex.catalog.common.definition.field.DimModel;
import com.xy.fedex.catalog.common.definition.field.MetricModel;
import com.xy.fedex.facade.api.request.OrthogonalRequest;
import com.xy.fedex.facade.api.response.OrthogonalMatrix;
import com.xy.fedex.facade.service.cs.AppHolder;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.ArrayList;
import java.util.List;

@DubboService(version = "${dubbo.server.version}")
public class FedexCatalogApiImpl implements FedexCatalogApi {
    /**
     * 元数据正交矩阵
     * @param orthogonalRequest
     * @return
     */
    @Override
    public OrthogonalMatrix getOrthogonalMatrix(OrthogonalRequest orthogonalRequest) {
        List<String> selectedMetrics = orthogonalRequest.getMetrics();
        List<String> selectedDims = orthogonalRequest.getDims();

        AppHolder.App app = AppHolder.getApp(orthogonalRequest.getApp());
        List<AppHolder.Metric> metrics = app.getMetrics();


        List<MetricModel> candidateMetrics = new ArrayList<>();
        List<DimModel> candidateDims = new ArrayList<>();
        return null;
    }

}
