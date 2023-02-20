package com.xy.fedex.catalog.service.impl;

import com.xy.fedex.catalog.dao.DimDao;
import com.xy.fedex.catalog.dao.MetricDao;
import com.xy.fedex.catalog.dto.DimDTO;
import com.xy.fedex.catalog.dto.MetricDTO;
import com.xy.fedex.catalog.exception.DimNotFoundException;
import com.xy.fedex.catalog.exception.MetricNotFoundException;
import com.xy.fedex.catalog.exception.UpdateFailedException;
import com.xy.fedex.catalog.po.DimPO;
import com.xy.fedex.catalog.po.MetricPO;
import com.xy.fedex.catalog.service.MetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class MetaServiceImpl implements MetaService {
    @Autowired
    private MetricDao metricDao;
    @Autowired
    private DimDao dimDao;

    @Override
    public MetricDTO getMetric(Long metricId) {
        return null;
    }

    @Override
    public DimDTO getDim(Long dimId) {
        return null;
    }

    @Override
    public Long saveMetric(MetricDTO metric) {
        MetricPO metricPO = new MetricPO();
        if(Objects.isNull(metric.getMetricId())) {
            //save
            metricPO.setTenantId(metric.getTenantId());
            metricPO.setBizLineId(metric.getBizLineId());
            metricPO.setSubjectId(metric.getSubjectId());
            metricPO.setMetricCode(metric.getMetricCode());
            metricPO.setMetricName(metric.getMetricName());
            metricPO.setMetricComment(metric.getMetricComment());
            metricPO.setMetricFormat(metric.getMetricFormat());
            metricPO.setUnit(metric.getUnit());

            metricDao.insertSelective(metricPO);
            return metricPO.getId();
        } else {
            metricPO = metricDao.selectByPrimaryKey(metric.getMetricId());
            if(Objects.isNull(metricPO)) {
                throw new MetricNotFoundException("metric not found:"+metric.getMetricId());
            }
            MetricPO updateMetric = new MetricPO();
            updateMetric.setMetricName(metric.getMetricName());
            updateMetric.setMetricFormat(metric.getMetricFormat());
            updateMetric.setMetricComment(metric.getMetricComment());
            updateMetric.setMetricFormat(metric.getMetricFormat());
            updateMetric.setSubjectId(metric.getSubjectId());
            updateMetric.setUnit(metric.getUnit());
            updateMetric.setId(metric.getMetricId());
            int cnt = metricDao.updateByPrimaryKeySelective(updateMetric);
            if(cnt <= 0) {
                throw new UpdateFailedException("update metric failed,metric id:"+metric.getMetricId());
            }
            return metricPO.getId();
        }
    }

    @Override
    public Long saveDim(DimDTO dim) {
        Long dimId = dim.getDimId();
        if(Objects.isNull(dimId)) {
            DimPO dimPO = new DimPO();
            dimPO.setDimCode(dim.getDimCode());
            dimPO.setDimName(dim.getDimName());
            dimPO.setDimComment(dim.getDimComment());
            dimPO.setTenantId(dim.getTenantId());
            dimDao.insertSelective(dimPO);
            return dimPO.getId();
        } else {
            DimPO dimPO = dimDao.selectByPrimaryKey(dimId);
            if(Objects.isNull(dimPO)) {
                throw new DimNotFoundException("dim not found:"+dimId);
            }
            DimPO updateDim = new DimPO();
            updateDim.setId(dim.getDimId());
            updateDim.setDimName(dim.getDimName());
            updateDim.setDimComment(dim.getDimComment());
            dimDao.updateByPrimaryKeySelective(updateDim);
            return dimPO.getId();
        }
    }
}
