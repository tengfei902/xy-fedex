package com.xy.fedex.catalog.dao;

import com.xy.fedex.catalog.po.ModelPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ModelDao {
    int deleteByPrimaryKey(Long id);

    int insert(ModelPO record);

    int insertSelective(ModelPO record);

    ModelPO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ModelPO record);

    int updateByPrimaryKey(ModelPO record);

    List<ModelPO> selectByModelCodes(@Param("modelCodes") List<String> modelCodes);

    ModelPO selectByModelCode(@Param("modelCode") String modelCode);

    List<ModelPO> selectByModelIds(@Param("modelIds") List<Long> modelIds);
}