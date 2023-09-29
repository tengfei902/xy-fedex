package com.xy.fedex.catalog.service.impl;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.statement.SQLSelect;
import com.alibaba.druid.sql.ast.statement.SQLSelectItem;
import com.alibaba.druid.sql.ast.statement.SQLSelectQueryBlock;
import com.google.common.base.Joiner;
import com.xy.fedex.catalog.common.definition.field.Metric;
import com.xy.fedex.catalog.dao.MetricDao;
import com.xy.fedex.catalog.exception.CatalogServiceExceptions;
import com.xy.fedex.catalog.exception.ErrorCode;
import com.xy.fedex.catalog.po.MetricPO;
import com.xy.fedex.catalog.po.PrimaryMetricModelPO;
import com.xy.fedex.catalog.service.MetricService;
import com.xy.fedex.catalog.utils.CatalogUtils;
import com.xy.fedex.dsl.utility.SQLExprUtils;
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

/**
 * @author tengfei
 */
@Service
public class MetricServiceImpl implements MetricService {
    @Autowired
    private MetricDao metricDao;

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

    @Override
    public void saveMetricModels(String tableSource) {

    }
}
