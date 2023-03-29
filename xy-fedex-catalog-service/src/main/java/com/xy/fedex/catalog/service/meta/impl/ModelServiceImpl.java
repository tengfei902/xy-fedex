package com.xy.fedex.catalog.service.meta.impl;

import com.alibaba.druid.sql.ast.statement.*;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlSelectQueryBlock;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.xy.fedex.admin.api.FedexDsnFacade;
import com.xy.fedex.catalog.api.dto.DimModelRequest;
import com.xy.fedex.catalog.api.dto.MetricModelRequest;
import com.xy.fedex.catalog.api.dto.ModelRequest;
import com.xy.fedex.catalog.common.definition.ModelDefinition;
import com.xy.fedex.catalog.common.definition.field.AdvanceCalculate;
import com.xy.fedex.catalog.common.enums.MetricType;
import com.xy.fedex.catalog.dao.*;
import com.xy.fedex.catalog.exception.DimNotFoundException;
import com.xy.fedex.catalog.exception.MetricNotFoundException;
import com.xy.fedex.catalog.exception.ModelNotFoundException;
import com.xy.fedex.catalog.exception.UpdateFailedException;
import com.xy.fedex.catalog.po.*;
import com.xy.fedex.catalog.service.meta.ModelService;
import com.xy.fedex.catalog.utils.ModelUtils;
import com.xy.fedex.dsl.utility.SQLExprUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
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
    @Autowired
    private MetricDao metricDao;
    @Autowired
    private DimDao dimDao;
    //    @Autowired
