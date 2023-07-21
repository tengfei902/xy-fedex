package com.xy.fedex.catalog.service;

import com.google.gson.Gson;
import com.xy.fedex.catalog.BaseTest;
import com.xy.fedex.catalog.common.definition.field.impl.DimModel;
import com.xy.fedex.catalog.dto.DimModelRequest;
import com.xy.fedex.catalog.service.meta.DimModelService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

public class DimModelServiceTest extends BaseTest {
    @Autowired
    private DimModelService dimModelService;

    @Test
    public void getDimModels() {
        List<DimModel> dimModels = dimModelService.getDimModels(DimModelRequest.builder().modelIds(Arrays.asList(23L)).build());
        System.out.println(new Gson().toJson(dimModels));
    }
}
