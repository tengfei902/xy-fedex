package com.xy.fedex.catalog.service.impl;

import com.xy.fedex.catalog.api.dto.AppRequest;
import com.xy.fedex.catalog.common.definition.AppDefinition;
import com.xy.fedex.catalog.dao.AppDao;
import com.xy.fedex.catalog.dao.AppModelRelationDao;
import com.xy.fedex.catalog.exception.AppNotFoundException;
import com.xy.fedex.catalog.po.AppModelRelationPO;
import com.xy.fedex.catalog.po.AppPO;
import com.xy.fedex.catalog.service.AppService;
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

    @Transactional
    @Override
    public Long saveApp(AppRequest appRequest) {
        Long appId = saveOrUpdateApp(appRequest);
        saveAppModelRelation(appId,appRequest.getRelatedModelIds());
        return appId;
    }

    private Long saveOrUpdateApp(AppRequest appRequest) {
        Long appId = appRequest.getAppId();
        if(Objects.isNull(appId)) {
            return createApp(appRequest);
        } else {
            return updateApp(appRequest);
        }
    }

    private Long createApp(AppRequest appRequest) {
        AppPO appPO = new AppPO();
        appPO.setAppName(appRequest.getAppName());
        appPO.setAppDesc(appRequest.getAppDesc());
        appPO.setBizLineId(appRequest.getBizLineId());
        appPO.setTenantId(appRequest.getTenantId());
        appDao.insertSelective(appPO);
        return appPO.getId();
    }

    private Long updateApp(AppRequest appRequest) {
        AppPO app = appDao.selectByPrimaryKey(appRequest.getAppId());
        if(Objects.isNull(app)) {
            throw new AppNotFoundException("app not found:"+appRequest.getAppId());
        }
        AppPO updateApp = new AppPO();
        updateApp.setId(appRequest.getAppId());
        updateApp.setAppName(appRequest.getAppName());
        updateApp.setAppDesc(appRequest.getAppDesc());
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
    public AppDefinition getApp(Long appId) {
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
        appDefinition.setMetrics(null);
        return null;
    }
}
