package com.xy.fedex.catalog.dao;

import com.xy.fedex.catalog.po.AppParamsPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AppParamsDao {
    int deleteByPrimaryKey(Long id);

    int insert(AppParamsPO record);

    int insertSelective(AppParamsPO record);

    AppParamsPO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AppParamsPO record);

    int updateByPrimaryKey(AppParamsPO record);

    List<AppParamsPO> selectByAppId(@Param("appId") Long appId);

    AppParamsPO selectByParamKey(@Param("appId") Long appId,@Param("paramKey") String paramKey);

    int updateByParamKeySelective(AppParamsPO record);
}