package com.xy.fedex.catalog.service.meta.impl;

import com.google.gson.Gson;
import com.xy.fedex.admin.api.vo.response.TableDetailVO;
import com.xy.fedex.catalog.common.definition.ModelDefinition;
import com.xy.fedex.catalog.common.definition.column.TableField;
import com.xy.fedex.catalog.common.definition.field.AdvanceCalculate;
import com.xy.fedex.catalog.common.definition.field.MetaField;
import com.xy.fedex.catalog.common.definition.field.impl.DeriveMetricModel;
import com.xy.fedex.catalog.common.definition.field.impl.DimModel;
import com.xy.fedex.catalog.common.definition.field.impl.MetricModel;
import com.xy.fedex.catalog.common.definition.field.impl.PrimaryMetricModel;
import com.xy.fedex.catalog.common.enums.MetricType;
import com.xy.fedex.catalog.constants.Constants;
import com.xy.fedex.catalog.dao.*;
import com.xy.fedex.catalog.dto.*;
import com.xy.fedex.catalog.exception.*;
import com.xy.fedex.catalog.po.*;
import com.xy.fedex.catalog.service.meta.MetaMatchService;
import com.xy.fedex.catalog.service.meta.MetaService;
import com.xy.fedex.catalog.service.meta.ModelService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class MetaServiceImpl implements MetaService {
    @Autowired
    private MetricDao metricDao;
    @Autowired
    private DimDao dimDao;
    @Autowired
    private MetaMatchService metaMatchService;
    @Autowired
    private AppMetricModelRelationDao appMetricModelRelationDao;
    @Autowired
    private MetricModelDao metricModelDao;
    @Autowired
    private ModelService modelService;
    @Autowired
    private DimModelDao dimModelDao;
    @Autowired
    private DimFamilyRelationDao dimFamilyRelationDao;
    @Autowired
    private DimFamilyDao dimFamilyDao;

    @Override
    public MetricDTO getMetric(Long metricId) {
        MetricPO metric = metricDao.selectByPrimaryKey(metricId);
        if (Objects.isNull(metric)) {
            throw new MetricNotFoundException("metric not found:" + metric);
        }
        return MetricDTO.builder().metricId(metric.getId()).metricCode(metric.getMetricCode()).metricName(metric.getMetricName()).subjectId(metric.getSubjectId()).metricComment(metric.getMetricComment()).bizLineId(metric.getBizLineId()).metricFormat(metric.getMetricFormat()).build();
    }

    @Override
    public MetricDTO getMetric(Long bizLineId, String metricCode) {
        MetricPO metric = metricDao.selectByMetricCode(bizLineId, metricCode);
        if (Objects.isNull(metric)) {
            return null;
        }
        return convert(metric);
    }

    private MetricDTO convert(MetricPO metric) {
        return MetricDTO.builder()
                .metricId(metric.getId())
                .subjectId(metric.getSubjectId())
                .metricCode(metric.getMetricCode())
                .formula(metric.getFormula())
                .bizLineId(metric.getBizLineId())
                .metricType(MetricType.parse(metric.getMetricType()))
                .metricName(metric.getMetricName())
                .metricComment(metric.getMetricComment())
                .metricFormat(metric.getMetricFormat())
                .unit(metric.getUnit()).build();
    }

    @Override
    public List<MetricDTO> getMetrics(Long bizLineId) {
        List<MetricPO> allMetrics = metricDao.selectAllMetrics(bizLineId);
        if (CollectionUtils.isEmpty(allMetrics)) {
            return Collections.EMPTY_LIST;
        }
        return allMetrics.stream().map(metricPO -> convert(metricPO)).collect(Collectors.toList());
    }

    @Override
    public DimDTO getDim(Long dimId) {
        DimPO dim = dimDao.selectByPrimaryKey(dimId);
        if (Objects.isNull(dim)) {
            throw new DimNotFoundException("dim not found:" + dimId);
        }
        return DimDTO.builder().dimId(dim.getId()).dimCode(dim.getDimCode()).dimComment(dim.getDimComment()).dimName(dim.getDimName()).dimType(dim.getDimType()).build();
    }

    @Override
    public DimDTO getDim(Long bizLineId, String dimCode) {
        DimPO dim = dimDao.selectByDimCode(bizLineId, dimCode);
        if (Objects.isNull(dim)) {
            return null;
        }
        return DimDTO.builder().dimId(dim.getId()).dimCode(dim.getDimCode()).dimComment(dim.getDimComment()).dimName(dim.getDimName()).dimType(dim.getDimType()).build();
    }

    @Override
    public List<DimDTO> getDims(Long bizLineId) {
        return null;
    }

    @Override
    public Long saveMetric(MetricDTO metric) {
        MetricPO metricPO = metricDao.selectByMetricCode(metric.getBizLineId(),metric.getMetricCode());
        if(Objects.isNull(metricPO)) {
            //new metric
            MetricPO newMetric = new MetricPO();
            newMetric.setTenant(metric.getTenantId());
            newMetric.setCreator(metric.getAccountId());
            newMetric.setBizLineId(metric.getBizLineId());
            newMetric.setMetricCode(metric.getMetricCode());
            newMetric.setMetricName(metric.getMetricName());
            newMetric.setMetricComment(metric.getMetricComment());
            metricDao.insertSelective(newMetric);
            return newMetric.getId();
        } else {
            MetricPO updateMetric = new MetricPO();
            updateMetric.setMetricCode(metric.getMetricCode());
            updateMetric.setMetricName(metric.getMetricName());
            updateMetric.setMetricComment(metric.getMetricComment());
            updateMetric.setSubjectId(metric.getSubjectId());
            updateMetric.setCreator(metric.getAccountId());
            updateMetric.setId(metricPO.getId());
            int cnt = metricDao.updateByPrimaryKeySelective(updateMetric);
            if (cnt <= 0) {
                throw new UpdateFailedException("update metric failed,metric id:" + metric.getMetricId());
            }
            return updateMetric.getId();
        }
    }

    @Transactional
    @Override
    public Long saveDim(DimDTO dim) {
        DimPO dimPO = dimDao.selectByDimCode(dim.getBizLineId(),dim.getDimCode());
        if(Objects.isNull(dimPO)) {
            //new
            dimPO = new DimPO();
            dimPO.setDimCode(dim.getDimCode());
            dimPO.setDimName(dim.getDimName());
            dimPO.setDimComment(dim.getDimComment());

            dimPO.setBizLineId(dim.getBizLineId());
            dimPO.setTenantId(dim.getTenantId());
            dimPO.setCreator(dim.getCreator());

            dimDao.insertSelective(dimPO);
            
            saveDimFamily(dim.getDimFamilyId(), dimPO.getId(),dim.getMaster());
            return dimPO.getId();
        } else {
            //update
            DimPO updateDim = new DimPO();
            updateDim.setId(dimPO.getId());
            updateDim.setDimCode(dim.getDimCode());
            updateDim.setDimName(dim.getDimName());
            updateDim.setDimComment(dim.getDimComment());
            dimDao.updateByPrimaryKeySelective(updateDim);

            dimFamilyRelationDao.deleteByDimId(dimPO.getId());
            dimFamilyDao.removeMasterDim(dim.getDimFamilyId(),dimPO.getId());

            saveDimFamily(dim.getDimFamilyId(),dimPO.getId(),dim.getMaster());
            return dimPO.getId();
        }
    }

    private void saveDimFamily(Long dimFamilyId,Long dimId,Boolean master) {
        if(Objects.isNull(dimFamilyId)) {
            return;
        }
        DimFamilyPO dimFamilyPO = dimFamilyDao.selectByPrimaryKey(dimFamilyId);
        if(Objects.isNull(dimFamilyPO)) {
            throw new DimFamilyNotFoundException("dim family not found:"+dimFamilyId);
        }
        if(!Objects.isNull(master) && master) {
            dimFamilyDao.updateMasterDim(dimFamilyId,dimId);
        }
        DimFamilyRelationPO dimFamilyRelationPO = new DimFamilyRelationPO();
        dimFamilyRelationPO.setDimFamilyId(dimFamilyId);
        dimFamilyRelationPO.setDimId(dimId);
        dimFamilyRelationDao.insertSelective(dimFamilyRelationPO);
    }

    @Override
    public void matchMetricAndDim(List<TableAliasDTO> tableAliasList) {
        for (TableAliasDTO tableAliasDTO : tableAliasList) {
            String alias = tableAliasDTO.getAlias();
            TableDetailVO table = tableAliasDTO.getTable();
            for (TableDetailVO.Column column : table.getColumns()) {
                String fieldName = column.getColumnName();

            }
        }
    }

    @Override
    public List<TableField> matchMeta(Long bizLineId, List<TableField> tableFields) {
        for (TableField tableField : tableFields) {
            String columnName = tableField.getColumnName();
            MetaField relateMetaField = metaMatchService.getRelateMetaField(bizLineId, columnName);
            tableField.setRelateField(relateMetaField);
        }
        return tableFields;
    }

    /**
     * metric model列表查询
     * @param metricModelRequest
     * @return
     */
    @Override
    public List<MetricModel> getMetricModels(MetricModelRequest metricModelRequest) {
        List<MetricModelDetailPO> metricModelDetails = appMetricModelRelationDao.selectAppMetricModels(metricModelRequest.getAppId(),metricModelRequest.getMetricId());
        if(CollectionUtils.isEmpty(metricModelDetails)) {
            return Collections.EMPTY_LIST;
        }
        List<MetricModel> metricModels = metricModelDetails.stream().map(metricModelDetailPO -> {
            MetricType metricType = MetricType.parse(metricModelDetailPO.getMetricType());
            switch (metricType) {
                case PRIMARY:
                    return getPrimaryMetricModel(metricModelDetailPO);
                case DERIVE:
                    return getDeriveMetricModel(metricModelRequest.getAppId(), metricModelDetailPO);
            }
            throw new MetricTypeNotSupportException("metric type not support:"+metricType.name());
        }).collect(Collectors.toList());
        return metricModels;
    }

    /**
     * 获取metric model
     * @param metricModelRequest
     * @return
     */
    @Override
    public MetricModel getMetricModel(MetricModelRequest metricModelRequest) {
        Long modelId = metricModelRequest.getModelId();
        if (Objects.isNull(modelId)) {
            throw new IllegalArgumentException("model id cannot be null");
        }
        ModelDefinition modelDefinition = modelService.getModel(modelId);
        if (Objects.isNull(modelDefinition)) {
            throw new ModelNotFoundException(String.format("model:%s not found", modelId));
        }
        MetricModelPO metricModelPO = metricModelDao.selectByModelMetric(metricModelRequest.getModelId(), metricModelRequest.getMetricId());
        if (Objects.isNull(metricModelPO)) {
            throw new MetricNotFoundException(String.format("metric:%s not found in model:%s", metricModelRequest.getMetricId(), metricModelRequest.getModelId()));
        }
        return getMetricModel(metricModelRequest.getAppId(),metricModelPO.getId());
    }

    private MetricModel getMetricModel(Long appId, Long metricModelId) {
        MetricModelDetailPO metricModelDetailPO = appMetricModelRelationDao.selectAppMetricModel(appId, metricModelId);
        MetricType metricType = MetricType.parse(metricModelDetailPO.getMetricType());
        switch (metricType) {
            case PRIMARY:
                return getPrimaryMetricModel(metricModelDetailPO);
            case DERIVE:
                return getDeriveMetricModel(appId, metricModelDetailPO);
        }
        throw new MetricTypeNotSupportException("metric type not support:"+metricType.name());
    }

    private PrimaryMetricModel getPrimaryMetricModel(MetricModelDetailPO metricModelDetailPO) {
        PrimaryMetricModel metricModel = new PrimaryMetricModel();
        metricModel.setModelId(metricModelDetailPO.getModelId());
        metricModel.setCode(metricModelDetailPO.getMetricCode());
        metricModel.setFormula(metricModelDetailPO.getFormula());
        metricModel.setComment(metricModelDetailPO.getMetricComment());

        metricModel.setMetricId(metricModelDetailPO.getMetricId());
        metricModel.setMetricModelId(metricModelDetailPO.getMetricModelId());
        metricModel.setAdvanceCalculate(getAdvanceCalculate(metricModelDetailPO.getModelId(), metricModelDetailPO.getAdvanceCalculate()));
        return metricModel;
    }

    private DeriveMetricModel getDeriveMetricModel(Long appId, MetricModelDetailPO metricModelDetailPO) {
        DeriveMetricModel deriveMetricModel = new DeriveMetricModel();
        deriveMetricModel.setCode(metricModelDetailPO.getMetricCode());
        deriveMetricModel.setFormula(metricModelDetailPO.getFormula());
        deriveMetricModel.setComment(metricModelDetailPO.getMetricComment());

        deriveMetricModel.setMetricId(metricModelDetailPO.getMetricId());
        deriveMetricModel.setMetricModelId(metricModelDetailPO.getMetricModelId());
        deriveMetricModel.setAdvanceCalculate(getAdvanceCalculate(metricModelDetailPO.getModelId(), metricModelDetailPO.getAdvanceCalculate()));

        String formula = metricModelDetailPO.getFormula();

        List<MetricModel> relateMetricModels = new ArrayList<>();

        Matcher matcher = Constants.DERIVE_FORMULA_PATTERN.matcher(formula);
        while (matcher.find()) {
            String group = matcher.group();
            Long relateMetricModelId = Long.valueOf(group.replace("${", "").replace("}", ""));
            MetricModel relateMetricModel = getMetricModel(appId, relateMetricModelId);
            relateMetricModels.add(relateMetricModel);
        }

        deriveMetricModel.setRelateMetricModels(relateMetricModels);
        return deriveMetricModel;
    }

    private AdvanceCalculate getAdvanceCalculate(Long modelId, String advanceCalculate) {
        ModelDefinition model = modelService.getModel(modelId);
//        if (StringUtils.isEmpty(advanceCalculate)) {
//            AdvanceCalculate result = new AdvanceCalculate();
//            result.setAllowDims(model.getDims().stream().map(DimModel::getCode).collect(Collectors.toList()));
//            return result;
//        } else {
//            AdvanceCalculate result = new Gson().fromJson(advanceCalculate, AdvanceCalculate.class);
//            if (CollectionUtils.isEmpty(result.getAllowDims())) {
//                result.setAllowDims(model.getDims().stream().map(DimModel::getCode).collect(Collectors.toList()));
//            }
//            return result;
//        }
        return null;
    }

    @Override
    public List<DimModel> getDimModels(DimModelRequest dimModelRequest) {
        Long appId = dimModelRequest.getAppId();
        Long dimId = dimModelRequest.getDimId();

        List<DimModelDetailPO> dimModelDetails = dimModelDao.selectByAppDimId(appId,dimId);
        if(CollectionUtils.isEmpty(dimModelDetails)) {
            return Collections.EMPTY_LIST;
        }

        return dimModelDetails.stream().map(dimModelDetail -> {
            DimModel dimModel = new DimModel();
            dimModel.setDimId(dimModelDetail.getDimId());
            dimModel.setDimModelId(dimModelDetail.getDimModelId());
            dimModel.setCode(dimModelDetail.getDimCode());
            dimModel.setFormula(dimModelDetail.getFormula());
            dimModel.setComment(dimModelDetail.getDimComment());
            dimModel.setModelId(dimModelDetail.getModelId());
            return dimModel;
        }).collect(Collectors.toList());
    }
}
