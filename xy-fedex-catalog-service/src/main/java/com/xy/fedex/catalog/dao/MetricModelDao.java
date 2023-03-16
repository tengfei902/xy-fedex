package com.xy.fedex.catalog.dao;

import com.xy.fedex.catalog.po.MetricModelDetailPO;
import com.xy.fedex.catalog.po.MetricModelPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface MetricModelDao {
    int deleteByPrimaryKey(Long id);

    int insert(MetricModelPO record);

    int insertSelective(MetricModelPO record);

    MetricModelPO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MetricModelPO record);

    int updateByPrimaryKey(MetricModelPO record);

    int batchInsert(List<MetricModelPO> metricModels);

    List<MetricModelPO> selectByModelId(@Param("modelId") Long modelId);

    List<MetricModelPO> selectByModelIds(@Param("modelIds") List<Long> modelIds);

    List<MetricModelDetailPO> selectMetricDetailByModelId(@Param("modelId") Long modelId);
}