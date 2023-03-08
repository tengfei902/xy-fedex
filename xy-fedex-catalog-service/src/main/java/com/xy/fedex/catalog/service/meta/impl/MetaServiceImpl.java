package com.xy.fedex.catalog.service.meta.impl;

import com.xy.fedex.admin.api.vo.response.TableDetailVO;
import com.xy.fedex.catalog.common.definition.column.TableField;
import com.xy.fedex.catalog.common.definition.field.MetaField;
import com.xy.fedex.catalog.common.enums.MetricType;
import com.xy.fedex.catalog.dao.DimDao;
import com.xy.fedex.catalog.dao.MetricDao;
import com.xy.fedex.catalog.dto.DimDTO;
import com.xy.fedex.catalog.dto.MetricDTO;
import com.xy.fedex.catalog.dto.TableAliasDTO;
import com.xy.fedex.catalog.exception.DimNotFoundException;
import com.xy.fedex.catalog.exception.MetricNotFoundException;
import com.xy.fedex.catalog.exception.UpdateFailedException;
import com.xy.fedex.catalog.po.DimPO;
import com.xy.fedex.catalog.po.MetricPO;
import com.xy.fedex.catalog.service.meta.MetaMatchService;
import com.xy.fedex.catalog.service.meta.MetaService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class MetaServiceImpl implements MetaService {
    @Autowired
    private MetricDao metricDao;
    @Autowired
    private DimDao dimDao;
    @Autowired
    private MetaMatchService metaMatchService;

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
        MetricPO metric = metricDao.selectByMetricCode(bizLineId,metricCode);
        if(Objects.isNull(metric)) {
            throw new MetricNotFoundException(String.format("metric not found, bizLineId:%s,metricCode:%s",bizLineId,metricCode));
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
        if(CollectionUtils.isEmpty(allMetrics)) {
            return Collections.EMPTY_LIST;
        }
        return allMetrics.stream().map(metricPO -> convert(metricPO)).collect(Collectors.toList());
    }

    @Override
    public DimDTO getDim(Long dimId) {
        DimPO dim = dimDao.selectByPrimaryKey(dimId);
        if(Objects.isNull(dim)) {
            throw new DimNotFoundException("dim not found:"+dimId);
        }
        return DimDTO.builder().dimId(dim.getId()).dimCode(dim.getDimCode()).dimComment(dim.getDimComment()).dimName(dim.getDimName()).dimType(dim.getDimType()).build();
    }

    @Override
    public List<DimDTO> getDims(Long bizLineId) {
        return null;
    }

    @Override
    public Long saveMetric(MetricDTO metric) {
        MetricPO metricPO = new MetricPO();
        if (Objects.isNull(metric.getMetricId())) {
            //save
            metricPO.setBizLineId(metric.getBizLineId());
            metricPO.setSubjectId(metric.getSubjectId());
            metricPO.setMetricCode(metric.getMetricCode());
            metricPO.setMetricName(metric.getMetricName());
            metricPO.setMetricComment(metric.getMetricComment());
            metricPO.setMetricFormat(metric.getMetricFormat());
            metricPO.setUnit(metric.getUnit());
            metricPO.setFormula(metric.getFormula());
            metricPO.setMetricType(Objects.isNull(metric.getMetricType())? MetricType.PRIMARY.getMetricType() : metric.getMetricType().getMetricType());

            metricDao.insertSelective(metricPO);
            return metricPO.getId();
        } else {
            metricPO = metricDao.selectByPrimaryKey(metric.getMetricId());
            if (Objects.isNull(metricPO)) {
                throw new MetricNotFoundException("metric not found:" + metric.getMetricId());
            }
            MetricPO updateMetric = new MetricPO();
            updateMetric.setMetricName(metric.getMetricName());
            updateMetric.setMetricFormat(metric.getMetricFormat());
            updateMetric.setMetricComment(metric.getMetricComment());
            updateMetric.setMetricFormat(metric.getMetricFormat());
            updateMetric.setSubjectId(metric.getSubjectId());
            updateMetric.setUnit(metric.getUnit());
            updateMetric.setFormula(metric.getFormula());
            updateMetric.setMetricType(Objects.isNull(metric.getMetricType())? MetricType.PRIMARY.getMetricType() : metric.getMetricType().getMetricType());
            updateMetric.setId(metric.getMetricId());
            int cnt = metricDao.updateByPrimaryKeySelective(updateMetric);
            if (cnt <= 0) {
                throw new UpdateFailedException("update metric failed,metric id:" + metric.getMetricId());
            }
            return metricPO.getId();
        }
    }

    @Override
    public Long saveDim(DimDTO dim) {
        Long dimId = dim.getDimId();
        if (Objects.isNull(dimId)) {
            DimPO dimPO = new DimPO();
            dimPO.setDimCode(dim.getDimCode());
            dimPO.setDimName(dim.getDimName());
            dimPO.setDimComment(dim.getDimComment());
            dimDao.insertSelective(dimPO);
            return dimPO.getId();
        } else {
            DimPO dimPO = dimDao.selectByPrimaryKey(dimId);
            if (Objects.isNull(dimPO)) {
                throw new DimNotFoundException("dim not found:" + dimId);
            }
            DimPO updateDim = new DimPO();
            updateDim.setId(dim.getDimId());
            updateDim.setDimName(dim.getDimName());
            updateDim.setDimComment(dim.getDimComment());
            dimDao.updateByPrimaryKeySelective(updateDim);
            return dimPO.getId();
        }
    }

    @Override
    public void matchMetricAndDim(List<TableAliasDTO> tableAliasList) {
        for(TableAliasDTO tableAliasDTO:tableAliasList) {
            String alias = tableAliasDTO.getAlias();
            TableDetailVO table = tableAliasDTO.getTable();
            for(TableDetailVO.Column column :table.getColumns()) {
                String fieldName = column.getColumnName();

            }
        }
    }

    @Override
    public List<TableField> matchMeta(Long bizLineId,List<TableField> tableFields) {
        for(TableField tableField:tableFields) {
            String columnName = tableField.getColumnName();
            MetaField relateMetaField = metaMatchService.getRelateMetaField(bizLineId,columnName);
            tableField.setRelateField(relateMetaField);
        }
        return tableFields;
    }
}
