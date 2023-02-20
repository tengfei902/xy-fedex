package com.xy.fedex.catalog.dao;

import com.xy.fedex.catalog.po.BizLinePO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BizLineDao {
    int deleteByPrimaryKey(Long id);

    int insert(BizLinePO record);

    int insertSelective(BizLinePO record);

    BizLinePO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BizLinePO record);

    int updateByPrimaryKey(BizLinePO record);
}