package com.xy.fedex.catalog.service.meta.impl;

import com.xy.fedex.catalog.common.definition.field.impl.DimModel;
import com.xy.fedex.catalog.dao.DimModelDao;
import com.xy.fedex.catalog.dto.DimModelRequest;
import com.xy.fedex.catalog.po.DimModelPO;
import com.xy.fedex.catalog.service.meta.DimModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DimModelServiceImpl implements DimModelService {
    @Autowired
    private DimModelDao dimModelDao;

    @Override
    public List<DimModel> getDimModels(DimModelRequest dimModelRequest) {
        List<DimModelPO> dimModels = dimModelDao.selectDimModels(dimModelRequest);
        return dimModels.stream().map(dimModelPO -> {
            DimModel dimModel = new DimModel();
            dimModel.setDimModelId(dimModelPO.getId());
            dimModel.setModelId(dimModelPO.getModelId());
            dimModel.setDimCode(dimModelPO.getDimCode());
            dimModel.setFormula(dimModelPO.getFormula());
            return dimModel;
        }).collect(Collectors.toList());
    }
}
