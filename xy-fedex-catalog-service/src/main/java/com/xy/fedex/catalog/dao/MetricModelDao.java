package com.xy.fedex.catalog.dao;

import com.xy.fedex.catalog.po.MetricModelPO;

import java.util.List;

public interface MetricModelDao {
    int deleteByPrimaryKey(Long id);

    int insert(MetricModelPO record);

    int insertSelective(MetricModelPO record);

    MetricModelPO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MetricModelPO record);

    int updateByPrimaryKey(MetricModelPO record);

    int batchInsert(List<MetricModelPO> metricModels);
}