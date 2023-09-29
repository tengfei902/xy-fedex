package com.xy.fedex.catalog.service.impl;

import com.google.common.base.Joiner;
import com.xy.fedex.catalog.common.definition.AppDefinition;
import com.xy.fedex.catalog.common.definition.field.Dim;
import com.xy.fedex.catalog.common.definition.field.Metric;
import com.xy.fedex.catalog.common.enums.FieldType;
import com.xy.fedex.catalog.constants.Constants;
import com.xy.fedex.catalog.dao.*;
import com.xy.fedex.catalog.dto.ModelDTO;
import com.xy.fedex.catalog.dto.request.AppRequest;
import com.xy.fedex.catalog.exception.AppNotFoundException;
import com.xy.fedex.catalog.po.*;
import com.xy.fedex.catalog.service.AppService;
import com.xy.fedex.catalog.utils.CatalogUtils;
import com.xy.fedex.rpc.context.UserContextHolder;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class AppServiceImpl implements AppService {
    @Autowired
    private AppDao appDao;
    @Autowired
    private AppMetricModelRelationDao appMetricModelRelationDao;
    @Autowired
    private AppColumnDao appColumnDao;
    @Autowired
    private AppParamsDao appParamsDao;

    @Transactional
    @Override
    public Long saveApp(AppRequest appRequest) {
        AppPO currentVersionApp = upgradeAppVersion(appRequest);
        //create relate models
        saveAppRelateModels(currentVersionApp,appRequest.getModels());
        //create metrics & dims
        saveAppColumns(currentVersionApp.getId(),appRequest.getMetrics(),appRequest.getDims());
        return currentVersionApp.getId();
    }

    private AppPO upgradeAppVersion(AppRequest appRequest) {
        String newVersion = CatalogUtils.getCurrentVersion();
        String appCode =CatalogUtils.getObjectFullName(appRequest.getAppCode());
        AppPO app = appDao.selectByAppCode(appCode);
        if(Objects.isNull(app)) {
            app = new AppPO();
            app.setAppCode(appRequest.getAppCode());
            app.setAppName(appRequest.getAppName());
            app.setAppComment(appRequest.getAppComment());
            app.setCreator(UserContextHolder.getCurrentUser().getUserName());
            app.setVersion(newVersion);
            appDao.insertSelective(app);
        } else {
            app.setAppName(appRequest.getAppName());
            app.setAppComment(appRequest.getAppComment());
            app.setCurrentVersion(app.getVersion());
            app.setVersion(newVersion);
            appDao.updateByPrimaryKeySelective(app);
        }
        return appDao.selectByPrimaryKey(app.getId());
    }

    private void saveAppRelateModels(AppPO app, List<ModelDTO> appRelateModels) {
        if(CollectionUtils.isEmpty(appRelateModels)) {
            return;
        }
        AppParamsPO appParamsPO = appParamsDao.selectByParamKey(app.getId(),Constants.APP_RELATE_MODELS);
        if(Objects.isNull(appParamsPO)) {
            appParamsPO = new AppParamsPO();
            appParamsPO.setAppId(app.getId());
            appParamsPO.setParamKey(Constants.APP_RELATE_MODELS);
            appParamsPO.setParamValue(Joiner.on(",").join(appRelateModels.stream().map(ModelDTO::getModelCode).collect(Collectors.toList())));
            appParamsDao.insertSelective(appParamsPO);
        } else {
            appParamsPO.setParamValue(Joiner.on(",").join(appRelateModels.stream().map(ModelDTO::getModelCode).collect(Collectors.toList())));
            appParamsDao.updateByParamKeySelective(appParamsPO);
        }
    }

    private void saveAppColumns(Long appId, List<Metric> metrics, List<Dim> dims) {
        appColumnDao.deleteAppColumns(appId);

        List<AppColumnPO> metricColumns = metrics.stream().map(metric -> {
            AppColumnPO appColumnPO = new AppColumnPO();
            appColumnPO.setAppId(appId);
            appColumnPO.setColumnCode(metric.getMetricCode());
            appColumnPO.setColumnName(metric.getMetricName());
            appColumnPO.setColumnType(FieldType.METRIC.getFieldType());
            appColumnPO.setColumnFormat(metric.getMetricFormat());
            return appColumnPO;
        }).collect(Collectors.toList());

        List<AppColumnPO> dimColumns = dims.stream().map(dim -> {
            AppColumnPO appColumnPO = new AppColumnPO();
            appColumnPO.setAppId(appId);
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
//        AppPO appPO = appDao.selectByName(bizLineId,appName);
//        if(Objects.isNull(appPO)) {
//            throw new AppNotFoundException(String.format("app not found, bizLineId:%s,appName:%s",bizLineId,appName));
//        }
//        return getAppDefinition(appPO);
        return null;
    }

    private AppDefinition getAppDefinition(AppPO appPO) {
        AppDefinition appDefinition = new AppDefinition();
        appDefinition.setAppName(appPO.getAppName());
        appDefinition.setAppComment(appPO.getAppComment());

        List<AppParamsPO> appParams = appParamsDao.selectByAppId(appPO.getId());
        Map<String,AppParamsPO> paramMap =  appParams.stream().collect(Collectors.toMap(AppParamsPO::getParamKey, Function.identity()));
        if(paramMap.containsKey(Constants.APP_RELATE_MODELS) && !StringUtils.isEmpty(paramMap.get(Constants.APP_RELATE_MODELS).getParamValue())) {
            List<Long> relateModelIds = Arrays.asList(paramMap.get(Constants.APP_RELATE_MODELS).getParamValue().split(",")).stream().map(s -> Long.valueOf(s)).collect(Collectors.toList());
        }

        appDefinition.setDims(new ArrayList<>());
        appDefinition.setMetrics(new ArrayList<>());

        List<AppColumnPO> appColumns = appColumnDao.selectByAppId(appPO.getId());
        for(AppColumnPO column:appColumns) {
            if(column.getColumnType() == FieldType.DIM.getFieldType()) {
                Dim dim = new Dim();
                dim.setDimCode(column.getColumnCode());
                dim.setDimName(column.getColumnName());
                dim.setDimComment(column.getColumnName());
                dim.setDimFormat(column.getColumnFormat());
                appDefinition.getDims().add(dim);
            }
            if(column.getColumnType() == FieldType.METRIC.getFieldType()) {
                Metric metric = new Metric();
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
//        List<DimPO> dims = dimModelDao.selectByModelIds(relateModelIds);
//        if(CollectionUtils.isEmpty(dims)) {
//            return Lists.newArrayList();
//        }
//        return dims.stream().map(dimPO -> {
//            Dim dim = new Dim();
//            dim.(dimPO.getId());
//            dim.setDimCode(dimPO.getDimCode());
//            dim.setDimName(dimPO.getDimName());
//            dim.setDimComment(dimPO.getDimComment());
//            dim.setDimType(DimType.parse(dimPO.getDimType()));
//            return dim;
//        }).collect(Collectors.toList());
        return null;
    }

    private List<Metric> getMetrics(Long appId) {
        List<MetricModelDetailPO> appMetrics = appMetricModelRelationDao.selectAppMetrics(appId);
        return appMetrics.stream().map(appMetricPO -> {
            Metric metric = new Metric();
            metric.setMetricCode(appMetricPO.getMetricCode());
            metric.setMetricName(appMetricPO.getMetricName());
            metric.setMetricComment(appMetricPO.getMetricComment());
            return metric;
        }).collect(Collectors.toList());
    }
}
