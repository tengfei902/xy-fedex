package com.xy.fedex.catalog.dao;

import com.xy.fedex.catalog.po.DimModelPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface DimModelDao {
    int deleteByPrimaryKey(Long id);

    int insert(DimModelPO record);

    int insertSelective(DimModelPO record);

    DimModelPO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DimModelPO record);

    int updateByPrimaryKey(DimModelPO record);

    int batchInsert(List<DimModelPO> records);
}