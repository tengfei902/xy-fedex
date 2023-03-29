package com.xy.fedex.catalog.dao;

import com.xy.fedex.catalog.po.AppMetricModelRelationPO;
import com.xy.fedex.catalog.po.AppMetricPO;
import com.xy.fedex.catalog.po.MetricModelDetailPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AppMetricModelRelationDao {
    int deleteByPrimaryKey(Long id);

    int insert(AppMetricModelRelationPO record);

    int insertSelective(AppMetricModelRelationPO record);

    AppMetricModelRelationPO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AppMetricModelRelationPO record);

    int updateByPrimaryKey(AppMetricModelRelationPO record);

    List<MetricModelDetailPO> selectAppMetrics(@Param("appId") Long appId);

    int batchInsert(@Param("relations") List<AppMetricModelRelationPO> records);

    int deleteByAppId(@Param("appId") Long appId);

    MetricModelDetailPO selectAppMetricModel(@Param("appId")Long appId,@Param("metricModelId") Long metricModelId);

    List<MetricModelDetailPO> selectAppMetricModels(@Param("appId") Long appId,@Param("metricId") Long metricId);
}