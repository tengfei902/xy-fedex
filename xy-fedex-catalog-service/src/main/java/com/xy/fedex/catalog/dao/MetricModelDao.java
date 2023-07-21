package com.xy.fedex.catalog.dao;

import com.xy.fedex.catalog.dto.MetricModelRequest;
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

    int updateByPrimaryKeyWithBLOBs(MetricModelPO record);

    int updateByPrimaryKey(MetricModelPO record);

    List<MetricModelPO> selectMetricModels(MetricModelRequest metricModelRequest);

    Integer getMetricModelCnt(MetricModelRequest metricModelRequest);

    int batchInsert(List<MetricModelPO> metricModels);

    int deleteByModelId(@Param("modelId") String modelId);
}