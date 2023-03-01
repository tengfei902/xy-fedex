package com.xy.fedex.catalog.dao;

import com.xy.fedex.catalog.po.AppModelRelationPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AppModelRelationDao {
    int deleteByPrimaryKey(Long id);

    int insert(AppModelRelationPO record);

    int insertSelective(AppModelRelationPO record);

    AppModelRelationPO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AppModelRelationPO record);

    int updateByPrimaryKey(AppModelRelationPO record);

    int deleteByAppId(@Param("appId") Long appId);

    int batchInsert(List<AppModelRelationPO> relations);

    List<AppModelRelationPO> selectByAppId(@Param("appId") Long appId);
}