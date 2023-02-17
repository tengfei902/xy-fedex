package com.xy.fedex.catalog.dao;

import com.xy.fedex.catalog.po.DimPO;

public interface DimDao {
    int deleteByPrimaryKey(Long id);

    int insert(DimPO record);

    int insertSelective(DimPO record);

    DimPO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DimPO record);

    int updateByPrimaryKey(DimPO record);
}