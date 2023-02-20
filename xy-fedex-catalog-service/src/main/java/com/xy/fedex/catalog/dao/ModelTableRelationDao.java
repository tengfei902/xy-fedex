package com.xy.fedex.catalog.dao;

import com.xy.fedex.catalog.po.ModelTableRelationPO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ModelTableRelationDao {
    int deleteByPrimaryKey(Long id);

    int insert(ModelTableRelationPO record);

    int insertSelective(ModelTableRelationPO record);

    ModelTableRelationPO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ModelTableRelationPO record);

    int updateByPrimaryKey(ModelTableRelationPO record);
}