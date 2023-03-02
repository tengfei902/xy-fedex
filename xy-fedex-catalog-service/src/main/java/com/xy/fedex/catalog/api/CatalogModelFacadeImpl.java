package com.xy.fedex.catalog.api;

import com.xy.fedex.catalog.api.dto.request.PrepareModelRequest;
import com.xy.fedex.catalog.api.dto.response.PrepareModelResponse;
import com.xy.fedex.catalog.common.definition.table.TableRelation;
import com.xy.fedex.catalog.service.TableService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

@DubboService(version = "${dubbo.server.version}")
public class CatalogModelFacadeImpl implements CatalogModelFacade {
    @Autowired
    private TableService tableService;

    @Override
    public PrepareModelResponse prepareModel(PrepareModelRequest prepareModelRequest) {
        TableRelation tableRelation = tableService.getTables(prepareModelRequest.getDsnId(), prepareModelRequest.getTableSource());
        PrepareModelResponse prepareModelResponse = new PrepareModelResponse();
        prepareModelResponse.setTableRelation();

        tableService.getTableFields();
        prepareModelResponse.setFields();
        return null;
    }
}
