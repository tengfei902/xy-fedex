package com.xy.fedex.catalog.dao;

import com.xy.fedex.catalog.po.ModelPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ModelDao {
    int deleteByPrimaryKey(Long id);

    int insert(ModelPO record);

    int insertSelective(ModelPO record);

    ModelPO selectByPrimaryKey(Long id);

    ModelPO selectByName(@Param("bizLineId")Long bizLineId,@Param("modelName")String modelName);

    int updateByPrimaryKeySelective(ModelPO record);
}