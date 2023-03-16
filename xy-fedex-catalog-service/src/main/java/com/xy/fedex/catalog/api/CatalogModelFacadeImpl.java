package com.xy.fedex.catalog.api;

import com.xy.fedex.catalog.api.dto.ModelRequest;
import com.xy.fedex.catalog.api.dto.request.PrepareModelRequest;
import com.xy.fedex.catalog.api.dto.response.PrepareModelResponse;
import com.xy.fedex.catalog.common.definition.column.TableField;
import com.xy.fedex.catalog.common.definition.table.TableRelation;
import com.xy.fedex.catalog.service.meta.MetaService;
import com.xy.fedex.catalog.service.meta.ModelService;
import com.xy.fedex.catalog.service.meta.TableService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@DubboService(version = "${dubbo.server.version}")
public class CatalogModelFacadeImpl implements CatalogModelFacade {
    @Autowired
    private TableService tableService;
    @Autowired
    private MetaService metaService;
    @Autowired
    private ModelService modelService;

    @Override
    public PrepareModelResponse prepareModel(PrepareModelRequest prepareModelRequest) {
        TableRelation tableRelation = tableService.getTables(prepareModelRequest.getDsnId(), prepareModelRequest.getTableSource());
        PrepareModelResponse prepareModelResponse = new PrepareModelResponse();
        prepareModelResponse.setTableRelation(tableRelation);

        Long bizLineId = prepareModelRequest.getBizLineId();
        Long dsnId = prepareModelRequest.getDsnId();
        List<TableField> tableFields = tableService.getTableFields(bizLineId,dsnId,tableRelation,prepareModelRequest.getFields());
        List<TableField> matchedMetaFields = metaService.matchMeta(bizLineId,tableFields);
        prepareModelResponse.setFields(matchedMetaFields);
        return prepareModelResponse;
    }

    @Override
    public Long saveModel(ModelRequest modelRequest) {
        //用户权限判断
        Long bizLineId = modelRequest.getBizLineId();
        Long dsnId = modelRequest.getDsnId();

        return modelService.saveModel(modelRequest);
    }
}
