package com.xy.fedex.catalog.api;

import com.alibaba.druid.DbType;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.expr.SQLCharExpr;
import com.alibaba.druid.sql.ast.expr.SQLIdentifierExpr;
import com.alibaba.druid.sql.ast.statement.*;
import com.alibaba.druid.sql.dialect.hive.stmt.HiveCreateTableStatement;
import com.xy.fedex.catalog.api.dto.request.list.GetAppRequest;
import com.xy.fedex.catalog.api.dto.request.SaveAppRequest;
import com.xy.fedex.catalog.api.dto.request.list.ListDimModelRequest;
import com.xy.fedex.catalog.api.dto.request.list.ListMetricModelRequest;
import com.xy.fedex.catalog.api.dto.request.list.ListMetricRequest;
import com.xy.fedex.catalog.api.dto.request.save.SaveModelRequest;
import com.xy.fedex.catalog.api.dto.request.save.field.dim.SaveDimModelRequest;
import com.xy.fedex.catalog.api.dto.request.save.field.metric.SaveDimRequest;
import com.xy.fedex.catalog.api.dto.request.save.field.metric.SaveMetricRequest;
import com.xy.fedex.catalog.api.dto.request.save.field.metric.SavePrimaryMetricModelRequest;
import com.xy.fedex.catalog.api.dto.response.list.ListResult;
import com.xy.fedex.catalog.common.constants.RpcConstants;
import com.xy.fedex.catalog.common.definition.AppDefinition;
import com.xy.fedex.catalog.common.definition.ModelDefinition;
import com.xy.fedex.catalog.common.definition.field.Metric;
import com.xy.fedex.catalog.common.definition.field.impl.DimModel;
import com.xy.fedex.catalog.common.definition.field.impl.MetricModel;
import com.xy.fedex.catalog.constants.Constants;
import com.xy.fedex.catalog.dto.*;
import com.xy.fedex.catalog.exception.DimNotFoundException;
import com.xy.fedex.catalog.exception.MetaNotFoundException;
import com.xy.fedex.catalog.exception.MetricNotFoundException;
import com.xy.fedex.catalog.service.containers.MetricHolder;
import com.xy.fedex.catalog.service.meta.AppService;
import com.xy.fedex.catalog.service.meta.MetaService;
import com.xy.fedex.catalog.service.meta.MetricModelService;
import com.xy.fedex.catalog.service.meta.ModelService;
import com.xy.fedex.catalog.utils.CatalogUtils;
import com.xy.fedex.def.Response;
import com.xy.fedex.dsl.exceptions.SQLExprNotSupportException;
import com.xy.fedex.dsl.utility.SQLExprUtils;
import com.xy.fedex.rpc.context.Tracer;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.rpc.RpcContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author tengfei
 */
@Component
@DubboService(version = "${dubbo.server.version}")
public class CatalogFacadeImpl implements CatalogFacade {
    @Autowired
    private ModelService modelService;
    @Autowired
    private MetaService metaService;
    @Autowired
    private MetricHolder metricHolder;
    @Autowired
    private AppService appService;
    @Autowired
    private MetricModelService metricModelService;

    @Override
    public Response<Long> execute(String sql) {
        List<SQLStatement> statements = SQLUtils.parseStatements(sql,DbType.hive);
        for(SQLStatement statement:statements) {
            if(statement.toString().toLowerCase().startsWith("create")) {
                HiveCreateTableStatement hiveCreateTableStatement = (HiveCreateTableStatement) statement;
                String schema = hiveCreateTableStatement.getSchema();

                SchemaDTO schemaDTO = CatalogUtils.getMetaObjectType(schema);
                switch (schemaDTO.getMetaObjectType()) {
                    case APP:
                        Long appId = saveApp(hiveCreateTableStatement);
                        return Response.success(appId);
                    case MODEL:
                        Long modelId = saveModel(hiveCreateTableStatement);
                        return Response.success(modelId);
                }

            }
            throw new SQLExprNotSupportException("sql not support:"+sql);
        }
        return null;
    }

