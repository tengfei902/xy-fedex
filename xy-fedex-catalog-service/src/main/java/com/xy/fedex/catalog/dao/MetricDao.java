package com.xy.fedex.catalog.dao;

import com.xy.fedex.catalog.po.MetricPO;

public interface MetricDao {
    int deleteByPrimaryKey(Long id);

    int insert(MetricPO record);

    int insertSelective(MetricPO record);

    MetricPO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MetricPO record);

    int updateByPrimaryKey(MetricPO record);
}