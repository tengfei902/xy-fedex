package com.xy.fedex.catalog.dao;

import com.xy.fedex.catalog.po.AppPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AppDao {
    int deleteByPrimaryKey(Long id);

    int insert(AppPO record);

    int insertSelective(AppPO record);

    AppPO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AppPO record);

    int updateByPrimaryKey(AppPO record);

    AppPO selectByName(@Param("bizLineId")Long bizLineId,@Param("appName")String appName);
}