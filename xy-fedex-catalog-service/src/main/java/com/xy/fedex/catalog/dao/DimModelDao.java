package com.xy.fedex.catalog.dao;

import com.xy.fedex.catalog.dto.DimModelRequest;
import com.xy.fedex.catalog.po.DimModelDetailPO;
import com.xy.fedex.catalog.po.DimModelPO;
import com.xy.fedex.catalog.po.DimPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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

    List<DimPO> selectByModelIds(@Param("modelIds") List<Long> modelIds);

    List<DimModelPO> selectByModelId(@Param("modelId") Long modelId);

    List<DimModelDetailPO> selectDimDetailByModelId(@Param("modelId") Long modelId);

    List<DimModelDetailPO> selectByAppDimId(@Param("appId")Long appId,@Param("dimId")Long dimId);

    List<DimModelPO> selectDimModels(DimModelRequest dimModelRequest);

    int deleteByModelId(@Param("modelId")Long modelId);
}