package com.xy.fedex.catalog.dao;

import com.xy.fedex.catalog.po.DimModelPO;

import java.util.List;

public interface DimModelDao {
    int deleteByPrimaryKey(Long id);

    int insert(DimModelPO record);

    int insertSelective(DimModelPO record);

    DimModelPO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DimModelPO record);

    int updateByPrimaryKey(DimModelPO record);

    int batchInsert(List<DimModelPO> records);
}