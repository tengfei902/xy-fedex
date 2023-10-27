package com.xy.fedex.catalog.api;

import com.alibaba.druid.DbType;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.expr.SQLCharExpr;
import com.alibaba.druid.sql.ast.expr.SQLIdentifierExpr;
import com.alibaba.druid.sql.ast.statement.SQLAssignItem;
import com.alibaba.druid.sql.dialect.hive.stmt.HiveCreateTableStatement;
import com.xy.fedex.catalog.api.conf.VarConf;
import com.xy.fedex.catalog.common.definition.ModelDefinition;
import com.xy.fedex.catalog.common.definition.field.impl.DimModel;
import com.xy.fedex.catalog.common.definition.field.impl.MetricModel;
import com.xy.fedex.catalog.dto.ModelDTO;
import com.xy.fedex.catalog.exception.CatalogServiceExceptions;
import com.xy.fedex.catalog.exception.ErrorCode;
import com.xy.fedex.catalog.service.DimService;
import com.xy.fedex.catalog.service.MetricService;
import com.xy.fedex.catalog.service.ModelService;
import com.xy.fedex.catalog.utils.CatalogUtils;
import com.xy.fedex.def.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@DubboService(version = "${dubbo.server.version}")
public class ModelFacadeImpl implements ModelFacade {
  @Autowired
  private ModelService modelService;
  @Autowired
  private MetricService metricService;
  @Autowired
  private DimService dimService;

  /**
   * 创建model
   * @param ddl
   * @return
   */
  @Override
  public Response<Boolean> saveModel(String ddl) {
    SQLStatement sqlStatement = SQLUtils.parseSingleStatement(ddl, DbType.hive);
    HiveCreateTableStatement hiveCreateTableStatement = (HiveCreateTableStatement) sqlStatement;

    List<SQLAssignItem> assignItems = hiveCreateTableStatement.getTblProperties();
    Map<String,String> modelParams = getParamMap(assignItems);
    ModelDTO modelDTO = new ModelDTO();
    modelDTO.setModelCode(hiveCreateTableStatement.getTableName());
    modelDTO.setModelName(VarConf.getParamValue(VarConf.MODEL_SHOW_NAME.getVarName(),modelParams));
    modelDTO.setModelComment(getModelComment(hiveCreateTableStatement.getComment()));
    modelDTO.setDsnCode(VarConf.getParamValue(VarConf.MODEL_DSN.getVarName(),modelParams));
    modelDTO.setCreator(CatalogUtils.getCurrentUserName());
    modelDTO.setModelParams(modelParams);
    modelDTO.setTableSource(hiveCreateTableStatement.getSelect());
    modelService.saveModel(modelDTO);

    return Response.success(true);
  }

  private String getModelComment(SQLExpr sqlExpr) {
    if(Objects.isNull(sqlExpr)) {
      return "";
    }
    SQLCharExpr sqlCharExpr = (SQLCharExpr) sqlExpr;
    return StringUtils.isEmpty(sqlCharExpr.getText())?"":sqlCharExpr.getText();
  }

  private Map<String,String> getParamMap(List<SQLAssignItem> assignItems) {
    Map<String,String> params = new HashMap<>();
    assignItems.forEach(sqlAssignItem -> {
      SQLIdentifierExpr sqlIdentifierExpr = (SQLIdentifierExpr) sqlAssignItem.getTarget();
      SQLCharExpr sqlCharExpr = (SQLCharExpr) sqlAssignItem.getValue();
      params.put(sqlIdentifierExpr.getName(), sqlCharExpr.getText());
    });
    return params;
  }

  @Override
  public Response<Boolean> saveModel(ModelDefinition modelDefinition) {
    return null;
  }

  @Override
  public Response<ModelDefinition> getModel(String modelCode) {
    ModelDTO modelDTO = modelService.getModelByCode(modelCode);
    if(Objects.isNull(modelDTO)) {
      throw CatalogServiceExceptions.newException(ErrorCode.MODEL_NOT_FOUND_FAILED,CatalogUtils.getCurrentProject(),modelCode);
    }
    ModelDefinition modelDefinition = new ModelDefinition();
    modelDefinition.setModelCode(modelDTO.getModelCode());
    modelDefinition.setModelName(modelDTO.getModelName());
    modelDefinition.setModelComment(modelDTO.getModelComment());
    modelDefinition.setModelProp(modelDTO.getModelParams());
    modelDefinition.setCreator(modelDTO.getCreator());
    modelDefinition.setTableSource(modelDTO.getTableSource().toString());
    modelDefinition.setCreateTime(modelDTO.getCreateTime());
    modelDefinition.setUpdateTime(modelDTO.getUpdateTime());
    modelDefinition.setVersion(null);

    List<MetricModel> metricModels = metricService.getMetricModels(modelCode);
    modelDefinition.setMetrics(metricModels);

    List<DimModel> dimModels = dimService.getDimModels(modelCode);
    modelDefinition.setDims(dimModels);

    return Response.success(modelDefinition);
  }
}
