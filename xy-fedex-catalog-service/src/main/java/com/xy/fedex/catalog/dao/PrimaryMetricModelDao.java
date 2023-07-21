package com.xy.fedex.catalog.dao;

import com.xy.fedex.catalog.dto.MetricModelRequest;
import com.xy.fedex.catalog.po.PrimaryMetricModelPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PrimaryMetricModelDao {
    int deleteByPrimaryKey(Long id);

    int insert(PrimaryMetricModelPO record);

    int insertSelective(PrimaryMetricModelPO record);

    PrimaryMetricModelPO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PrimaryMetricModelPO record);

    int updateByPrimaryKey(PrimaryMetricModelPO record);

    List<PrimaryMetricModelPO> selectMetricModels(MetricModelRequest request);
}