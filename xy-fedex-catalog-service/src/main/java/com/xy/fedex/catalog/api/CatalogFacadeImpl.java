package com.xy.fedex.catalog.api;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.statement.SQLTableSource;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlSelectQueryBlock;
import com.xy.fedex.catalog.api.dto.DimModelRequest;
import com.xy.fedex.catalog.api.dto.MetricModelRequest;
import com.xy.fedex.catalog.api.dto.ModelRequest;
import com.xy.fedex.catalog.api.dto.request.PrepareModelRequest;
import com.xy.fedex.catalog.api.dto.response.PrepareModelResponse;
import com.xy.fedex.catalog.common.definition.ModelDefinition;
import com.xy.fedex.catalog.common.definition.field.Dim;
import com.xy.fedex.catalog.common.definition.field.Metric;
import com.xy.fedex.catalog.service.meta.ModelService;
import com.xy.fedex.catalog.service.meta.TableService;
import com.xy.fedex.catalog.utils.ModelUtils;
import com.xy.fedex.def.Response;
import com.xy.fedex.dsl.utility.SQLExprUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

@DubboService(version = "${dubbo.server.version}")
public class CatalogFacadeImpl implements CatalogFacade {
    @Autowired
    private ModelService modelService;
    @Autowired
    private TableService tableService;

    @Override
    public Response<Long> saveModel(ModelRequest modelRequest) {
        //校验
        //model
        String modelSelect = ModelUtils.getModelDefinition(modelRequest);
        MySqlSelectQueryBlock mySqlSelectQueryBlock = (MySqlSelectQueryBlock) SQLExprUtils.parse(modelSelect).getQueryBlock();
        SQLTableSource tableSource = mySqlSelectQueryBlock.getFrom();
        SQLExpr condition = mySqlSelectQueryBlock.getWhere();
        return Response.success(modelService.saveModel(modelRequest));
    }
}
