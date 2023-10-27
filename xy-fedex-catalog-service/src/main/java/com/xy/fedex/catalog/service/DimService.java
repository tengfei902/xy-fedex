package com.xy.fedex.catalog.service;

import com.xy.fedex.catalog.common.definition.field.Dim;
import com.xy.fedex.catalog.common.definition.field.impl.DimModel;

import java.util.List;

/**
 * @author tengfei
 */
public interface DimService {

    /**
     * 查询维度列表
     * @param dimCodes
     * @return
     */
    List<Dim> getDims(List<String> dimCodes);

    void saveDims(List<Dim> dims);

    void saveDimModels(List<DimModel> dimModels);

    void deleteDimModels(String modelCode);

    List<DimModel> getDimModels(String modelCode);
}
