package com.xy.fedex.catalog.dao;

import com.xy.fedex.catalog.po.PrimaryMetricModelParamsPO;

public interface PrimaryMetricModelParamsDao {
    int deleteByPrimaryKey(Long id);

    int insert(PrimaryMetricModelParamsPO record);

    int insertSelective(PrimaryMetricModelParamsPO record);

    PrimaryMetricModelParamsPO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PrimaryMetricModelParamsPO record);

    int updateByPrimaryKey(PrimaryMetricModelParamsPO record);
}