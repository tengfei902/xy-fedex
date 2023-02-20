package com.xy.fedex.catalog.service.impl;

import com.alibaba.druid.sql.ast.statement.*;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlSelectQueryBlock;
import com.google.gson.Gson;
import com.xy.fedex.admin.api.FedexDsnFacade;
import com.xy.fedex.catalog.api.dto.DimModelRequest;
import com.xy.fedex.catalog.api.dto.MetricModelRequest;
import com.xy.fedex.catalog.api.dto.ModelRequest;
import com.xy.fedex.catalog.dao.DimModelDao;
import com.xy.fedex.catalog.dao.MetricModelDao;
import com.xy.fedex.catalog.dao.ModelDao;
import com.xy.fedex.catalog.dao.ModelTableRelationDao;
import com.xy.fedex.catalog.exception.ModelNotFoundException;
import com.xy.fedex.catalog.exception.UpdateFailedException;
import com.xy.fedex.catalog.po.DimModelPO;
import com.xy.fedex.catalog.po.MetricModelPO;
import com.xy.fedex.catalog.po.ModelPO;
import com.xy.fedex.catalog.po.ModelTableRelationPO;
import com.xy.fedex.catalog.service.ModelService;
import com.xy.fedex.catalog.utils.ModelUtils;
import com.xy.fedex.dsl.utility.SQLExprUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ModelServiceImpl implements ModelService {
    @Autowired
    private ModelDao modelDao;
    @Autowired
    private MetricModelDao metricModelDao;
    @Autowired
    private DimModelDao dimModelDao;
    @Autowired
    private ModelTableRelationDao modelTableRelationDao;
//    @Autowired
//    @DubboReference
    private FedexDsnFacade fedexDsnFacade;

    @Transactional
    @Override
    public void createModel(ModelRequest modelRequest) {
        //save model
        ModelPO model = saveOrUpdateModel(modelRequest);
        //save metric model
        saveOrUpdateMetricModels(model.getId(), modelRequest);
        //save dim model
        saveOrUpdateDimModels(model.getId(), modelRequest.getDimModels());
        //save model table relation
        saveModelTableRelation(model.getId(), modelRequest);
    }

    @Override
    public void createModel(String sql) {

    }

    private ModelPO saveOrUpdateModel(ModelRequest modelRequest) {
        Long modelId = modelRequest.getModelId();
        if (!Objects.isNull(modelId)) {
            return updateModel(modelRequest);
        } else {
            return saveModel(modelRequest);
        }
    }

    private void saveModelTableRelation(Long modelId, ModelRequest modelRequest) {
        String modelDefinition = ModelUtils.getModelDefinition(modelRequest);
        SQLSelect sqlSelect = SQLExprUtils.parse(modelDefinition);
        MySqlSelectQueryBlock mySqlSelectQueryBlock = (MySqlSelectQueryBlock) sqlSelect.getQueryBlock();

        SQLTableSource tableSource = mySqlSelectQueryBlock.getFrom();
    }

    private void saveModelTableRelation(Long modelId, SQLTableSource tableSource) {
        if (tableSource instanceof SQLExprTableSource) {
            SQLExprTableSource sqlExprTableSource = (SQLExprTableSource) tableSource;
            String tableName = sqlExprTableSource.getTableName();
            String schemaName = sqlExprTableSource.getSchema();
            String tableAlias = sqlExprTableSource.getAlias();
            //判断表是否存在
            //判断表是否包含需要的字段
            //判断别名是否唯一
            ModelTableRelationPO modelTableRelation = new ModelTableRelationPO();
            modelTableRelation.setModelId(modelId);
            modelTableRelation.setSchemaName(schemaName);
            modelTableRelation.setTableName(tableName);
            modelTableRelation.setTableAlias(tableAlias);
            modelTableRelationDao.insertSelective(modelTableRelation);
        }
        if (tableSource instanceof SQLJoinTableSource) {
            SQLJoinTableSource sqlJoinTableSource = (SQLJoinTableSource) tableSource;
            SQLTableSource left = sqlJoinTableSource.getLeft();
            SQLTableSource right = sqlJoinTableSource.getRight();

            saveModelTableRelation(modelId, left);
            saveModelTableRelation(modelId, right);
        }
        if (tableSource instanceof SQLSubqueryTableSource) {
            SQLSubqueryTableSource sqlSubqueryTableSource = (SQLSubqueryTableSource) tableSource;
            SQLSelect subQuerySelect = sqlSubqueryTableSource.getSelect();

        }
    }

    private ModelPO updateModel(ModelRequest modelRequest) {
        ModelPO modelPO = modelDao.selectByPrimaryKey(modelRequest.getModelId());
        if (Objects.isNull(modelPO)) {
            throw new ModelNotFoundException("model not found:" + modelRequest.getModelId());
        }
        ModelPO updateModel = new ModelPO();
        updateModel.setId(modelRequest.getModelId());
        updateModel.setModelName(modelRequest.getModelName());
        updateModel.setModelDesc(modelRequest.getModelDesc());
        if (!Objects.isNull(modelRequest.getModelProp())) {
            updateModel.setModelProp(new Gson().toJson(modelRequest.getModelProp()));
        }
        modelDao.updateByPrimaryKeySelective(updateModel);
        return modelDao.selectByPrimaryKey(modelRequest.getModelId());
    }

    private ModelPO saveModel(ModelRequest modelRequest) {
        ModelPO modelPO = new ModelPO();
        modelPO.setModelName(modelRequest.getModelName());
        modelPO.setModelDesc(modelRequest.getModelDesc());
        modelPO.setBizLineId(modelRequest.getBizLineId());
        modelPO.setTenantId(modelRequest.getTenantId());
        modelPO.setDsnId(modelRequest.getDsnId());
        modelPO.setCreator(modelRequest.getCreator());
        modelPO.setModelProp(new Gson().toJson(modelRequest.getModelProp()));
        modelDao.insertSelective(modelPO);

        return modelDao.selectByPrimaryKey(modelPO.getId());
    }

    private void saveOrUpdateMetricModels(Long modelId, ModelRequest modelRequest) {
        List<MetricModelRequest> metricModelRequests = modelRequest.getMetricModels();
        if (CollectionUtils.isEmpty(metricModelRequests)) {
            return;
        }
        List<MetricModelRequest> newMetricModels = metricModelRequests.stream().filter(metricModelRequest -> Objects.isNull(metricModelRequest.getMetricModelId())).collect(Collectors.toList());
        List<MetricModelRequest> updateMetricModels = metricModelRequests.stream().filter(metricModelRequest -> !Objects.isNull(metricModelRequest.getMetricModelId())).collect(Collectors.toList());

        saveMetricModels(modelId, newMetricModels);
        updateMetricModels(updateMetricModels);
    }

    private void saveMetricModels(Long modelId, List<MetricModelRequest> metricModels) {
        if (CollectionUtils.isEmpty(metricModels)) {
            return;
        }
        List<MetricModelPO> metricModelPOs = metricModels.stream().map(metricModelRequest -> {
            MetricModelPO metricModelPO = new MetricModelPO();
            metricModelPO.setModelId(modelId);
            metricModelPO.setFormula(metricModelRequest.getFormula());
            metricModelPO.setAdvanceCaculate(new Gson().toJson(Optional.ofNullable(metricModelRequest.getAdvanceCalculate()).get()));
            metricModelPO.setCondition(metricModelRequest.getCondition());
            metricModelPO.setAllowDims(new Gson().toJson(Optional.ofNullable(metricModelRequest.getAllowDims()).get()));
            metricModelPO.setForceDims(new Gson().toJson(Optional.ofNullable(metricModelRequest.getForceDims()).get()));
            metricModelPO.setOrderBy(metricModelRequest.getOrderBy());
            return metricModelPO;
        }).collect(Collectors.toList());
        metricModelDao.batchInsert(metricModelPOs);
    }

    private void updateMetricModels(List<MetricModelRequest> metricModels) {
        if (CollectionUtils.isEmpty(metricModels)) {
            return;
        }
        List<MetricModelPO> metricModelList = metricModels.stream().map(metricModelRequest -> {
            MetricModelPO updateMetricModel = new MetricModelPO();
            updateMetricModel.setId(metricModelRequest.getMetricModelId());
            updateMetricModel.setFormula(metricModelRequest.getFormula());
            updateMetricModel.setCondition(metricModelRequest.getCondition());
            updateMetricModel.setAllowDims(new Gson().toJson(Optional.ofNullable(metricModelRequest.getAllowDims()).get()));
            updateMetricModel.setForceDims(new Gson().toJson(Optional.ofNullable(metricModelRequest.getForceDims()).get()));
            updateMetricModel.setOrderBy(metricModelRequest.getOrderBy());
            updateMetricModel.setAdvanceCaculate(new Gson().toJson(Optional.ofNullable(metricModelRequest.getAdvanceCalculate()).get()));
            return updateMetricModel;
        }).collect(Collectors.toList());

        metricModelList.stream().forEach(metricModelPO -> {
            int cnt = metricModelDao.updateByPrimaryKeySelective(metricModelPO);
            if (cnt != 1) {
                throw new UpdateFailedException(String.format("update metric model failed,MetricModelId:%s", metricModelPO.getId()));
            }
        });
    }

    private void saveOrUpdateDimModels(Long modelId, List<DimModelRequest> dimModelRequests) {
        if (CollectionUtils.isEmpty(dimModelRequests)) {
            return;
        }
        List<DimModelRequest> newDimModelRequests = dimModelRequests.stream().filter(dimModelRequest -> Objects.isNull(dimModelRequest.getDimModelId())).collect(Collectors.toList());
        List<DimModelRequest> updateDimModelRequests = dimModelRequests.stream().filter(dimModelRequest -> !Objects.isNull(dimModelRequest.getDimModelId())).collect(Collectors.toList());

        saveDimModels(modelId, newDimModelRequests);
    }

    private void saveDimModels(Long modelId, List<DimModelRequest> dimModelRequests) {
        if (CollectionUtils.isEmpty(dimModelRequests)) {
            return;
        }
        List<DimModelPO> dimModelPOs = dimModelRequests.stream().map(dimModelRequest -> {
            DimModelPO dimModel = new DimModelPO();
            dimModel.setDimId(dimModelRequest.getDimId());
            dimModel.setModelId(modelId);
            dimModel.setFormula(dimModelRequest.getFormula());
            return dimModel;
        }).collect(Collectors.toList());

        dimModelDao.batchInsert(dimModelPOs);
    }

    private void updateDimModels(List<DimModelRequest> dimModelRequests) {
        if (CollectionUtils.isEmpty(dimModelRequests)) {
            return;
        }
        dimModelRequests.stream().forEach(dimModelRequest -> {

        });
    }

    /**
     * ModelRequest校验
     * 1.权限校验
     * 2.参数校验
     * @param modelRequest
     */
    @Override
    public void checkModelRequest(ModelRequest modelRequest) {
        //权限判断
        //参数校验
        String select = ModelUtils.getModelDefinition(modelRequest);
    }
}
