package com.xy.fedex.catalog.dao;

import com.xy.fedex.catalog.po.DimPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DimDao {
    int deleteByPrimaryKey(Long id);

    int insert(DimPO record);

    int insertSelective(DimPO record);

    DimPO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DimPO record);

    int updateByPrimaryKey(DimPO record);

    DimPO selectByDimCode(@Param("bizLineId") Long bizLineId,@Param("dimCode")String dimCode);
}