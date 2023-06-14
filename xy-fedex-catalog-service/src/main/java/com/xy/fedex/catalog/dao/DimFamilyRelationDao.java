package com.xy.fedex.catalog.dao;

import com.xy.fedex.catalog.po.DimFamilyRelationPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DimFamilyRelationDao {
    int deleteByPrimaryKey(Long id);

    int insert(DimFamilyRelationPO record);

    int insertSelective(DimFamilyRelationPO record);

    DimFamilyRelationPO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DimFamilyRelationPO record);

    int updateByPrimaryKey(DimFamilyRelationPO record);

    int deleteByDimId(@Param("dimId") Long dimId);
}