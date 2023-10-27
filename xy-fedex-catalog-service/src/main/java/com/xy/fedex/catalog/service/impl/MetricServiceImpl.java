package com.xy.fedex.catalog.service.impl;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.xy.fedex.catalog.common.definition.field.Metric;
import com.xy.fedex.catalog.common.definition.field.impl.DeriveMetricModel;
import com.xy.fedex.catalog.common.definition.field.impl.MetricModel;
import com.xy.fedex.catalog.common.definition.field.impl.PrimaryMetricModel;
import com.xy.fedex.catalog.dao.DeriveMetricModelDao;
import com.xy.fedex.catalog.dao.MetricDao;
import com.xy.fedex.catalog.dao.MetricModelDao;
import com.xy.fedex.catalog.dao.PrimaryMetricModelDao;
import com.xy.fedex.catalog.exception.CatalogServiceExceptions;
import com.xy.fedex.catalog.exception.ErrorCode;
import com.xy.fedex.catalog.po.DeriveMetricModelPO;
import com.xy.fedex.catalog.po.MetricPO;
import com.xy.fedex.catalog.po.PrimaryMetricModelPO;
import com.xy.fedex.catalog.service.MetricService;
import com.xy.fedex.catalog.utils.CatalogUtils;
import com.xy.fedex.rpc.context.UserContextHolder;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author tengfei
 */
@Service
public class MetricServiceImpl implements MetricService {
    @Autowired
    private MetricDao metricDao;
    @Autowired
    private PrimaryMetricModelDao primaryMetricModelDao;
    @Autowired
    private DeriveMetricModelDao deriveMetricModelDao;

    @Override
    public List<Metric> getMetrics(List<String> metricCodes) {
        metricCodes = metricCodes.stream().map(s -> CatalogUtils.getObjectFullName(s)).collect(Collectors.toList());
        List<MetricPO> metrics = metricDao.selectByMetricCodes(metricCodes);
        if(metrics.size() != metricCodes.size()) {
            metricCodes.removeAll(metrics.stream().map(MetricPO::getMetricCode).collect(Collectors.toList()));
            throw CatalogServiceExceptions.newException(ErrorCode.METRIC_NOT_FOUND_FAILED, UserContextHolder.getCurrentUser().getProject(), Joiner.on(",").join(metricCodes));
        }
        return metrics.stream().map(metricPO -> {
            Metric metric = new Metric();
            metric.setMetricCode(CatalogUtils.getObject(metricPO.getMetricCode()).getObjectName());
            metric.setMetricName(metricPO.getMetricName());
            metric.setMetricComment(metricPO.getMetricComment());
            return metric;
        }).collect(Collectors.toList());
    }

    @Override
    public Metric getMetric(String metricCode) {
        return getMetrics(Arrays.asList(metricCode)).get(0);
    }

    @Override
    public void saveMetrics(List<Metric> metrics) {
        if(CollectionUtils.isEmpty(metrics)) {
            return;
        }
        List<String> metricCodes = metrics.stream().map(metric -> CatalogUtils.getObjectFullName(metric.getMetricCode())).collect(Collectors.toList());
        List<MetricPO> existedMetrics = metricDao.selectByMetricCodes(metricCodes);
        Map<String,MetricPO> existedMetricMap = existedMetrics.stream().collect(Collectors.toMap(MetricPO::getMetricCode, Function.identity()));

        List<MetricPO> saveMetrics = metrics.stream().map(metric -> {
            MetricPO metricPO = new MetricPO();
            metricPO.setMetricCode(CatalogUtils.getObjectFullName(metric.getMetricCode()));
            metricPO.setMetricName(StringUtils.isEmpty(metric.getMetricName())?"":metric.getMetricName());
            metricPO.setMetricComment(StringUtils.isEmpty(metric.getMetricComment())?"":metric.getMetricComment() );
            metricPO.setCreator(CatalogUtils.getCurrentUserName());
            return metricPO;
        }).collect(Collectors.toList());

        List<MetricPO> newMetrics = saveMetrics.stream().filter(metricPO -> !existedMetricMap.containsKey(metricPO.getMetricCode())).collect(Collectors.toList());
        if(!CollectionUtils.isEmpty(newMetrics)) {
            metricDao.batchInsert(newMetrics);
        }
        List<MetricPO> updateMetrics = saveMetrics.stream().filter(metricPO -> existedMetricMap.containsKey(metricPO.getMetricCode())).collect(Collectors.toList());
        if(!CollectionUtils.isEmpty(updateMetrics)) {
            for(MetricPO updateMetric:updateMetrics) {
                updateMetric.setId(existedMetricMap.get(updateMetric.getMetricCode()).getId());
                metricDao.updateByPrimaryKeySelective(updateMetric);
            }
        }
    }

