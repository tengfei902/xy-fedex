package com.xy.fedex.catalog.dao;

import com.xy.fedex.catalog.po.AppModelRelationPO;

public interface AppModelRelationDao {
    int deleteByPrimaryKey(Long id);

    int insert(AppModelRelationPO record);

    int insertSelective(AppModelRelationPO record);

    AppModelRelationPO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AppModelRelationPO record);

    int updateByPrimaryKey(AppModelRelationPO record);
}