package com.xy.fedex.catalog.dao;

import com.xy.fedex.catalog.po.DimFamilyPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DimFamilyDao {
    int deleteByPrimaryKey(Long id);

    int insert(DimFamilyPO record);

    int insertSelective(DimFamilyPO record);

    DimFamilyPO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DimFamilyPO record);

    int updateByPrimaryKey(DimFamilyPO record);

    int updateMasterDim(@Param("dimFamilyId")Long dimFamilyId,@Param("masterDimId") Long masterDimId);

    int removeMasterDim(@Param("dimFamilyId")Long dimFamilyId,@Param("masterDimId") Long masterDimId);
}