    @Override
    public Long saveApp(String appDDL) {
        SQLStatement statement = SQLUtils.parseSingleStatement(appDDL, DbType.hive);
        HiveCreateTableStatement hiveCreateTableStatement = (HiveCreateTableStatement) statement;
        return saveApp(hiveCreateTableStatement);
    }

    private Long saveApp(HiveCreateTableStatement statement) {
        SaveAppRequest saveAppRequest = new SaveAppRequest();
        saveAppRequest.setAppName(statement.getTableName());

        String tenantId = RpcContext.getContext().getAttachment(RpcConstants.TENANT_ID);
        saveAppRequest.setTenantId(tenantId);

        String accountId = RpcContext.getContext().getAttachment(RpcConstants.ACCOUNT_ID);
        saveAppRequest.setCreator(accountId);

        saveAppRequest.setAppComment(((SQLCharExpr)statement.getComment()).getText());

        SchemaDTO schemaDTO = CatalogUtils.getMetaObjectType(statement.getSchema());
        saveAppRequest.setBizLineId(schemaDTO.getBizLineId());

        Map<String,String> tblPropertyMap = SQLExprUtils.getTblPropertyValue(statement.getTblProperties());
        String relateModelIds = tblPropertyMap.get(Constants.RELATE_MODEL_IDS);
        if(!StringUtils.isEmpty(relateModelIds)) {
            saveAppRequest.setRelateModelIds(Arrays.asList(relateModelIds.split(",")).stream().map(s -> Long.parseLong(s)).collect(Collectors.toList()));
        }
        List<SQLTableElement> tableElements = statement.getTableElementList();
        for(SQLTableElement tableElement:tableElements) {
            SQLColumnDefinition sqlColumnDefinition = (SQLColumnDefinition) tableElement;
            String columnCode = sqlColumnDefinition.getName().getSimpleName();
            String columnName = ((SQLCharExpr)sqlColumnDefinition.getComment()).getText();
            String columnComment = ((SQLCharExpr)sqlColumnDefinition.getComment()).getText();
            String sqlDataType = sqlColumnDefinition.getDataType().toString();
            DimDTO dim = metaService.getDim(saveAppRequest.getBizLineId(),columnCode);
            if(!Objects.isNull(dim)) {
                SaveAppRequest.Dim appDim = new SaveAppRequest.Dim();
                appDim.setDimId(dim.getDimId());
                appDim.setDimCode(columnCode);
                appDim.setDimName(columnName);
                appDim.setDimComment(columnComment);
                appDim.setDimFormat(sqlDataType);
                if(Objects.isNull(saveAppRequest.getDims())) {
                    saveAppRequest.setDims(new ArrayList<>());
                }
                saveAppRequest.getDims().add(appDim);
                continue;
            }
            MetricDTO metric = metaService.getMetric(saveAppRequest.getBizLineId(),columnCode);
            if(!Objects.isNull(metric)) {
                SaveAppRequest.Metric appMetric = new SaveAppRequest.Metric();
                appMetric.setMetricId(metric.getMetricId());
                appMetric.setMetricCode(columnCode);
                appMetric.setMetricName(columnName);
                appMetric.setMetricComment(columnComment);
                appMetric.setMetricFormat(sqlDataType);
                if(Objects.isNull(saveAppRequest.getMetrics())) {
                    saveAppRequest.setMetrics(new ArrayList<>());
                }
                saveAppRequest.getMetrics().add(appMetric);
                continue;
            }
            throw new MetaNotFoundException(String.format("metric or dim not found,biz_line_id:%s,code:%s",saveAppRequest.getBizLineId(),columnName));
        }
        return appService.saveApp(saveAppRequest);
    }

    @Override
    public Response<AppDefinition> getApp(GetAppRequest request) {
        if(!Objects.isNull(request.getAppId())) {
            AppDefinition app = appService.getApp(request.getAppId());
            return Response.success(app);
        } else {
            AppDefinition app = appService.getApp(request.getBizLineId(),request.getAppName());
            return Response.success(app);
        }
    }

    @Override
    public Long saveModel(String modelDefinition) {
        HiveCreateTableStatement createTableStatement = SQLExprUtils.parseCreateTable(modelDefinition);
        return saveModel(createTableStatement);
    }

