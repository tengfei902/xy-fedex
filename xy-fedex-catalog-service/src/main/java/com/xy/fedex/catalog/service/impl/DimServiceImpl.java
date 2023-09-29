package com.xy.fedex.catalog.service.impl;

import com.google.common.base.Joiner;
import com.xy.fedex.catalog.common.definition.field.Dim;
import com.xy.fedex.catalog.dao.DimDao;
import com.xy.fedex.catalog.exception.CatalogServiceExceptions;
import com.xy.fedex.catalog.exception.ErrorCode;
import com.xy.fedex.catalog.po.DimPO;
import com.xy.fedex.catalog.service.DimService;
import com.xy.fedex.catalog.utils.CatalogUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author tengfei
 */
@Service
public class DimServiceImpl implements DimService {
    @Autowired
    private DimDao dimDao;

    @Override
    public List<Dim> getDims(List<String> dimCodes) {
        if(CollectionUtils.isEmpty(dimCodes)) {
            return Collections.EMPTY_LIST;
        }
        dimCodes = dimCodes.stream().map(s -> CatalogUtils.getObjectFullName(s)).collect(Collectors.toList());
        List<DimPO> existedDims = dimDao.selectByDimCodes(dimCodes);
        if(existedDims.size() != dimCodes.size()) {
            List<String> existedDimCodes = existedDims.stream().map(DimPO::getDimCode).collect(Collectors.toList());
            dimCodes.removeAll(existedDimCodes);
            throw CatalogServiceExceptions.newException(ErrorCode.DIM_NOT_FOUND_FAILED, CatalogUtils.getCurrentProject(), Joiner.on(",").join(dimCodes));
        }
        return existedDims.stream().map(dimPO -> {
            Dim dim = new Dim();
            dim.setDimCode(CatalogUtils.getObject(dimPO.getDimCode()).getObjectName());
            dim.setDimName(dimPO.getDimName());
            dim.setDimComment(dimPO.getDimComment());
            dim.setDimType(dimPO.getDimType());
            return dim;
        }).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void saveDims(List<Dim> dims) {
        List<String> dimCodes = dims.stream().map(dim -> CatalogUtils.getObjectFullName(dim.getDimCode())).collect(Collectors.toList());
        List<DimPO> existedDims = dimDao.selectByDimCodes(dimCodes);
        Map<String,DimPO> existedDimMap = existedDims.stream().collect(Collectors.toMap(DimPO::getDimCode, Function.identity()));

        List<DimPO> dimList = dims.stream().map(dim -> {
            DimPO dimPO = new DimPO();
            dimPO.setDimCode(CatalogUtils.getObjectFullName(dim.getDimCode()));
            dimPO.setDimName(StringUtils.isEmpty(dim.getDimName())?"":dim.getDimName());
            dimPO.setDimComment(StringUtils.isEmpty(dim.getDimComment())?"":dim.getDimComment());
            dimPO.setDimType(StringUtils.isEmpty(dim.getDimType())?"":dim.getDimType());
            dimPO.setCreator(CatalogUtils.getCurrentUserName());
            return dimPO;
        }).collect(Collectors.toList());

        List<DimPO> insertDims = dimList.stream().filter(dimPO -> !existedDimMap.containsKey(dimPO.getDimCode())).collect(Collectors.toList());
        if(!CollectionUtils.isEmpty(insertDims)) {
            dimDao.batchInsert(insertDims);
        }
        List<DimPO> updateDims = dimList.stream().filter(dimPO -> existedDimMap.containsKey(dimPO.getDimCode())).collect(Collectors.toList());
        if(!CollectionUtils.isEmpty(updateDims)) {
            for(DimPO dim:updateDims) {
                dim.setId(existedDimMap.get(dim.getDimCode()).getId());
                dimDao.updateByPrimaryKeySelective(dim);
            }
        }
    }
}
