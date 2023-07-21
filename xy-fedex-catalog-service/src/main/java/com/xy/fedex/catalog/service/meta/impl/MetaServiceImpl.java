package com.xy.fedex.catalog.service.meta.impl;

import com.xy.fedex.catalog.common.definition.field.impl.DimModel;
import com.xy.fedex.catalog.common.enums.MetricType;
import com.xy.fedex.catalog.dao.*;
import com.xy.fedex.catalog.dto.*;
import com.xy.fedex.catalog.exception.*;
import com.xy.fedex.catalog.po.*;
import com.xy.fedex.catalog.service.meta.MetaMatchService;
import com.xy.fedex.catalog.service.meta.MetaService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
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
}
