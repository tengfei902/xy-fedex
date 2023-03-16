package com.xy.fedex.catalog.dao;

import com.xy.fedex.catalog.po.AppMetricModelRelation;

public interface AppMetricModelRelationMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AppMetricModelRelation record);

    int insertSelective(AppMetricModelRelation record);

    AppMetricModelRelation selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AppMetricModelRelation record);

    int updateByPrimaryKey(AppMetricModelRelation record);
}