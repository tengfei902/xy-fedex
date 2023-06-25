package com.xy.fedex.catalog.service.meta.impl;

import com.google.common.collect.Lists;
import com.xy.fedex.catalog.api.dto.request.SaveAppRequest;
import com.xy.fedex.catalog.common.definition.AppDefinition;
import com.xy.fedex.catalog.common.definition.field.Dim;
import com.xy.fedex.catalog.common.definition.field.Metric;
import com.xy.fedex.catalog.common.enums.DimType;
import com.xy.fedex.catalog.common.enums.FieldType;
import com.xy.fedex.catalog.dao.*;
import com.xy.fedex.catalog.exception.AppNotFoundException;
import com.xy.fedex.catalog.po.*;
import com.xy.fedex.catalog.service.meta.AppService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class AppServiceImpl implements AppService {
    @Autowired
    private AppDao appDao;
    @Autowired
    private AppModelRelationDao appModelRelationDao;
    @Autowired
    private AppMetricModelRelationDao appMetricModelRelationDao;
    @Autowired
    private DimModelDao dimModelDao;
    @Autowired
    private MetricModelDao metricModelDao;
    @Autowired
    private AppColumnDao appColumnDao;

    @Transactional
    @Override
    public Long saveApp(SaveAppRequest saveAppRequest) {
        Long appId = saveOrUpdateApp(saveAppRequest);
        List<Long> relateModelIds = saveAppRequest.getRelateModelIds();
        saveAppModelRelation(appId, relateModelIds);
        saveAppColumns(appId, saveAppRequest.getMetrics(), saveAppRequest.getDims());
        return appId;
    }

    private Long saveOrUpdateApp(SaveAppRequest saveAppRequest) {
        AppPO app = appDao.selectByName(saveAppRequest.getBizLineId(),saveAppRequest.getAppName());
        if(Objects.isNull(app)) {
            return createApp(saveAppRequest);
        }
        return app.getId();
    }

    private Long createApp(SaveAppRequest saveAppRequest) {
        AppPO appPO = new AppPO();
        appPO.setAppName(saveAppRequest.getAppName());
        appPO.setTenantId(saveAppRequest.getTenantId());
        appPO.setCreator(saveAppRequest.getCreator());
        appPO.setAppComment(saveAppRequest.getAppComment());
        appPO.setBizLineId(saveAppRequest.getBizLineId());
        appDao.insertSelective(appPO);
        return appPO.getId();
    }

    private Long updateApp(SaveAppRequest saveAppRequest,AppPO app) {
        if(Objects.isNull(app)) {
            throw new AppNotFoundException("app not found:"+ saveAppRequest.getAppId());
        }
        AppPO updateApp = new AppPO();
        updateApp.setId(app.getId());
        updateApp.setAppName(saveAppRequest.getAppName());
//        updateApp.setAppDesc(saveAppRequest.getAppDesc());
        appDao.updateByPrimaryKeySelective(updateApp);
        return app.getId();
    }

    private void saveAppModelRelation(Long appId, List<Long> relatedModelIds) {
        appModelRelationDao.deleteByAppId(appId);
        if(CollectionUtils.isEmpty(relatedModelIds)) {
            return;
        }
        List<AppModelRelationPO> relations = relatedModelIds.stream().map(modelId -> {
            AppModelRelationPO relation = new AppModelRelationPO();
            relation.setAppId(appId);
            relation.setModelId(modelId);
            return relation;
        }).collect(Collectors.toList());
        appModelRelationDao.batchInsert(relations);
    }

    private void saveAppColumns(Long appId, List<SaveAppRequest.Metric> metrics, List<SaveAppRequest.Dim> dims) {
        appColumnDao.deleteAppColumns(appId);

        List<AppColumnPO> metricColumns = metrics.stream().map(metric -> {
            AppColumnPO appColumnPO = new AppColumnPO();
            appColumnPO.setAppId(appId);
            appColumnPO.setColumnId(metric.getMetricId());
            appColumnPO.setColumnCode(metric.getMetricCode());
            appColumnPO.setColumnName(metric.getMetricName());
            appColumnPO.setColumnType(FieldType.METRIC.getFieldType());
            appColumnPO.setColumnFormat(metric.getMetricFormat());
            return appColumnPO;
        }).collect(Collectors.toList());

        List<AppColumnPO> dimColumns = dims.stream().map(dim -> {
            AppColumnPO appColumnPO = new AppColumnPO();
            appColumnPO.setAppId(appId);
            appColumnPO.setColumnId(dim.getDimId());
            appColumnPO.setColumnCode(dim.getDimCode());
            appColumnPO.setColumnName(dim.getDimName());
            appColumnPO.setColumnType(FieldType.DIM.getFieldType());
            appColumnPO.setColumnFormat(dim.getDimFormat());
            return appColumnPO;
        }).collect(Collectors.toList());

        List<AppColumnPO> columns = new ArrayList<>();
        columns.addAll(metricColumns);
        columns.addAll(dimColumns);

        appColumnDao.batchInsert(columns);
    }

    @Override
    public AppDefinition getApp(Long appId) {
        AppPO appPO = appDao.selectByPrimaryKey(appId);
        if(Objects.isNull(appPO)) {
            throw new AppNotFoundException("app not found:"+appId);
        }
        return getAppDefinition(appPO);
    }

    @Override
    public AppDefinition getApp(Long bizLineId, String appName) {
        AppPO appPO = appDao.selectByName(bizLineId,appName);
        if(Objects.isNull(appPO)) {
            throw new AppNotFoundException(String.format("app not found, bizLineId:%s,appName:%s",bizLineId,appName));
        }
        return getAppDefinition(appPO);
    }

    private AppDefinition getAppDefinition(AppPO appPO) {
        AppDefinition appDefinition = new AppDefinition();
        appDefinition.setAppId(appPO.getId());
        appDefinition.setAppName(appPO.getAppName());
        appDefinition.setAppComment(appPO.getAppComment());

        List<AppModelRelationPO> appModelRelations = appModelRelationDao.selectByAppId(appPO.getId());
        if(!CollectionUtils.isEmpty(appModelRelations)) {
            appDefinition.setModelIds(appModelRelations.stream().map(AppModelRelationPO::getModelId).collect(Collectors.toList()));
        }

        appDefinition.setDims(new ArrayList<>());
        appDefinition.setMetrics(new ArrayList<>());

        List<AppColumnPO> appColumns = appColumnDao.selectByAppId(appPO.getId());
        for(AppColumnPO column:appColumns) {
            if(column.getColumnType() == FieldType.DIM.getFieldType()) {
                Dim dim = new Dim();
                dim.setDimId(column.getColumnId());
                dim.setDimCode(column.getColumnCode());
                dim.setDimName(column.getColumnName());
                dim.setDimComment(column.getColumnName());
                dim.setDimFormat(column.getColumnFormat());
                appDefinition.getDims().add(dim);
            }
            if(column.getColumnType() == FieldType.METRIC.getFieldType()) {
                Metric metric = new Metric();
                metric.setMetricId(column.getColumnId());
                metric.setMetricCode(column.getColumnCode());
                metric.setMetricName(column.getColumnName());
                metric.setMetricComment(column.getColumnName());
                metric.setMetricFormat(column.getColumnFormat());
                appDefinition.getMetrics().add(metric);
            }
        }

        return appDefinition;
    }

    private List<Dim> getDims(List<Long> relateModelIds) {
        List<DimPO> dims = dimModelDao.selectByModelIds(relateModelIds);
        if(CollectionUtils.isEmpty(dims)) {
            return Lists.newArrayList();
        }
        return dims.stream().map(dimPO -> {
            Dim dim = new Dim();
//            dim.(dimPO.getId());
//            dim.setDimCode(dimPO.getDimCode());
//            dim.setDimName(dimPO.getDimName());
//            dim.setDimComment(dimPO.getDimComment());
//            dim.setDimType(DimType.parse(dimPO.getDimType()));
            return dim;
        }).collect(Collectors.toList());
    }

    private List<Metric> getMetrics(Long appId) {
        List<MetricModelDetailPO> appMetrics = appMetricModelRelationDao.selectAppMetrics(appId);
        return appMetrics.stream().map(appMetricPO -> {
            Metric metric = new Metric();
            metric.setMetricId(appMetricPO.getMetricId());
            metric.setMetricCode(appMetricPO.getMetricCode());
            metric.setMetricName(appMetricPO.getMetricName());
            metric.setMetricComment(appMetricPO.getMetricComment());
            return metric;
        }).collect(Collectors.toList());
    }
}
