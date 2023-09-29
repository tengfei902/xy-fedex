package com.xy.fedex.catalog.service.impl;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.statement.SQLSelect;
import com.alibaba.druid.sql.ast.statement.SQLSelectGroupByClause;
import com.alibaba.druid.sql.ast.statement.SQLSelectItem;
import com.alibaba.druid.sql.ast.statement.SQLSelectQueryBlock;
import com.google.common.base.Joiner;
import com.xy.fedex.catalog.common.definition.field.Dim;
import com.xy.fedex.catalog.common.definition.field.Metric;
import com.xy.fedex.catalog.common.definition.field.impl.DimModel;
import com.xy.fedex.catalog.common.definition.field.impl.MetricModel;
import com.xy.fedex.catalog.common.definition.field.impl.PrimaryMetricModel;
import com.xy.fedex.catalog.dao.ModelDao;
import com.xy.fedex.catalog.dao.ModelParamsDao;
import com.xy.fedex.catalog.dao.PrimaryMetricModelDao;
import com.xy.fedex.catalog.dto.ModelDTO;
import com.xy.fedex.catalog.exception.CatalogServiceExceptions;
import com.xy.fedex.catalog.exception.ErrorCode;
import com.xy.fedex.catalog.po.ModelPO;
import com.xy.fedex.catalog.po.ModelParamsPO;
import com.xy.fedex.catalog.service.DimService;
import com.xy.fedex.catalog.service.MetricService;
import com.xy.fedex.catalog.service.ModelService;
import com.xy.fedex.catalog.utils.CatalogUtils;
import com.xy.fedex.dsl.utility.SQLExprUtils;
import com.xy.fedex.rpc.context.UserContextHolder;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ModelServiceImpl implements ModelService {
    @Autowired
    private ModelDao modelDao;
    @Autowired
    private ModelParamsDao modelParamsDao;
    @Autowired
    private MetricService metricService;
    @Autowired
    private DimService dimService;

    @Override
    public ModelDTO getModelByCode(String modelCode) {
        return getModelByCodes(Arrays.asList(modelCode)).get(0);
    }

    @Override
    public ModelDTO getModelById(Long modelId) {
        return getModelByIds(Arrays.asList(modelId)).get(0);
    }

    @Override
    public List<ModelDTO> getModelByCodes(List<String> modelCodes) {
        if(CollectionUtils.isEmpty(modelCodes)) {
            return Collections.EMPTY_LIST;
        }
        modelCodes = modelCodes.stream().map(s -> CatalogUtils.getObjectFullName(s)).collect(Collectors.toList());
        List<ModelPO> models = modelDao.selectByModelCodes(modelCodes);

        List<String> existedModelCodes = models.stream().map(ModelPO::getModelCode).collect(Collectors.toList());
        modelCodes.removeAll(existedModelCodes);
        if(!CollectionUtils.isEmpty(modelCodes)) {
            throw CatalogServiceExceptions.newException(ErrorCode.MODEL_NOT_FOUND_FAILED,CatalogUtils.getCurrentProject(), Joiner.on(",").join(modelCodes));
        }

        return convertPO2DTO(models);
    }

    @Override
    public List<ModelDTO> getModelByIds(List<Long> modelIds) {
        if(CollectionUtils.isEmpty(modelIds)) {
            return Collections.EMPTY_LIST;
        }
        List<ModelPO> models = modelDao.selectByModelIds(modelIds);
        List<Long> existedModelIds = models.stream().map(ModelPO::getId).collect(Collectors.toList());
        modelIds.removeAll(existedModelIds);
        if(CollectionUtils.isEmpty(modelIds)) {
            throw CatalogServiceExceptions.newException(ErrorCode.MODEL_NOT_FOUND_FAILED,CatalogUtils.getCurrentProject(), Joiner.on(",").join(modelIds));
        }

        return convertPO2DTO(models);
    }

    private List<ModelDTO> convertPO2DTO(List<ModelPO> models) {
        List<ModelParamsPO> modelParams = modelParamsDao.selectByModelIds(models.stream().map(ModelPO::getId).collect(Collectors.toList()));
        Map<Long,List<ModelParamsPO>> modelParamsMap = modelParams.stream().collect(Collectors.groupingBy(ModelParamsPO::getModelId));
        return models.stream().map(modelPO -> {
            ModelDTO model = new ModelDTO();
            model.setModelCode(CatalogUtils.getObject(modelPO.getModelCode()).getObjectName());
            model.setModelName(modelPO.getModelName());
            model.setModelComment(modelPO.getModelComment());
            model.setDsnCode(model.getDsnCode());
            model.setCreator(modelPO.getCreator());
            model.setCreateTime(modelPO.getCreateTime());
            model.setUpdateTime(modelPO.getUpdateTime());
            List<ModelParamsPO> params = modelParamsMap.get(modelPO.getId());
            if(!CollectionUtils.isEmpty(params)) {
                Map<String,String> paramMap = new HashMap<>();
                for(ModelParamsPO modelParamsPO:params) {
                    paramMap.put(modelParamsPO.getParamKey(),modelParamsPO.getParamValue());
                }
                model.setModelParams(paramMap);
            }
            return model;
        }).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void saveModel(ModelDTO modelDTO) {
        ModelPO model = createModel(modelDTO);
        saveModelParams(model,modelDTO.getModelParams());
        String tableSource = modelDTO.getTableSource();
        SQLSelect sqlSelect = SQLExprUtils.parse(tableSource);

        SQLSelectQueryBlock sqlSelectQueryBlock = sqlSelect.getQueryBlock();
        List<SQLSelectItem> selectItems = sqlSelectQueryBlock.getSelectList();
        SQLSelectGroupByClause sqlSelectGroupByClause = sqlSelectQueryBlock.getGroupBy();
        List<SQLExpr> groupByItems = sqlSelectGroupByClause.getItems();

        List<SQLSelectItem> metrics = selectItems.stream().filter(selectItem -> !groupByItems.contains(selectItem.getExpr())).collect(Collectors.toList());
        List<SQLSelectItem> dims = selectItems.stream().filter(selectItem -> groupByItems.contains(selectItem.getExpr())).collect(Collectors.toList());

        metricService.saveMetricModels(metrics.stream().map(selectItem -> {
            PrimaryMetricModel metricModel = new PrimaryMetricModel();
            metricModel.setMetricCode(selectItem.getAlias());
            metricModel.setFormula(selectItem.getExpr().toString());
            metricModel.setAdvanceCalculate(null);
            metricModel.setModelCode(modelDTO.getModelCode());
            return metricModel;
        }).collect(Collectors.toList()));

        dimService.saveDimModels(dims.stream().map(selectItem -> {
            DimModel dim = new DimModel();
            dim.setDimCode(selectItem.getAlias());
            dim.setModelCode(modelDTO.getModelCode());
            dim.setFormula(selectItem.getExpr().toString());
            return dim;
        }).collect(Collectors.toList()));
    }

    private ModelPO createModel(ModelDTO modelDTO) {
        ModelPO modelPO = modelDao.selectByModelCode(CatalogUtils.getObjectFullName(modelDTO.getModelCode()));
        ModelPO newModel = new ModelPO();
        newModel.setModelCode(CatalogUtils.getObjectFullName(modelDTO.getModelCode()));
        newModel.setModelName(modelDTO.getModelName());
        newModel.setModelComment(modelDTO.getModelComment());
        newModel.setTableSource(modelDTO.getTableSource());
        newModel.setDsnCode(modelDTO.getDsnCode());
        if(Objects.isNull(modelPO)) {
            modelDao.insertSelective(newModel);
        } else {
            newModel.setId(modelPO.getId());
            modelDao.updateByPrimaryKeySelective(newModel);
        }
        return modelDao.selectByPrimaryKey(newModel.getId());
    }

    private void saveModelParams(ModelPO model, Map<String,String> params) {
        if(MapUtils.isEmpty(params)) {
            return;
        }
        List<ModelParamsPO> modelParams = params.keySet().stream().map(s -> {
            String paramKey = s;
            String paramValue = params.get(s);
            ModelParamsPO modelParamsPO = new ModelParamsPO();
            modelParamsPO.setModelId(model.getId());
            modelParamsPO.setParamKey(paramKey);
            modelParamsPO.setParamValue(paramValue);
            modelParamsPO.setCreator(UserContextHolder.getCurrentUser().getUserName());
            return modelParamsPO;
        }).collect(Collectors.toList());
        modelParamsDao.deleteByModelId(model.getId());
        modelParamsDao.batchInsert(modelParams);
    }

    private void saveMetricModels() {

    }
}
