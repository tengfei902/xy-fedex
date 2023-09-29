package com.xy.fedex.catalog.service;

import com.xy.fedex.catalog.dto.ModelDTO;

import java.util.List;

/**
 * @author tengfei
 */
public interface ModelService {
    ModelDTO getModelByCode(String modelCode);

    List<ModelDTO> getModelByCodes(List<String> modelCodes);

    ModelDTO getModelById(Long modelId);

    List<ModelDTO> getModelByIds(List<Long> modelIds);

    void saveModel(ModelDTO modelDTO);
}
