package com.xy.fedex.catalog.dao;

import com.xy.fedex.catalog.po.PrimaryMetricModelPO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PrimaryMetricModelDao {
    int deleteByPrimaryKey(Long id);

    int insert(PrimaryMetricModelPO record);

    int insertSelective(PrimaryMetricModelPO record);

    PrimaryMetricModelPO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PrimaryMetricModelPO record);

    int updateByPrimaryKey(PrimaryMetricModelPO record);

    int batchInsert(@Param("primaryMetricModels") List<PrimaryMetricModelPO> primaryMetricModels);

    int deleteByModel(@Param("modelCode")String modelCode);

    List<PrimaryMetricModelPO> selectByModelCode(@Param("modelCode") String modelCode);
}