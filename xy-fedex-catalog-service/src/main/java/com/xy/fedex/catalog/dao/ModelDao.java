package com.xy.fedex.catalog.dao;

import com.xy.fedex.catalog.po.ModelPO;

public interface ModelDao {
    int deleteByPrimaryKey(Long id);

    int insert(ModelPO record);

    int insertSelective(ModelPO record);

    ModelPO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ModelPO record);

    int updateByPrimaryKeyWithBLOBs(ModelPO record);

    int updateByPrimaryKey(ModelPO record);
}