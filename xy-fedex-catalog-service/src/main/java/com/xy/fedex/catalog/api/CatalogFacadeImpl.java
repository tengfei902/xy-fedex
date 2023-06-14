package com.xy.fedex.catalog.api;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.expr.SQLCharExpr;
import com.alibaba.druid.sql.ast.expr.SQLIdentifierExpr;
import com.alibaba.druid.sql.ast.statement.*;
import com.alibaba.druid.sql.dialect.hive.stmt.HiveCreateTableStatement;
import com.xy.fedex.catalog.api.dto.request.list.ListMetricRequest;
import com.xy.fedex.catalog.api.dto.request.save.SaveModelRequest;
import com.xy.fedex.catalog.api.dto.request.save.field.dim.SaveDimModelRequest;
import com.xy.fedex.catalog.api.dto.request.save.field.metric.SaveDimRequest;
import com.xy.fedex.catalog.api.dto.request.save.field.metric.SaveMetricRequest;
import com.xy.fedex.catalog.api.dto.request.save.field.metric.SavePrimaryMetricModelRequest;
import com.xy.fedex.catalog.api.dto.response.list.ListResult;
import com.xy.fedex.catalog.common.constants.RpcConstants;
import com.xy.fedex.catalog.common.definition.ModelDefinition;
import com.xy.fedex.catalog.common.definition.field.DimModel;
import com.xy.fedex.catalog.common.definition.field.Metric;
import com.xy.fedex.catalog.common.definition.field.MetricModel;
import com.xy.fedex.catalog.dto.DimDTO;
import com.xy.fedex.catalog.dto.MetricDTO;
import com.xy.fedex.catalog.service.containers.MetricHolder;
import com.xy.fedex.catalog.service.meta.MetaService;
import com.xy.fedex.catalog.service.meta.ModelService;
import com.xy.fedex.dsl.utility.SQLExprUtils;
import com.xy.fedex.rpc.context.Tracer;
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
    @Override
    public Long saveModel(String modelDefinition) {
        HiveCreateTableStatement createTableStatement = SQLExprUtils.parseCreateTable(modelDefinition);
        //dsn
        String schema = createTableStatement.getSchema();
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
        modelRequest.setDsnId(1000L);
        if(!Objects.isNull(commentExpr)) {
            modelRequest.setModelComment(((SQLCharExpr)commentExpr).getText());
        }
        modelRequest.setCreator(accountId);
        modelRequest.setBizLineId(Long.parseLong(schema.replace("biz_line_","")));
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
                dimModel.setDimId(dim.getDimId());
                dimModel.setFormula(selectItemExpr.toString());
                dimModel.setCode(code);

                modelRequest.getDimModels().add(dimModel);
            } else {
                SavePrimaryMetricModelRequest metricModel = new SavePrimaryMetricModelRequest();
                MetricDTO metric = metaService.getMetric(modelRequest.getBizLineId(),code);

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
    public ModelDefinition getModel(Long modelId) {
        return null;
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
}
