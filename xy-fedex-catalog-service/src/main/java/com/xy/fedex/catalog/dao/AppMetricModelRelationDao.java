package com.xy.fedex.catalog.dao;

import com.xy.fedex.catalog.po.AppMetricModelRelationPO;
import com.xy.fedex.catalog.po.AppMetricPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AppMetricModelRelationDao {
    int deleteByPrimaryKey(Long id);

    int insert(AppMetricModelRelationPO record);

    int insertSelective(AppMetricModelRelationPO record);

    AppMetricModelRelationPO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AppMetricModelRelationPO record);

    int updateByPrimaryKey(AppMetricModelRelationPO record);

    List<AppMetricPO> selectAppMetrics(@Param("appId") Long appId);
}