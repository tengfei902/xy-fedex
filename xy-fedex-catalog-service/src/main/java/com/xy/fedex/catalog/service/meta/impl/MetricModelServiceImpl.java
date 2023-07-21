package com.xy.fedex.catalog.service.meta.impl;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.xy.fedex.catalog.common.definition.field.impl.*;
import com.xy.fedex.catalog.common.enums.MetricType;
import com.xy.fedex.catalog.dao.DeriveMetricModelDao;
import com.xy.fedex.catalog.dao.MetricModelDao;
import com.xy.fedex.catalog.dao.PrimaryMetricModelDao;
import com.xy.fedex.catalog.dto.DimModelRequest;
import com.xy.fedex.catalog.dto.MetricModelRequest;
import com.xy.fedex.catalog.exception.MetricModelNotFoundException;
import com.xy.fedex.catalog.exception.MetricTypeNotSupportException;
import com.xy.fedex.catalog.po.MetricModelPO;
import com.xy.fedex.catalog.po.PrimaryMetricModelPO;
import com.xy.fedex.catalog.service.meta.DimModelService;
import com.xy.fedex.catalog.service.meta.MetricModelService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author tengfei
 */
@Service
public class MetricModelServiceImpl implements MetricModelService {
    @Autowired
    private MetricModelDao metricModelDao;
    @Autowired
    private DimModelService dimModelService;
    @Autowired
    private PrimaryMetricModelDao primaryMetricModelDao;
    @Autowired
    private DeriveMetricModelDao deriveMetricModelDao;

    @Override
    public List<MetricModel> getMetricModels(MetricModelRequest request) {
        List<PrimaryMetricModelPO> primaryMetricModels = primaryMetricModelDao.selectMetricModels(request);

        List<MetricModelPO> metricModels = metricModelDao.selectMetricModels(request);
        return metricModels.stream().map(this::getMetricModel).collect(Collectors.toList());
    }

    private MetricModel getMetricModel(MetricModelPO metricModelPO) {
        MetricType metricType = MetricType.parse(metricModelPO.getMetricType());
        if(Objects.isNull(metricType)) {
            throw new MetricTypeNotSupportException("metric type not support:"+metricModelPO.getMetricType());
        }
        switch (metricType) {
            case PRIMARY:
                return getPrimaryMetricModel(metricModelPO);
            case DERIVE:
                return getDeriveMetricModel(metricModelPO);
            default:
                throw new MetricTypeNotSupportException("metric type not support:"+metricType);
        }
    }

    private PrimaryMetricModel getPrimaryMetricModel(MetricModelPO metricModelPO) {
        PrimaryMetricModel primaryMetricModel = new PrimaryMetricModel();
        primaryMetricModel.setModelId(metricModelPO.getModelIds().get(0));
        primaryMetricModel.setMetricId(metricModelPO.getMetricId());
        primaryMetricModel.setMetricModelId(metricModelPO.getId());
        List<Long> modelIds = new Gson().fromJson(metricModelPO.getModelId(),new TypeToken<List<Long>>(){}.getType());
        AdvanceCalculate advanceCalculate = getAdvanceCalculate(modelIds.get(0),metricModelPO);
        primaryMetricModel.setAdvanceCalculate(advanceCalculate);

        primaryMetricModel.setFormula(metricModelPO.getFormula());
        primaryMetricModel.setMetricCode(metricModelPO.getMetricCode());
        return primaryMetricModel;
    }

    private AdvanceCalculate getAdvanceCalculate(Long modelId, MetricModelPO metricModelPO) {
        List<DimModel> dimModels = dimModelService.getDimModels(DimModelRequest.builder().modelIds(Arrays.asList(modelId)).build());
        AdvanceCalculate advanceCalculate = new AdvanceCalculate();
        if(!StringUtils.isEmpty(metricModelPO.getAdvanceCalculate())) {
            List<AdvanceCalculate> advanceCalculates = new Gson().fromJson(metricModelPO.getAdvanceCalculate(),new TypeToken<List<AdvanceCalculate>>(){}.getType());
            if(!CollectionUtils.isEmpty(advanceCalculates)) {
                advanceCalculate = advanceCalculates.get(0);
            }
        }
        //allow dims
        if(CollectionUtils.isEmpty(advanceCalculate.getAllowDims())) {
            advanceCalculate.setAllowDims(dimModels.stream().map(DimModel::getDimCode).collect(Collectors.toList()));
        }
        return advanceCalculate;
    }

    private DeriveMetricModel getDeriveMetricModel(MetricModelPO metricModelPO) {
        DeriveMetricModel deriveMetricModel = new DeriveMetricModel();

        deriveMetricModel.setMetricModelId(metricModelPO.getId());
        deriveMetricModel.setMetricId(metricModelPO.getMetricId());
        deriveMetricModel.setMetricCode(metricModelPO.getMetricCode());
        deriveMetricModel.setRelateMetricModels(new ArrayList<>());

        String formula = metricModelPO.getFormula();
        deriveMetricModel.setFormula(formula);

        List<AdvanceCalculate> advanceCalculates = new Gson().fromJson(metricModelPO.getAdvanceCalculate(),new TypeToken<List<AdvanceCalculate>>(){}.getType()) ;
        for(AdvanceCalculate advanceCalculate:advanceCalculates) {
            Long relateMetricModelId = advanceCalculate.getMetricModelId();
            MetricModelPO metricModel = metricModelDao.selectByPrimaryKey(relateMetricModelId);
            if(Objects.isNull(metricModel)) {
                throw new MetricModelNotFoundException("metric model not found:"+relateMetricModelId);
            }
            MetricModel relateMetricModel = getMetricModel(metricModel);
            relateMetricModel.setAdvanceCalculate(advanceCalculate);
            deriveMetricModel.getRelateMetricModels().add(relateMetricModel);
        }
        return deriveMetricModel;
    }

    @Override
    public void saveMetricModel(List<MetricModel> metricModels) {
        metricModels.stream().map(metricModel -> {
            if(metricModel instanceof PrimaryMetricModel) {
                PrimaryMetricModel primaryMetricModel = (PrimaryMetricModel) metricModel;
            } else {
                DeriveMetricModel deriveMetricModel = (DeriveMetricModel) metricModel;
            }
            return null;
        }).collect(Collectors.toList());
    }

    @Override
    public Integer getMetricModelCnt(MetricModelRequest request) {
        return metricModelDao.getMetricModelCnt(request);
    }
}