    private Long saveModel(HiveCreateTableStatement createTableStatement) {
        //dsn
        String schema = createTableStatement.getSchema();
        SchemaDTO schemaDTO = CatalogUtils.getMetaObjectType(schema);
        //table
        String tableName = createTableStatement.getTableName();
        //comment
        SQLExpr commentExpr = createTableStatement.getComment();
        //tbl properties
        List<SQLAssignItem> tblProperties = createTableStatement.getTblProperties();
        Map<String,String> tableParams = getTableProps(tblProperties);
        //creator
        String accountId = RpcContext.getContext().getAttachment(RpcConstants.ACCOUNT_ID);

        SQLSelect select = createTableStatement.getSelect();

        SaveModelRequest modelRequest = new SaveModelRequest();
        modelRequest.setModelName(tableName);
        //todo dsn
        modelRequest.setDsnId(0L);
        if(!Objects.isNull(commentExpr)) {
            modelRequest.setModelComment(((SQLCharExpr)commentExpr).getText());
        }
        modelRequest.setCreator(accountId);
        modelRequest.setBizLineId(schemaDTO.getBizLineId());
        initMetaFields(select.getQueryBlock(),modelRequest);

        return modelService.saveModel(modelRequest);
    }
    private Map<String,String> getTableProps(List<SQLAssignItem> tblProperties) {
        Map<String,String> params = new HashMap<>();
        for(SQLAssignItem item:tblProperties) {
            String key = ((SQLIdentifierExpr)item.getTarget()).getName();
            String value = ((SQLCharExpr)item.getValue()).getText();
            params.put(key,value);
        }
        return params;
    }

    private void initMetaFields(SQLSelectQueryBlock sqlSelectQueryBlock, SaveModelRequest modelRequest) {
        SQLTableSource tableSource = sqlSelectQueryBlock.getFrom();
        if(tableSource instanceof SQLSubqueryTableSource) {
            return;
        }
        if(tableSource instanceof SQLUnionQueryTableSource) {
            SQLUnionQueryTableSource sqlUnionQueryTableSource = (SQLUnionQueryTableSource) tableSource;
            SQLUnionQuery sqlUnionQuery = sqlUnionQueryTableSource.getUnion();
            List<SQLSelectQuery> sqlSelectQueries = sqlUnionQuery.getChildren();
            return;
        }

        List<SQLSelectItem> selectItems = sqlSelectQueryBlock.getSelectList();
        SQLSelectGroupByClause sqlSelectGroupByClause = sqlSelectQueryBlock.getGroupBy();
        List<String> groupByItems = sqlSelectGroupByClause.getItems().stream().map(Object::toString).collect(Collectors.toList());
        SQLExpr condition = sqlSelectQueryBlock.getWhere();

        modelRequest.setTableSource(tableSource.toString());
        if(!Objects.isNull(condition)) {
            modelRequest.setCondition(condition.toString());
        }

        modelRequest.setMetricModels(new ArrayList<>());
        modelRequest.setDimModels(new ArrayList<>());
        for(SQLSelectItem selectItem:selectItems) {
            SQLExpr selectItemExpr = selectItem.getExpr();
            String code = selectItem.getAlias().replaceAll("`","");

            if(groupByItems.contains(code) || groupByItems.contains(selectItemExpr.toString())) {
                //dim
                SaveDimModelRequest dimModel = new SaveDimModelRequest();
                dimModel.setModelId(modelRequest.getModelId());

                DimDTO dim = metaService.getDim(modelRequest.getBizLineId(),code);
                if(Objects.isNull(dim)) {
                    throw new DimNotFoundException(String.format("dim not found,bizLineId:%s,dimCode:%s",modelRequest.getBizLineId(),code));
                }
                dimModel.setDimId(dim.getDimId());
                dimModel.setFormula(selectItemExpr.toString());
                dimModel.setCode(code);

                modelRequest.getDimModels().add(dimModel);
            } else {
                SavePrimaryMetricModelRequest metricModel = new SavePrimaryMetricModelRequest();
                MetricDTO metric = metaService.getMetric(modelRequest.getBizLineId(),code);
                if(Objects.isNull(metric)) {
                    throw new MetricNotFoundException(String.format("metric not found,bizLineId:%s,metricCode:%s",modelRequest.getBizLineId(),code));
                }

                metricModel.setMetricId(metric.getMetricId());
                metricModel.setCode(code);
                metricModel.setFormula(selectItemExpr.toString());
                SavePrimaryMetricModelRequest.AdvanceCalculate advanceCalculate = new SavePrimaryMetricModelRequest.AdvanceCalculate();
                metricModel.setAdvanceCalculate(advanceCalculate);

                modelRequest.getMetricModels().add(metricModel);
            }
        }
    }

