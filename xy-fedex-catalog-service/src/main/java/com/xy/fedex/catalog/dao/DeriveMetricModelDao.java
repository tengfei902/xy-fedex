package com.xy.fedex.catalog.dao;

import com.xy.fedex.catalog.po.DeriveMetricModelPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface DeriveMetricModelDao {
    int deleteByPrimaryKey(Long id);

    int insert(DeriveMetricModelPO record);

    int insertSelective(DeriveMetricModelPO record);

    DeriveMetricModelPO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DeriveMetricModelPO record);

    int updateByPrimaryKey(DeriveMetricModelPO record);

    int deleteByModel(@Param("modelCode")String modelCode);
}