    @Override
    public void saveMetric(Metric metric) {
        saveMetrics(Arrays.asList(metric));
    }

    @Transactional
    @Override
    public void saveMetricModels(List<MetricModel> metricModels) {
        getMetrics(metricModels.stream().map(MetricModel::getMetricCode).collect(Collectors.toList()));

        List<PrimaryMetricModelPO> primaryMetricModels = metricModels.stream().filter(metricModel -> metricModel instanceof PrimaryMetricModel).map(metricModel -> {
            PrimaryMetricModel primaryMetricModel = (PrimaryMetricModel) metricModel;
            PrimaryMetricModelPO primaryMetricModelPO = new PrimaryMetricModelPO();
            primaryMetricModelPO.setMetricCode(CatalogUtils.getObjectFullName(primaryMetricModel.getMetricCode()));
            primaryMetricModelPO.setModelCode(CatalogUtils.getObjectFullName(primaryMetricModel.getModelCode()));
            primaryMetricModelPO.setFormula(primaryMetricModel.getFormula());
            return primaryMetricModelPO;
        }).collect(Collectors.toList());

        for(PrimaryMetricModelPO primaryMetricModelPO:primaryMetricModels) {
            primaryMetricModelDao.insertSelective(primaryMetricModelPO);
        }

        List<DeriveMetricModelPO> deriveMetricModels = metricModels.stream().filter(metricModel -> metricModel instanceof DeriveMetricModel).map(metricModel -> {
            DeriveMetricModel deriveMetricModel = (DeriveMetricModel) metricModel;
            DeriveMetricModelPO deriveMetricModelPO = new DeriveMetricModelPO();
            deriveMetricModelPO.setAppCode(CatalogUtils.getObjectFullName(deriveMetricModel.getAppCode()));
            deriveMetricModelPO.setMetricCode(CatalogUtils.getObjectFullName(deriveMetricModelPO.getMetricCode()));
            deriveMetricModelPO.setFormula(deriveMetricModelPO.getFormula());
            deriveMetricModelPO.setCreator(UserContextHolder.getCurrentUser().getUserName());
            return deriveMetricModelPO;
        }).collect(Collectors.toList());

        for(DeriveMetricModelPO deriveMetricModelPO:deriveMetricModels) {
            deriveMetricModelDao.insertSelective(deriveMetricModelPO);
        }
    }

    @Override
    public void deleteMetricModels(String modelCode) {
        primaryMetricModelDao.deleteByModel(modelCode);
    }

    @Override
    public List<MetricModel> getMetricModels(String modelCode) {
        List<PrimaryMetricModelPO> primaryMetricModelPOS = primaryMetricModelDao.selectByModelCode(CatalogUtils.getObjectFullName(modelCode));
        if(CollectionUtils.isEmpty(primaryMetricModelPOS)) {
            return Lists.newArrayList();
        }
        List<MetricModel> metricModels = primaryMetricModelPOS.stream().map(primaryMetricModelPO -> {
            PrimaryMetricModel primaryMetricModel = new PrimaryMetricModel();
            primaryMetricModel.setModelCode(CatalogUtils.getObject(primaryMetricModelPO.getModelCode()).getObjectName());
            primaryMetricModel.setMetricCode(CatalogUtils.getObject(primaryMetricModelPO.getMetricCode()).getObjectName());
            primaryMetricModel.setFormula(primaryMetricModelPO.getFormula());
//            primaryMetricModel.setAdvanceCalculate();
            return primaryMetricModel;
        }).collect(Collectors.toList());
        return metricModels;
    }
}
