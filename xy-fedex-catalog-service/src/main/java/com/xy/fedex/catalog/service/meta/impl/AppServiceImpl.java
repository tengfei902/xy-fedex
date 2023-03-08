package com.xy.fedex.catalog.service.meta.impl;

import com.xy.fedex.catalog.api.dto.request.SaveAppRequest;
import com.xy.fedex.catalog.common.definition.AppDefinition;
import com.xy.fedex.catalog.dao.AppDao;
import com.xy.fedex.catalog.dao.AppMetricModelRelationDao;
import com.xy.fedex.catalog.dao.AppModelRelationDao;
import com.xy.fedex.catalog.exception.AppNotFoundException;
import com.xy.fedex.catalog.po.AppMetricPO;
import com.xy.fedex.catalog.po.AppModelRelationPO;
import com.xy.fedex.catalog.po.AppPO;
import com.xy.fedex.catalog.service.meta.AppService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    @Override
    public Long saveApp(SaveAppRequest saveAppRequest) {
        Long appId = saveOrUpdateApp(saveAppRequest);
        saveAppModelRelation(appId, saveAppRequest.getRelatedModelIds());
        return appId;
    }

    private Long saveOrUpdateApp(SaveAppRequest saveAppRequest) {
        Long appId = saveAppRequest.getAppId();
        if(Objects.isNull(appId)) {
            return createApp(saveAppRequest);
        } else {
            return updateApp(saveAppRequest);
        }
    }

    private Long createApp(SaveAppRequest saveAppRequest) {
        AppPO appPO = new AppPO();
        appPO.setAppName(saveAppRequest.getAppName());
        appPO.setAppDesc(saveAppRequest.getAppDesc());
        appPO.setBizLineId(saveAppRequest.getBizLineId());
        appDao.insertSelective(appPO);
        return appPO.getId();
    }

    private Long updateApp(SaveAppRequest saveAppRequest) {
        AppPO app = appDao.selectByPrimaryKey(saveAppRequest.getAppId());
        if(Objects.isNull(app)) {
            throw new AppNotFoundException("app not found:"+ saveAppRequest.getAppId());
        }
        AppPO updateApp = new AppPO();
        updateApp.setId(saveAppRequest.getAppId());
        updateApp.setAppName(saveAppRequest.getAppName());
        updateApp.setAppDesc(saveAppRequest.getAppDesc());
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

    @Override
    public AppDefinition getApp(Long bizLineId,Long appId) {
        AppPO appPO = appDao.selectByPrimaryKey(appId);
        if(Objects.isNull(appPO)) {
            throw new AppNotFoundException("app not found:"+appId);
        }
        AppDefinition appDefinition = new AppDefinition();
        appDefinition.setAppId(appPO.getId());
        appDefinition.setAppName(appPO.getAppName());
        appDefinition.setAppDesc(appPO.getAppDesc());

        List<AppModelRelationPO> appModelRelations = appModelRelationDao.selectByAppId(appId);
        if(!CollectionUtils.isEmpty(appModelRelations)) {
            appDefinition.setModelIds(appModelRelations.stream().map(AppModelRelationPO::getModelId).collect(Collectors.toList()));
        }
        appDefinition.setDims(null);
        appDefinition.setMetrics(getMetrics(appId));
        return appDefinition;
    }

    private List<AppDefinition.Metric> getMetrics(Long appId) {
        List<AppMetricPO> appMetrics = appMetricModelRelationDao.selectAppMetrics(appId);
        return appMetrics.stream().map(appMetricPO -> {
            AppDefinition.Metric metric = new AppDefinition.Metric();
            metric.setMetricModelId(appMetricPO.getMetricModelId());
            metric.setMetricId(appMetricPO.getMetricId());
            metric.setMetricCode(appMetricPO.getMetricCode());
            metric.setMetricName(appMetricPO.getMetricName());
            metric.setMetricComment(appMetricPO.getMetricComment());
            metric.setFormula(appMetricPO.getFormula());
            return metric;
        }).collect(Collectors.toList());
    }
}
