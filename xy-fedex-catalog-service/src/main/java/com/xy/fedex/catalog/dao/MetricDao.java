package com.xy.fedex.catalog.dao;

import com.xy.fedex.catalog.po.MetricPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MetricDao {
    int deleteByPrimaryKey(Long id);

    int insert(MetricPO record);

    int insertSelective(MetricPO record);

    MetricPO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MetricPO record);

    int updateByPrimaryKey(MetricPO record);

    MetricPO selectByMetricCode(@Param("bizLineId")Long bizLineId,@Param("metricCode")String metricCode);

    List<MetricPO> selectAllMetrics(@Param("bizLineId") Long bizLineId);
}