package com.xy.fedex.catalog.service.meta.impl;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.xy.fedex.catalog.api.dto.request.list.ListMetricModelRequest;
import com.xy.fedex.catalog.common.definition.field.impl.AdvanceCalculate;
import com.xy.fedex.catalog.common.definition.field.impl.DeriveMetricModel;
import com.xy.fedex.catalog.common.definition.field.impl.MetricModel;
import com.xy.fedex.catalog.common.definition.field.impl.PrimaryMetricModel;
import com.xy.fedex.catalog.common.enums.MetricType;
import com.xy.fedex.catalog.constants.Constants;
import com.xy.fedex.catalog.dao.MetricModelDao;
import com.xy.fedex.catalog.dto.MetricModelRequest;
import com.xy.fedex.catalog.exception.MetricModelNotFoundException;
import com.xy.fedex.catalog.exception.MetricTypeNotSupportException;
import com.xy.fedex.catalog.po.MetricModelPO;
import com.xy.fedex.catalog.service.meta.MetricModelService;
import com.xy.fedex.dsl.utility.SQLExprUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class MetricModelServiceImpl implements MetricModelService {
    @Autowired
    private MetricModelDao metricModelDao;

    @Override
    public List<MetricModel> getMetricModels(MetricModelRequest request) {
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
        if(!StringUtils.isEmpty(metricModelPO.getAdvanceCalculate())) {
            List<AdvanceCalculate> advanceCalculates = new Gson().fromJson(metricModelPO.getAdvanceCalculate(),new TypeToken<List<AdvanceCalculate>>(){}.getType());
            if(!CollectionUtils.isEmpty(advanceCalculates)) {
                primaryMetricModel.setAdvanceCalculate(advanceCalculates.get(0));
            }
        }
        primaryMetricModel.setFormula(metricModelPO.getFormula());
        return primaryMetricModel;
    }

    private DeriveMetricModel getDeriveMetricModel(MetricModelPO metricModelPO) {
        DeriveMetricModel deriveMetricModel = new DeriveMetricModel();

        deriveMetricModel.setMetricModelId(metricModelPO.getId());
        deriveMetricModel.setMetricId(metricModelPO.getMetricId());
        deriveMetricModel.setMetricCode(null);
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
