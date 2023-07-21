package com.xy.fedex.catalog.service.meta;

import com.xy.fedex.catalog.common.definition.field.impl.DimModel;
import com.xy.fedex.catalog.dto.DimModelRequest;

import java.util.List;

/**
 * @author tengfei
 */
public interface DimModelService {
    List<DimModel> getDimModels(DimModelRequest dimModelRequest);
}