    @Override
    public Response<ModelDefinition> getModel(Long modelId) {
        ModelDefinition modelDefinition = modelService.getModel(modelId);
        return Response.success(modelDefinition);
    }

    @Override
    public Long saveMetric(SaveMetricRequest saveMetricRequest) {
        String accountId = Tracer.getAccountId();
        String tenantId = Tracer.getTenantId();
        MetricDTO metricDTO = MetricDTO.builder()
                .bizLineId(saveMetricRequest.getBizLineId())
                .tenantId(tenantId)
                .accountId(accountId)
                .metricCode(saveMetricRequest.getMetricCode())
                .metricName(saveMetricRequest.getMetricName())
                .metricComment(saveMetricRequest.getMetricComment())
                .build();

        return metaService.saveMetric(metricDTO);
    }

    @Override
    public Long saveDim(SaveDimRequest saveDimRequest) {
        String accountId = Tracer.getAccountId();
        String tenantId = Tracer.getTenantId();
        DimDTO dimDTO = DimDTO.builder()
                .dimCode(saveDimRequest.getDimCode())
                .dimName(saveDimRequest.getDimName())
                .bizLineId(saveDimRequest.getBizLineId())
                .dimComment(saveDimRequest.getDimComment())
                .dimFamilyId(saveDimRequest.getDimFamilyId())
                .master(saveDimRequest.getMaster())
                .creator(accountId)
                .tenantId(tenantId)
                .build();
        return metaService.saveDim(dimDTO);
    }

    @Override
    public ListResult<Metric> getMetrics(ListMetricRequest listMetricRequest) {
        return null;
    }

    @Override
    public Response<ListResult<MetricModel>> getMetricModels(ListMetricModelRequest listMetricModelRequest) {
        List<Long> modelIds = getModelIds(listMetricModelRequest.getAppId(), listMetricModelRequest.getModelId());
        MetricModelRequest metricModelRequest = MetricModelRequest.builder().modelIds(modelIds).metricId(listMetricModelRequest.getMetricId()).build();
        List<MetricModel> metricModels = metricModelService.getMetricModels(metricModelRequest);
        Integer totalCnt = metricModelService.getMetricModelCnt(metricModelRequest);
        return Response.success(ListResult.newPage(totalCnt,listMetricModelRequest.getOffset(),listMetricModelRequest.getLimit(),metricModels));
    }

    private List<Long> getModelIds(Long appId,Long modelId) {
        if(Objects.isNull(appId) && Objects.isNull(modelId)) {
            throw new IllegalArgumentException("appId and modelId cannot neither be null");
        }
        if(Objects.isNull(appId)) {
            return Arrays.asList(modelId);
        }
        AppDefinition app = appService.getApp(appId);
        if(Objects.isNull(modelId)) {
            return app.getModelIds();
        }

        if(app.getModelIds().contains(modelId)) {
            return Arrays.asList(modelId);
        }

        throw new IllegalArgumentException(String.format("modelId:%s not related to app:%s",modelId,appId));
    }

    @Override
    public Response<ListResult<DimModel>> getDimModels(ListDimModelRequest listDimModelRequest) {
        List<Long> modelIds = getModelIds(listDimModelRequest.getAppId(), listDimModelRequest.getModelId());
        DimModelRequest dimModelRequest = new DimModelRequest(modelIds, listDimModelRequest.getDimId());
        metaService.getDimModels(dimModelRequest);
        return null;
    }
}
