package com.xy.fedex.catalog.dao;

import com.xy.fedex.catalog.po.AppColumnPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AppColumnDao {
    int deleteByPrimaryKey(Long id);

    int insert(AppColumnPO record);

    int insertSelective(AppColumnPO record);

    AppColumnPO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AppColumnPO record);

    int updateByPrimaryKey(AppColumnPO record);

    int deleteAppColumns(@Param("appId")Long appId);

    int batchInsert(List<AppColumnPO> columns);

    List<AppColumnPO> selectByAppId(@Param("appId")Long appId);
}