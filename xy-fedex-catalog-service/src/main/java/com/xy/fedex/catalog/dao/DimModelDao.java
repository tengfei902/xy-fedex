package com.xy.fedex.catalog.dao;

import com.xy.fedex.catalog.po.DimModelPO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DimModelDao {
    int deleteByPrimaryKey(Long id);

    int insert(DimModelPO record);

    int insertSelective(DimModelPO record);

    DimModelPO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DimModelPO record);

    int updateByPrimaryKey(DimModelPO record);

    int batchInsert(@Param("dimModels")List<DimModelPO> dimModels);

    int deleteByModel(@Param("modelCode")String modelCode);

    List<DimModelPO> selectByModelCode(@Param("modelCode") String modelCode);
}