package com.xy.fedex.catalog.dao;

import com.xy.fedex.catalog.po.ModelParamsPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ModelParamsDao {
    int deleteByPrimaryKey(Long id);

    int insert(ModelParamsPO record);

    int insertSelective(ModelParamsPO record);

    ModelParamsPO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ModelParamsPO record);

    int updateByPrimaryKey(ModelParamsPO record);

    List<ModelParamsPO> selectByModelId(@Param("modelId")Long modelId);

    List<ModelParamsPO> selectByModelIds(@Param("modelIds")List<Long> modelIds);

    int batchInsert(@Param("modelParams") List<ModelParamsPO> modelParams);

    int deleteByModelId(@Param("modelId") Long modelId);
}