//    @DubboReference
    private FedexDsnFacade fedexDsnFacade;

    /**
     * 根据选择的表获取模型初始化状态
     * @param tableSource
     * @return
     */
    @Override
    public ModelRequest getModelRequest(String tableSource) {
        //获取所有表
        //获取表字段
        //表字段与指标、维度匹配
        return null;
    }

    @Transactional
    @Override
    public Long saveModel(ModelRequest modelRequest) {
        //save model
        ModelPO model = saveOrUpdateModel(modelRequest);
        //save metric model
        saveOrUpdateMetricModels(model.getId(), modelRequest);
        //save dim model
        saveOrUpdateDimModels(model.getId(), modelRequest);
        //save model table relation
        saveModelTableRelation(model.getId(), modelRequest);
        return model.getId();
    }

    private ModelPO saveOrUpdateModel(ModelRequest modelRequest) {
        Long modelId = modelRequest.getModelId();
        if (!Objects.isNull(modelId)) {
            return updateModel(modelRequest);
        } else {
            return createModel(modelRequest);
        }
    }

    private void saveModelTableRelation(Long modelId, ModelRequest modelRequest) {
        String modelDefinition = ModelUtils.getModelDefinition(modelRequest);
        SQLSelect sqlSelect = SQLExprUtils.parse(modelDefinition);
        MySqlSelectQueryBlock mySqlSelectQueryBlock = (MySqlSelectQueryBlock) sqlSelect.getQueryBlock();

        SQLTableSource tableSource = mySqlSelectQueryBlock.getFrom();
        saveModelTableRelation(modelId, tableSource);
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
            MySqlSelectQueryBlock subQuerySelectBlock = (MySqlSelectQueryBlock) subQuerySelect.getQueryBlock();
            saveModelTableRelation(modelId, subQuerySelectBlock.getFrom());
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
        updateModel.setModelDesc(modelRequest.getModelComment());
        if (!Objects.isNull(modelRequest.getModelProp())) {
            updateModel.setModelProp(new Gson().toJson(modelRequest.getModelProp()));
        }
        modelDao.updateByPrimaryKeySelective(updateModel);
        return modelDao.selectByPrimaryKey(modelRequest.getModelId());
    }

    private ModelPO createModel(ModelRequest modelRequest) {
        ModelPO modelPO = new ModelPO();
        modelPO.setModelName(modelRequest.getModelName());
        modelPO.setModelDesc(modelRequest.getModelComment());
        modelPO.setBizLineId(modelRequest.getBizLineId());
        modelPO.setDsnId(modelRequest.getDsnId());
        modelPO.setTableSource(modelRequest.getTableSource());
        modelPO.setCondition(modelRequest.getCondition());
        modelPO.setModelProp(new Gson().toJson(modelRequest.getModelProp()));
        modelDao.insertSelective(modelPO);

        return modelDao.selectByPrimaryKey(modelPO.getId());
    }

    private void saveOrUpdateMetricModels(Long modelId, ModelRequest modelRequest) {
        List<MetricModelRequest> metricModelRequests = modelRequest.getMetricModels();
        if (CollectionUtils.isEmpty(metricModelRequests)) {
            return;
        }
        metricModelRequests.forEach(metricModelRequest -> fillMetricId(modelRequest.getBizLineId(),metricModelRequest));
        List<MetricModelRequest> newMetricModels = metricModelRequests.stream().filter(metricModelRequest -> Objects.isNull(metricModelRequest.getMetricModelId())).collect(Collectors.toList());
        List<MetricModelRequest> updateMetricModels = metricModelRequests.stream().filter(metricModelRequest -> !Objects.isNull(metricModelRequest.getMetricModelId())).collect(Collectors.toList());

        saveMetricModels(modelId, newMetricModels);
        updateMetricModels(updateMetricModels);
    }

    private void fillMetricId(Long bizLineId,MetricModelRequest metricModelRequest) {
        if(!Objects.isNull(metricModelRequest.getMetricId())) {
            return;
        }
        if(StringUtils.isEmpty(metricModelRequest.getMetricCode())) {
            throw new IllegalArgumentException("metricId or metricCode should be set in MetricModelRequest");
        }
        MetricPO metric = metricDao.selectByMetricCode(bizLineId,metricModelRequest.getMetricCode());
        if(Objects.isNull(metric)) {
            throw new MetricNotFoundException(String.format("metric not found ,bizLineId:%s,metricCode:%s",bizLineId,metricModelRequest.getMetricCode()));
        }
        metricModelRequest.setMetricId(metric.getId());
    }

    private void saveMetricModels(Long modelId, List<MetricModelRequest> metricModels) {
        if (CollectionUtils.isEmpty(metricModels)) {
            return;
        }
        List<MetricModelPO> metricModelPOS = metricModels.stream().map(metricModelRequest -> {
            MetricModelPO metricModelPO = new MetricModelPO();
            metricModelPO.setModelId(modelId);
            metricModelPO.setMetricId(metricModelRequest.getMetricId());
            metricModelPO.setFormula(metricModelRequest.getFormula());
            metricModelPO.setMetricType(MetricType.PRIMARY.getMetricType());
            if (!Objects.isNull(metricModelRequest.getAdvanceCalculate())) {
                metricModelPO.setAdvanceCalculate(new Gson().toJson(metricModelRequest.getAdvanceCalculate()));
            }
            return metricModelPO;
        }).collect(Collectors.toList());
        metricModelDao.batchInsert(metricModelPOS);
    }

    private void updateMetricModels(List<MetricModelRequest> metricModels) {
        if (CollectionUtils.isEmpty(metricModels)) {
            return;
        }
        List<MetricModelPO> metricModelList = metricModels.stream().map(metricModelRequest -> {
            MetricModelPO updateMetricModel = new MetricModelPO();
            updateMetricModel.setId(metricModelRequest.getMetricModelId());
            updateMetricModel.setFormula(metricModelRequest.getFormula());
            updateMetricModel.setAdvanceCalculate(new Gson().toJson(Optional.ofNullable(metricModelRequest.getAdvanceCalculate()).get()));
            return updateMetricModel;
        }).collect(Collectors.toList());

        metricModelList.stream().forEach(metricModelPO -> {
            int cnt = metricModelDao.updateByPrimaryKeySelective(metricModelPO);
            if (cnt != 1) {
                throw new UpdateFailedException(String.format("update metric model failed,MetricModelId:%s", metricModelPO.getId()));
            }
        });
    }

    private void saveOrUpdateDimModels(Long modelId, ModelRequest modelRequest) {
        if (CollectionUtils.isEmpty(modelRequest.getDimModels())) {
            return;
        }
        List<DimModelRequest> newDimModelRequests = modelRequest.getDimModels().stream().filter(dimModelRequest -> Objects.isNull(dimModelRequest.getDimModelId())).collect(Collectors.toList());
        List<DimModelRequest> updateDimModelRequests = modelRequest.getDimModels().stream().filter(dimModelRequest -> !Objects.isNull(dimModelRequest.getDimModelId())).collect(Collectors.toList());

        saveDimModels(modelRequest.getBizLineId(),modelId, newDimModelRequests);
        updateDimModels(updateDimModelRequests);
    }

    private void saveDimModels(Long bizLineId,Long modelId, List<DimModelRequest> dimModelRequests) {
        if (CollectionUtils.isEmpty(dimModelRequests)) {
            return;
        }
        dimModelRequests.forEach(dimModelRequest -> fillDimId(bizLineId,dimModelRequest));
        List<DimModelPO> dimModelPOs = dimModelRequests.stream().map(dimModelRequest -> {
            DimModelPO dimModel = new DimModelPO();
            dimModel.setDimId(dimModelRequest.getDimId());
            dimModel.setModelId(modelId);
            dimModel.setFormula(dimModelRequest.getFormula());
            return dimModel;
        }).collect(Collectors.toList());

        dimModelDao.batchInsert(dimModelPOs);
    }

    private void fillDimId(Long bizLineId,DimModelRequest dimModelRequest) {
        if(!Objects.isNull(dimModelRequest.getDimId())) {
            return;
        }
        if(StringUtils.isEmpty(dimModelRequest.getDimCode())) {
            throw new IllegalArgumentException("dimId or dimCode should be set in dimModelRequest");
        }
        DimPO dimPO = dimDao.selectByDimCode(bizLineId,dimModelRequest.getDimCode());
        if(Objects.isNull(dimPO)) {
            throw new DimNotFoundException(String.format("dim not found:%s,%s",bizLineId,dimModelRequest.getDimCode()));
        }
        dimModelRequest.setDimId(dimPO.getId());
    }

    private void updateDimModels(List<DimModelRequest> dimModelRequests) {
        if (CollectionUtils.isEmpty(dimModelRequests)) {
            return;
        }
        dimModelRequests.forEach(dimModelRequest -> {
            DimModelPO dimModel = new DimModelPO();
            dimModel.setId(dimModelRequest.getDimModelId());
            dimModel.setFormula(dimModelRequest.getFormula());
            dimModelDao.updateByPrimaryKeySelective(dimModel);
        });
    }

    /**
     * ModelRequest校验
     * 1.权限校验
     * 2.参数校验
     *
     * @param modelRequest
     */
    @Override
    public void checkModelRequest(ModelRequest modelRequest) {
        //权限判断
        //参数校验
        String select = ModelUtils.getModelDefinition(modelRequest);
    }

    @Override
    public ModelDefinition getModel(Long modelId) {
        ModelPO model = modelDao.selectByPrimaryKey(modelId);
        if(Objects.isNull(model)) {
            throw new ModelNotFoundException(String.format("model not found:%s",modelId));
        }
        ModelDefinition modelDefinition = new ModelDefinition();
        modelDefinition.setModelId(modelId);
        modelDefinition.setModelDesc(model.getModelDesc());
        modelDefinition.setModelName(model.getModelName());
        modelDefinition.setTableSource(model.getTableSource());
        modelDefinition.setCondition(model.getCondition());
        if(!Objects.isNull(model.getModelProp())) {
            modelDefinition.setModelProp(new Gson().fromJson(model.getModelProp(),new TypeToken<Map<String,String>>(){}.getType()));
        }

        modelDefinition.setMetrics(getModelMetrics(modelId));
        modelDefinition.setDims(getModelDims(modelId));

        return modelDefinition;
    }

    private List<ModelDefinition.Metric> getModelMetrics(Long modelId) {
        List<MetricModelDetailPO> details = metricModelDao.selectMetricDetailByModelId(modelId);

        return details.stream().map(metricModelDetail -> {
            ModelDefinition.Metric metric = new ModelDefinition.Metric();
            metric.setMetricId(metricModelDetail.getMetricId());
            metric.setMetricModelId(metricModelDetail.getMetricModelId());
            metric.setMetricCode(metricModelDetail.getMetricCode());
            metric.setMetricName(metricModelDetail.getMetricName());
            metric.setMetricComment(metricModelDetail.getMetricComment());
            metric.setFormula(metricModelDetail.getFormula());
            metric.setAdvanceCalculate(new Gson().fromJson(metricModelDetail.getAdvanceCalculate(), AdvanceCalculate.class));
            return metric;
        }).collect(Collectors.toList());
    }

    private List<ModelDefinition.Dim> getModelDims(Long modelId) {
        List<DimModelDetailPO> dimModelDetails = dimModelDao.selectDimDetailByModelId(modelId);
        return dimModelDetails.stream().map(dimModelDetail -> {
            ModelDefinition.Dim dim = new ModelDefinition.Dim();
            dim.setDimId(dimModelDetail.getDimId());
            dim.setDimCode(dimModelDetail.getDimCode());
            dim.setDimModelId(dimModelDetail.getDimModelId());
            dim.setDimName(dimModelDetail.getDimName());
            dim.setDimComment(dimModelDetail.getDimComment());
            dim.setFormula(dimModelDetail.getFormula());
            return dim;
        }).collect(Collectors.toList());
    }


}
